package webserver.auth;

import database.model.Roles;
import database.model.UserDataSet;
import database.service.DBServiceHibernate;
import database.service.UserDbService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    private ServletContext context;
    private final UserDbService dbService;

    public AuthorizationFilter(UserDbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String name = req.getParameter("name");
        String password = req.getParameter("password");

        if (dbService.authenticate(name, password)){
            Cookie userName = new Cookie("name",name);
            Cookie userPass = new Cookie("password",password);
            Cookie userRole = null;
            if(dbService.getRoleByName(name).equals(Roles.ADMIN))
                userRole = new Cookie("role",Roles.ADMIN.toString());
            else
                userRole = new Cookie("role",Roles.USER.toString());

            res.addCookie(userName);
            res.addCookie(userPass);
            res.addCookie(userRole);

            res.setContentType("text/html");

            HttpSession session = req.getSession();
//            session.setMaxInactiveInterval(30);
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND,"Wrong name or password");
        }

    }

    @Override
    public void destroy() {

    }
}
