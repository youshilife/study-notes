package life.youshi.studynotes.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * [过滤器] JSON过滤器
 *
 * 设置请求和响应使用JSON。
 */
public class JsonFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);

        if (servletResponse.getContentType() == null) {
            servletResponse.setContentType("application/json");
        }
    }

    @Override
    public void destroy() {

    }
}
