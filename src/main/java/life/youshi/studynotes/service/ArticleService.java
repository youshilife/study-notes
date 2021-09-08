package life.youshi.studynotes.service;

import life.youshi.studynotes.entity.Article;
import life.youshi.studynotes.mapper.ArticleMapper;

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
