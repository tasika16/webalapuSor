package filter;

import entity.LoginBean;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "LoginFilter", urlPatterns = { "*.xhtml" })
public class LoginFilter implements Filter {

    public static final String LOGIN_PAGE = "/faces/pages/login.xhtml";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        LoginBean loginBean = (LoginBean) request.getSession().getAttribute("loginBean");
        String uri = request.getRequestURI();
        if (uri.contains(LOGIN_PAGE) || uri.contains("javax.faces.resource")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (loginBean == null || !loginBean.isLoggedIn()) {
                response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}