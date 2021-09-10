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


import tempfile
import requests


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


# =====================================
# 执行
# =====================================

open_temp_dir()

article = get_article(17)
article["content"] += "[新内容]"
update_article(article)

close_temp_dir()
