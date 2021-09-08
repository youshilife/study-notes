package life.youshi.studynotes.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * [过滤器] 字符编码过滤器
 *
 * 指定请求和响应都使用同一个字符编码（默认为UTF-8）。
 */
public class CharacterEncodingFilter implements Filter {
    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encoding = filterConfig.getInitParameter("encoding");
        if (encoding != null) {
            this.encoding = encoding;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);

        filterChain.doFilter(servletRequest, servletResponse);

        if (servletResponse.getContentType() == null) {
            servletResponse.setContentType("text/html;charset=" + encoding);
        }
    }

    @Override
    public void destroy() {

    }
}
