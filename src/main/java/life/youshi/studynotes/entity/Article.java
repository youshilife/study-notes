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
    // 排序值
    private Integer sortCode;
    // 访问路径
    private String path;
    // 标题
    private String title;
    // 内容（Markdown格式）
    private String contentMarkdown;
    // 内容（HTML格式）
    private String contentHtml;
    // 内容（纯文本格式）
    private String contentText;
    // 创建时间
    private Date createdAt;
    // 更新时间
    private Date updatedAt;

    public Article() {
    }

    public Article(Integer parentId, Integer sortCode, String path, String title, String contentMarkdown, String contentHtml, String contentText) {
        this.parentId = parentId;
        this.sortCode = sortCode;
        this.path = path;
        this.title = title;
        this.contentMarkdown = contentMarkdown;
        this.contentHtml = contentHtml;
        this.contentText = contentText;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Article(Integer parentId, Integer sortCode, String path, String title, String contentMarkdown, String contentHtml, String contentText, Date createdAt, Date updatedAt) {
        this.parentId = parentId;
        this.sortCode = sortCode;
        this.path = path;
        this.title = title;
        this.contentMarkdown = contentMarkdown;
        this.contentHtml = contentHtml;
        this.contentText = contentText;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Article(Integer id, Integer parentId, Integer sortCode, String path, String title, String contentMarkdown, String contentHtml, String contentText, Date createdAt, Date updatedAt) {
        this.id = id;
        this.parentId = parentId;
        this.sortCode = sortCode;
        this.path = path;
        this.title = title;
        this.contentMarkdown = contentMarkdown;
        this.contentHtml = contentHtml;
        this.contentText = contentText;
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

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentMarkdown() {
        return contentMarkdown;
    }

    public void setContentMarkdown(String contentMarkdown) {
        this.contentMarkdown = contentMarkdown;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
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
        stringBuilder.append(String.format("[内容（Markdown格式）]\n%s\n", contentMarkdown));
        stringBuilder.append(String.format("[内容（HTML格式）]\n%s\n", contentHtml));
        stringBuilder.append(String.format("[内容（纯文本格式）]\n%s\n", contentText));
        stringBuilder.append(String.format("[创建时间]    %s\n", dateFormat.format(createdAt)));
        stringBuilder.append(String.format("[更新时间]    %s\n", dateFormat.format(updatedAt)));
        return stringBuilder.toString();
    }
}
