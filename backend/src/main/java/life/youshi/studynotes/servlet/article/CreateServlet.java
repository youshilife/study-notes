package life.youshi.studynotes.servlet.article;

import life.youshi.studynotes.entity.Article;
import life.youshi.studynotes.util.JsonResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * [Servlet] 创建文章
 */
@WebServlet(urlPatterns = "/api/article/create", loadOnStartup = 0)
public class CreateServlet extends ArticleServlet {
    @Override
    public void init() throws ServletException {
        // 若系统启动时没有根文章，则自动创建之
        Article root = articleService.getArticleByPath("/");
        if (root == null) {
            root = new Article(null, "/", 0, "学习笔记");
            articleService.createArticle(root);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handle(response, () -> {
            Article parentArticle = checkId(request, "parent_id");
            String title = checkTitle(request, "title", parentArticle.getId(), null);
            String content = "";
            if (request.getParameter("content") != null) {
                content = checkContent(request, "content");
            }
            String path = articleService.getPath(parentArticle, title);
            Integer sortCode = articleService.getNextSortCode(parentArticle);

            Article article = new Article(parentArticle.getId(), path, sortCode, title, content);
            articleService.createArticle(article);

            return new JsonResponseUtils()
                .putData("article", article);
        });
    }
}
