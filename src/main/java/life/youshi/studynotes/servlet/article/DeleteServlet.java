package life.youshi.studynotes.servlet.article;

import life.youshi.studynotes.entity.Article;
import life.youshi.studynotes.util.JsonResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * [Servlet] 删除文章
 */
@WebServlet("/api/article/delete")
public class DeleteServlet extends ArticleServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handle(response, () -> {
            Article article = checkId(request, "id");
            // 不允许删除根文章
            if (article.getPath().equals("/")) {
                throw new ForbiddenException();
            }
            articleService.deleteArticle(article);
            return new JsonResponseUtils();
        });
    }
}
