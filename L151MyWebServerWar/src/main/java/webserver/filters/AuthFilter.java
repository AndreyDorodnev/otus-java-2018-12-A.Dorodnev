package webserver.filters;

import webserver.helpers.CookiesHelper;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Cookie[] cookies = req.getCookies();
        if(cookies!=null){
            String role = CookiesHelper.getRole(cookies);
            resp.setContentType("text/html");
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else {
            resp.sendRedirect("index.html");
        }
    }

    @Override
    public void destroy() {

    }
}
