package webserver.servlets;

import database.model.Roles;
import webserver.helpers.CookiesHelper;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet{

    public LoginServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();
        if(cookies!=null){
            String role = CookiesHelper.getRole(cookies);
            if(role.equals(Roles.ADMIN.toString()))
                resp.sendRedirect("/admin.html");
            else
                resp.sendRedirect("/user");
        }
        else {
            resp.sendRedirect("index.html");
        }

    }
}
