package webserver.servlets;

import database.model.Roles;
import database.service.UserDbService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;

public class LoginServlet extends HttpServlet{

    private final UserDbService dbService;

    public LoginServlet(UserDbService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String password = req.getParameter("password");

        if(dbService.getRoleByName(name).equals(Roles.ADMIN))
            resp.sendRedirect("/admin.html");
        else
            resp.sendRedirect("/user");
    }



}
