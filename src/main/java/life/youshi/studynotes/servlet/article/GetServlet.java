package life.youshi.studynotes.servlet.article;

import life.youshi.studynotes.entity.Article;
import life.youshi.studynotes.service.ArticleService;
import life.youshi.studynotes.util.JsonResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

/**
 * [Servlet] 获取文章
 */
@WebServlet("/api/article")
public class GetServlet extends HttpServlet {
    private final ArticleService articleService = new ArticleService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = URLDecoder.decode(request.getParameter("path"), request.getCharacterEncoding());
        if (!path.equals("/") && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        Article article = articleService.getArticleByPath(path);
        Article parent = articleService.getParentArticle(article);
        List<Article> children = articleService.getChildArticles(article);

        if (parent != null) {
            parent.setContent(null);
        }
        if (children != null) {
            for (Article child : children) {
                child.setContent(null);
            }
        }

        JsonResponseUtils result = new JsonResponseUtils()
            .putData("article", article)
            .putData("parent", parent)
            .putData("children", children);

        response.getWriter().println(result.toJson());
    }
}
