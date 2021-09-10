package life.youshi.studynotes.servlet.article;

import life.youshi.studynotes.entity.Article;
import life.youshi.studynotes.util.JsonResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * [Servlet] 获取文章
 */
@WebServlet("/api/article/get")
public class GetServlet extends ArticleServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handle(response, () -> {
            Article article;
            if (request.getParameter("id") != null) {
                article = checkId(request, "id");
            } else if (request.getParameter("path") != null) {
                article = checkPath(request, "path");
            } else {
                throw new BadRequestException();
            }

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

            return new JsonResponseUtils()
                .putData("article", article)
                .putData("parent", parent)
                .putData("children", children);
        });
    }
}
