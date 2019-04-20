package webserver.auth;

import database.model.Roles;
import webserver.helpers.CookiesHelper;
import webserver.template.TemplateProcessor;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthentificationAdminFilter implements Filter {

    private ServletContext context;
    private final TemplateProcessor templateProcessor;

    public AuthentificationAdminFilter( ) throws IOException {
        this.templateProcessor = new TemplateProcessor();
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
            res.sendRedirect("/index.html");
        Roles role = CookiesHelper.getRole(cookies);
        if(role.equals(Roles.ADMIN))
            filterChain.doFilter(req,res);
        else
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    public void destroy() {

    }

}
