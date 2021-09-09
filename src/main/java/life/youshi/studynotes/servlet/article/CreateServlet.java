package life.youshi.studynotes.servlet.article;

import life.youshi.studynotes.entity.Article;
import life.youshi.studynotes.service.ArticleService;
import life.youshi.studynotes.util.JsonResponseUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * [Servlet] 创建文章
 */
@WebServlet(urlPatterns = "/api/article/create", loadOnStartup = 0)
public class CreateServlet extends HttpServlet {
    private final ArticleService articleService = new ArticleService();

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 若系统启动时没有根文章，则自动创建之
        Article root = articleService.getArticleByPath("/");
        if (root == null) {
            root = new Article(null, "/", 0, "学习笔记");
            articleService.createArticle(root);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer parentId = Integer.parseInt(request.getParameter("parent_id"));
        String title = request.getParameter("title");

        Article parent = articleService.getArticleById(parentId);
        String path = articleService.getPath(parent, title);
        int sortCode = articleService.getNextSortCode(parent);

        Article article = new Article(parentId, path, sortCode, title);
        articleService.createArticle(article);

        JsonResponseUtils result = new JsonResponseUtils()
            .putData("article", article);

        response.getWriter().println(result.toJson());
    }
}
