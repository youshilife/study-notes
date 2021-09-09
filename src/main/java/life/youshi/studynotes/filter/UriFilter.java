package life.youshi.studynotes.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * [过滤器] URI过滤器
 *
 * 将URI标准化为Servlet可匹配的形式。
 */
public class UriFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // url-pattern为`/`时，无法匹配到以`/`结尾的非根URI（因为当做静态资源了）
        // 因此，对于此种URI，去除尾部`/`并重定向，以便使url-pattern为`/`能够匹配到
        String uri = URLDecoder.decode(httpRequest.getRequestURI(), httpRequest.getCharacterEncoding());
        if (!uri.equals("/") && uri.endsWith("/")) {
            uri = uri.substring(0, uri.length() - 1);
            httpResponse.sendRedirect(uri);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
