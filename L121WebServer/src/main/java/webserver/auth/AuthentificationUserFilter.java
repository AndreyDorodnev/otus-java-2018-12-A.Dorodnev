package webserver.auth;

import database.model.Roles;
import database.service.DBServiceHibernate;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class AuthentificationUserFilter implements Filter {
    private ServletContext context;

    public AuthentificationUserFilter() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        Cookie[] cookies = req.getCookies();
        res.setContentType("text/html");
        if(cookies == null)
            res.sendRedirect("index.html");
        Roles role = getRole(cookies);
        if(role.equals(Roles.ADMIN)||role.equals(Roles.USER))
            filterChain.doFilter(req,res);
        else
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    private Roles getRole(Cookie[] cookies){
        Cookie cookie = Arrays.stream(cookies).filter(x->x.getName().equals("role")).findFirst().get();
        if(cookie!=null){
            return Roles.valueOf(cookie.getValue());
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}
