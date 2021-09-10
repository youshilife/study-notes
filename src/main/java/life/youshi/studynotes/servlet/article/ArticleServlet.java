package life.youshi.studynotes.servlet.article;

import life.youshi.studynotes.entity.Article;
import life.youshi.studynotes.service.ArticleService;
import life.youshi.studynotes.util.JsonResponseUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * [Servlet] 文章管理
 */
public abstract class ArticleServlet extends HttpServlet {
    protected final ArticleService articleService = new ArticleService();

    // 不正确的请求格式异常
    protected static class BadRequestException extends RuntimeException {}
    // 禁止操作异常
    protected static class ForbiddenException extends RuntimeException {}
    // 未找到资源异常
    protected static class NotFoundException extends RuntimeException {}
    // 标题冲突异常
    protected static class TitleConflictException extends RuntimeException {}

    /**
     * 检查请求中的整数参数
     *
     * @param request   请求
     * @param paramKey  参数键名
     * @return          转换成的整数
     * @throws BadRequestException  检查不通过时抛出
     */
    protected Integer checkInteger(HttpServletRequest request, String paramKey) {
        try {
            return Integer.parseInt(request.getParameter(paramKey));
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    /**
     * 检查请求中的字符串参数（可为空串）
     *
     * @param request   请求
     * @param paramKey  参数键名
     * @return          去掉首尾空白字符后的字符串
     * @throws BadRequestException  检查不通过时抛出
     */
    protected String checkString(HttpServletRequest request, String paramKey) {
        try {
            return request.getParameter(paramKey).trim();
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    /**
     * 检查请求中的字符串参数（不可为空串）
     *
     * @param request   请求
     * @param paramKey  参数键名
     * @return          去掉首尾空白字符后的字符串
     * @throws BadRequestException  检查不通过时抛出
     */
    protected String checkStringNonEmpty(HttpServletRequest request, String paramKey) {
        try {
            String string = request.getParameter(paramKey).trim();
            if (string.equals("")) {
                throw new Exception();
            }
            return string;
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    /**
     * 检查请求中的字符串参数（不含非法字符）
     *
     * @param request       请求
     * @param paramKey      参数键名
     * @param ILLEGAL_CHARS 非法字符数组
     * @return              去掉首尾空白字符后的字符串
     * @throws BadRequestException  检查不通过时抛出
     */
    protected String checkStringNoIllegalChar(HttpServletRequest request, String paramKey, final char[] ILLEGAL_CHARS) {
        try {
            String string = checkString(request, paramKey);
            for (char ch : ILLEGAL_CHARS) {
                if (string.indexOf(ch) >= 0) {
                    throw new Exception();
                }
            }
            return string;
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    /**
     * 检查请求中的文章ID
     *
     * ID合法条件：
     * - 参数存在且可转为整数
     * - 对应的文章存在
     *
     * @param request   请求
     * @param paramKey  参数键名
     * @return          ID对应的文章实例
     * @throws NotFoundException    文章不存在时抛出
     * @throws BadRequestException  其他异常时抛出
     */
    protected Article checkId(HttpServletRequest request, String paramKey) {
        try {
            Integer id = checkInteger(request, paramKey);
            Article article = articleService.getArticleById(id);
            Objects.requireNonNull(article);
            return article;
        } catch (NullPointerException e) {
            throw new NotFoundException();
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    /**
     * 检查请求中的文章访问路径
     *
     * 访问路径合法条件：
     * - 参数存在且为非空字符串
     * - 对应的文章存在
     *
     * @param request   请求
     * @param paramKey  参数键名
     * @return          访问路径对应的文章实例
     * @throws NotFoundException    文章不存在时抛出
     * @throws BadRequestException  其他异常时抛出
     */
    protected Article checkPath(HttpServletRequest request, String paramKey) {
        try {
            String path = checkStringNonEmpty(request, paramKey);
            path = URLDecoder.decode(path, request.getCharacterEncoding());
            if (path.endsWith("/") && !path.equals("/")) {
                path = path.substring(0, path.length() - 1);
            }
            Article article = articleService.getArticleByPath(path);
            Objects.requireNonNull(article);
            return article;
        } catch (NullPointerException e) {
            throw new NotFoundException();
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    /**
     * 检查请求中的文章标题
     *
     * 标题合法条件：
     * - 参数存在且为非空字符串
     * - 不含非法字符
     * - 与兄弟文章的标题不冲突
     *
     * @param request   请求
     * @param paramKey  参数键名
     * @return          去掉首尾空白字符后的标题
     * @throws TitleConflictException   标题冲突时抛出
     * @throws BadRequestException      其他异常时抛出
     */
    protected String checkTitle(HttpServletRequest request, String paramKey, Integer parentArticleId, Integer exceptArticleId) {
        final char[] ILLEGAL_CHARS = {
            '/', '*',
        };

        try {
            String title = checkStringNonEmpty(request, paramKey);
            title = checkStringNoIllegalChar(request, paramKey, ILLEGAL_CHARS);

            Map<String, Object> params = new LinkedHashMap<>();
            params.put("parentId", parentArticleId);
            params.put("title", title);
            List<Article> children = articleService.getArticlesByParams(params);
            if (children.size() > 0 && !Objects.equals(children.get(0).getId(), exceptArticleId)) {
                throw new TitleConflictException();
            }

            return title;
        } catch (TitleConflictException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    /**
     * 检查请求中的文章内容
     *
     * @param request   请求
     * @param paramKey  参数键名
     * @return          去掉首尾空白字符后的文章内容
     *                  对于非空内容，末尾保证有一个换行符
     * @throws BadRequestException  检查不通过时抛出
     */
    protected String checkContent(HttpServletRequest request, String paramKey) {
        try {
            String content = checkString(request, paramKey);
            // 保证非空内容最后有一个换行符
            if (!content.equals("")) {
                content += "\n";
            }
            return content;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    /**
     * 处理请求
     *
     * @param response  响应
     * @param function  处理函数，返回JSON结果
     */
    protected void handle(HttpServletResponse response, Supplier<JsonResponseUtils> function) throws IOException {
        JsonResponseUtils result;

        try {
            result = function.get();
        } catch (BadRequestException e) {
            response.setStatus(400);
            result = new JsonResponseUtils(400, "请求格式错误！");
        } catch (ForbiddenException e) {
            response.setStatus(403);
            result = new JsonResponseUtils(403, "不允许的操作！");
        } catch (NotFoundException e) {
            response.setStatus(404);
            result = new JsonResponseUtils(404, "资源不存在！");
        } catch (TitleConflictException e) {
            response.setStatus(406);
            result = new JsonResponseUtils(404, "标题冲突！");
        } catch (Exception e) {
            response.setStatus(500);
            result = new JsonResponseUtils(500, "服务器错误！");
        }

        response.getWriter().println(result.toJson());
    }
}
