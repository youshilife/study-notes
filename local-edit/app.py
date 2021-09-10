# =============================================================================
# 本地编辑助手脚本
# =============================================================================
#
# 自动调用本地的编辑器编辑文章，在本地保存时自动提交到后端。
#
# 依赖的第三方模块：
# - flask：建立本地Web服务器，接收浏览器发来的编辑请求
# - requests：与后端进行数据交互
# - watchdog：监控文件变化，实时更新内容到后端
#


import os
import time
import tempfile
import requests
from watchdog.observers import Observer
from watchdog.events import PatternMatchingEventHandler


# =====================================
# 数据定义
# =====================================


# 后端主机
BACKEND_HOST = "http://localhost:8080"
# 后端Web API
APIS = {
    "get_article":    BACKEND_HOST + "/api/article/get",
    "update_article": BACKEND_HOST + "/api/article/update",
}

# 临时目录
temp_dir = None
# 文件监视器
file_watcher = None


# =====================================
# 函数定义
# =====================================


def open_temp_dir():
    """打开临时目录"""
    global temp_dir
    temp_dir = tempfile.TemporaryDirectory()
    print("已打开临时目录: " + temp_dir.name)


def close_temp_dir():
    """关闭临时目录"""
    global temp_dir
    if temp_dir:
        temp_dir.cleanup()


def get_article(id):
    """从后端获取文章数据
    :param id       文章ID
    :return         文章数据（dict）
                    若获取失败，则返回None
    """
    article = None
    response = requests.get(APIS["get_article"], {
        "id": id,
    })
    if response.status_code == 200:
        article = response.json()["data"]["article"]
    return article


def update_article(article):
    """更新文章数据到后端
    :param article  文章数据（dict）
    :return         是否成功
    """
    response = requests.post(APIS["update_article"], {
        "id":      article["id"],
        "content": article["content"],
    })
    return response.status_code == 200


def save_article(article):
    """保存文章数据到文件
    :param article  文章数据（dict）
                    保存的文件路径会以"filePath"键设置在文章数据中
    """
    global temp_dir
    dir_path = temp_dir.name + os.sep + str(article["id"])
    file_path = dir_path + os.sep + article["title"] + ".md"
    os.mkdir(dir_path)
    with open(file_path, "w", encoding="UTF-8") as file:
        file.write(article["content"])
    article["filePath"] = file_path
    print(f"文章《{article['title']}》(ID: {article['id']})已保存到 {file_path}")


def load_article(article):
    """从文件中载入文章数据
    :param article  文章数据（dict）（已包含"filePath"键）
    """
    with open(article["filePath"], "r", encoding="UTF-8") as file:
        article["content"] = file.read()


def on_file_modified(event):
    print(event.src_path + " 被修改")


def open_file_watcher():
    """开启文件监视器"""
    global temp_dir
    global file_watcher
    # 监听的文件路径模式
    watch_patterns = ["*.md"]
    # 忽略的文件路径模式
    ignore_patterns = [
        # Typora临时文件
        "*.~*",
    ]
    # 是否忽略目录
    ignore_directories = True
    # 是否区分大小写
    case_sensitive = True
    # 事件处理器
    event_handler = PatternMatchingEventHandler(
        watch_patterns, ignore_patterns, ignore_directories, case_sensitive
    )
    # 绑定文件修改处理函数
    event_handler.on_modified = on_file_modified

    # 监控路径
    watch_path = temp_dir.name
    # 是否递归监控子目录
    recursive = True
    # 创建观察者
    file_watcher = Observer()
    # 设置监控计划
    file_watcher.schedule(event_handler, watch_path, recursive=recursive)

    # 开始监控
    file_watcher.start()


def close_file_watcher():
    """关闭文件监视器"""
    global file_watcher
    file_watcher.stop()
    file_watcher.join()


# =====================================
# 执行
# =====================================


open_temp_dir()
open_file_watcher()

article = get_article(17)
save_article(article)
load_article(article)
update_article(article)

try:
    while True:
        time.sleep(1)
except KeyboardInterrupt:
    pass

close_file_watcher()
close_temp_dir()
