package webserver.servlets;

import database.service.DBServiceHibernate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet{

    private static final int EXPIRE_INTERVAL = 20; // seconds
    private final DBServiceHibernate dbService;

    public LoginServlet(DBServiceHibernate dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");


        if (dbService.authenticate(name, password)) {
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(30);
        } else {
            resp.setStatus(403);
        }
    }
}
