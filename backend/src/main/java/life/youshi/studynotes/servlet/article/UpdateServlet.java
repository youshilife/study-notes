package life.youshi.studynotes.servlet.article;

import life.youshi.studynotes.entity.Article;
import life.youshi.studynotes.util.JsonResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * [Servlet] 更新文章
 */
@WebServlet("/api/article/update")
public class UpdateServlet extends ArticleServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handle(response, () -> {
            Article article = checkId(request, "id");
            boolean hasData = false;
            if (request.getParameter("sort_code") != null) {
                Integer sortCode = checkInteger(request, "sort_code");
                article.setSortCode(sortCode);
                hasData = true;
            }
            if (request.getParameter("title") != null) {
                String title = checkTitle(request, "title", article.getParentId(), article.getId());
                article.setPath(article.getPath().replaceFirst(article.getTitle() + "$", title));
                article.setTitle(title);
                hasData = true;
            }
            if (request.getParameter("content") != null) {
                String content = checkContent(request, "content");
                article.setContent(content);
                hasData = true;
            }

            if (! hasData) {
                throw new BadRequestException();
            }

            articleService.updateArticle(article);
            article = articleService.getArticleById(article.getId());

            return new JsonResponseUtils()
                .putData("article", article);
        });
    }
}
