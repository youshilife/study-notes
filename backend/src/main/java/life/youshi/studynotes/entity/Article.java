package life.youshi.studynotes.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * [实体] 文章
 */
public class Article {
    // 文章ID
    private Integer id;
    // 父文章ID
    private Integer parentId;
    // 访问路径
    // 根：/
    // 非根：/XXX/YYY/ZZZ
    private String path;
    // 排序值
    private Integer sortCode = 0;
    // 标题
    private String title;
    // 内容（Markdown代码）
    private String content = "";
    // 创建时间
    private Date createdAt = new Date();
    // 更新时间
    private Date updatedAt = new Date();

    public Article() {
    }

    public Article(Integer parentId, String path, Integer sortCode, String title) {
        this.parentId = parentId;
        this.path = path;
        this.sortCode = sortCode;
        this.title = title;
    }

    public Article(Integer parentId, String path, Integer sortCode, String title, String content) {
        this.parentId = parentId;
        this.path = path;
        this.sortCode = sortCode;
        this.title = title;
        this.content = content;
    }

    public Article(Integer parentId, String path, Integer sortCode, String title, String content, Date createdAt, Date updatedAt) {
        this.parentId = parentId;
        this.path = path;
        this.sortCode = sortCode;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Article(Integer id, Integer parentId, String path, Integer sortCode, String title, String content, Date createdAt, Date updatedAt) {
        this.id = id;
        this.parentId = parentId;
        this.path = path;
        this.sortCode = sortCode;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("[文章编号]    %d\n", id));
        stringBuilder.append(String.format("[父文章编号]  %d\n", parentId));
        stringBuilder.append(String.format("[排序值]      %d\n", sortCode));
        stringBuilder.append(String.format("[访问路径]    %s\n", path));
        stringBuilder.append(String.format("[内容]\n%s\n", content));
        stringBuilder.append(String.format("[创建时间]    %s\n", dateFormat.format(createdAt)));
        stringBuilder.append(String.format("[更新时间]    %s\n", dateFormat.format(updatedAt)));
        return stringBuilder.toString();
    }
}
