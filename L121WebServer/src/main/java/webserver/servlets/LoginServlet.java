package webserver.servlets;

import database.model.Roles;
import database.service.DBServiceHibernate;
import database.service.UserDbService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;

public class LoginServlet extends HttpServlet{

    private static final int EXPIRE_INTERVAL = 20; // seconds

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();
        resp.setContentType("text/html");
        if(cookies!=null){
            Roles role  = getRole(cookies);
            if(role.equals(Roles.ADMIN))
                resp.sendRedirect("admin.html");
            else
                resp.sendRedirect("/user");
        }
        else {
            resp.sendRedirect("index.html");
        }
    }

    private Roles getRole(Cookie[] cookies){
        Cookie cookie = Arrays.stream(cookies).filter(x->x.getName().equals("role")).findFirst().get();
        if(cookie!=null){
            return Roles.valueOf(cookie.getValue());
        }
        return null;
    }

}
