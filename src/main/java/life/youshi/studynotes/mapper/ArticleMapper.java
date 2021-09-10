package life.youshi.studynotes.mapper;

import life.youshi.studynotes.entity.Article;
import life.youshi.studynotes.util.MyBatisUtils;

import java.util.List;
import java.util.Map;

/**
 * [数据库映射器] 文章
 *
 * 提供文章数据的数据库交互操作方法。
 */
public class ArticleMapper {
    // 配置文件中的命名空间
    private static final String namespace = ArticleMapper.class.getName();

    /**
     * 根据文章ID查询文章
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
     * 根据访问路径查询文章
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
     * 根据父文章ID查询文章
     *
     * @param parentId  父文章ID
     * @return          文章列表
     */
    public List<Article> selectArticlesByParentId(Integer parentId) {
        return (List<Article>) MyBatisUtils.executeQuery(
            sqlSession ->
                sqlSession.selectList(namespace + ".selectByParentId", parentId)
        );
    }

    /**
     * 根据动态参数查询文章
     *
     * @param params    参数表
     * @return          文章列表
     */
    public List<Article> selectArticlesByParams(Map<String, Object> params) {
        return (List<Article>) MyBatisUtils.executeQuery(
            sqlSession ->
                sqlSession.selectList(namespace + ".selectByParams", params)
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
