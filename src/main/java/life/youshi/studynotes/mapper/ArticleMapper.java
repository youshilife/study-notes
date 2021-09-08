package life.youshi.studynotes.mapper;

import life.youshi.studynotes.entity.Article;
import life.youshi.studynotes.util.MyBatisUtils;

/**
 * [数据库映射器] 文章
 *
 * 提供文章数据的数据库交互操作方法。
 */
public class ArticleMapper {
    // 配置文件中的命名空间
    private static final String namespace = ArticleMapper.class.getName();

    /**
     * 根据ID获取文章
     *
     * @param id    文章ID
     * @return      文章实例
     */
    public Article selectArticleById(Integer id) {
        return (Article) MyBatisUtils.executeQuery(
            sqlSession ->
                sqlSession.selectOne(namespace + ".selectById", id)
        );
    }

    /**
     * 根据访问路径获取文章
     *
     * @param path  访问路径
     * @return      文章实例
     */
    public Article selectArticleByPath(String path) {
        return (Article) MyBatisUtils.executeQuery(
            sqlSession ->
                sqlSession.selectOne(namespace + ".selectByPath", path)
        );
    }

    /**
     * 插入文章
     *
     * @param article   文章实例
     * @return          是否成功
     */
    public boolean insertArticle(Article article) {
        int rows = (int) MyBatisUtils.executeUpdate(
            sqlSession ->
                sqlSession.insert(namespace + ".insert", article)
        );
        return rows > 0;
    }

    /**
     * 更新文章
     *
     * @param article   文章实例
     * @return          是否成功
     */
    public boolean updateArticle(Article article) {
        int rows = (int) MyBatisUtils.executeUpdate(
            sqlSession ->
                sqlSession.update(namespace + ".update", article)
        );
        return rows > 0;
    }

    /**
     * 根据ID删除文章
     *
     * @param id    文章ID
     * @return      是否成功
     */
    public boolean deleteArticleById(Integer id) {
        int rows = (int) MyBatisUtils.executeUpdate(
            sqlSession ->
                sqlSession.delete(namespace + ".deleteById", id)
        );
        return rows > 0;
    }

    /**
     * 删除文章
     *
     * @param article   文章实例
     * @return          是否成功
     */
    public boolean deleteArticle(Article article) {
        return deleteArticleById(article.getId());
    }
}
