package life.youshi.studynotes.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.util.function.Function;

/**
 * [实用工具] MyBatis实用工具类
 *
 * 提供使用MyBatis的便利方法。
 */
public class MyBatisUtils {
    // SqlSession会话工厂
    private static final SqlSessionFactory factory;

    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            factory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 开启一个SqlSession会话
     *
     * @param autoCommit    是否自动提交事务
     * @return              SqlSession会话实例
     */
    public static SqlSession openSession(boolean autoCommit) {
        return factory.openSession(autoCommit);
    }

    /**
     * 开启一个SqlSession会话（自动提交事务）
     *
     * @return              SqlSession会话实例
     */
    public static SqlSession openSession() {
        return openSession(true);
    }

    /**
     * 关闭一个SqlSession会话
     *
     * @param session   SqlSession会话实例
     */
    public static void closeSession(SqlSession session) {
        if (session != null) {
            session.close();
        }
    }

    /**
     * 执行数据库查询（读库）
     *
     * @param function  函数，查询逻辑封装在函数体中
     * @return          函数返回的结果对象
     */
    public static Object executeQuery(Function<SqlSession, Object> function) {
        SqlSession session = null;
        try {
            session = openSession();
            return function.apply(session);
        } finally {
            closeSession(session);
        }
    }

    /**
     * 执行数据库更新（写库）
     *
     * 整个函数中的所有操作都在同一个事务中。
     *
     * @param function  函数，查询逻辑封装在函数体中
     * @return          函数返回的结果对象
     */
    public static Object executeUpdate(Function<SqlSession, Object> function) {
        SqlSession session = null;
        try {
            session = openSession(false);
            Object result = function.apply(session);
            session.commit();
            return result;
        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            throw e;
        } finally {
            closeSession(session);
        }
    }
}
