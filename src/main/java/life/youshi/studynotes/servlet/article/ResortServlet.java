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

/**
 * [Servlet] 重新排序
 */
@WebServlet("/api/article/resort")
public class ResortServlet extends HttpServlet {
    private final ArticleService articleService = new ArticleService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Integer sortCode = Integer.parseInt(request.getParameter("sort_code"));

        Article article = articleService.getArticleById(id);
        article.setSortCode(sortCode);

        articleService.updateArticle(article);

        JsonResponseUtils result = new JsonResponseUtils();

        response.getWriter().println(result.toJson());
    }
}
