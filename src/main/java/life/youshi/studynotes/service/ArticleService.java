package life.youshi.studynotes.service;

import life.youshi.studynotes.entity.Article;
import life.youshi.studynotes.mapper.ArticleMapper;

import java.util.List;

/**
 * [服务] 文章服务
 *
 * 实现文章相关业务逻辑。
 */
public class ArticleService {
    private final ArticleMapper articleMapper = new ArticleMapper();

    /**
     * 根据文章ID获取文章
     *
     * @param id    文章ID
     * @return      文章实例
     */
    public Article getArticleById(Integer id) {
        return articleMapper.selectArticleById(id);
    }

    /**
     * 根据访问路径获取文章
     *
     * @param path  访问路径
     * @return      文章实例
     */
    public Article getArticleByPath(String path) {
        return articleMapper.selectArticleByPath(path);
    }

    /**
     * 获取文章的父文章
     *
     * @param article   文章实例
     * @return          父文章实例
     *                  若不存在父文章则返回null
     */
    public Article getParentArticle(Article article) {
        return getArticleById(article.getParentId());
    }

    /**
     * 获取文章的子文章
     *
     * @param article   文章实例
     * @return          子文章实例列表
     */
    public List<Article> getChildArticles(Article article) {
        return articleMapper.selectArticlesByParentId(article.getId());
    }

    /**
     * 获取指定父文章中的下一个排序值
     *
     * @param parentArticle 父文章实例
     * @return              排序值
     *                      使用此排序值可使得文章排在最后
     */
    public int getNextSortCode(Article parentArticle) {
        int sortCode = 0;
        List<Article> children = getChildArticles(parentArticle);
        if (children != null && children.size() > 0) {
            sortCode = children.get(children.size() - 1).getSortCode() + 1;
        }
        return sortCode;
    }

    /**
     * 在数据库中创建新的文章数据
     *
     * @param article   文章实例
     * @return          是否成功
     */
    public boolean createArticle(Article article) {
        return articleMapper.insertArticle(article);
    }

    /**
     * 更新数据库中的文章数据
     *
     * @param article   文章实例
     * @return          是否成功
     */
    public boolean updateArticle(Article article) {
        return articleMapper.updateArticle(article);
    }

    /**
     * 根据文章ID删除数据库中的文章数据
     *
     * @param id    文章ID
     * @return      是否成功
     */
    public boolean deleteArticleById(Integer id) {
        return articleMapper.deleteArticleById(id);
    }

    /**
     * 删除数据库中的文章数据
     *
     * @param article   文章实例
     * @return          是否成功
     */
    public boolean deleteArticle(Article article) {
        return articleMapper.deleteArticle(article);
    }
}
