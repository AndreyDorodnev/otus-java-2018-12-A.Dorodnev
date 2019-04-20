package webserver.servlets;

import database.model.PhoneDataSet;
import database.model.Roles;
import database.model.UserDataSet;
import database.service.UserDbService;
import webserver.helpers.CookiesHelper;
import webserver.template.TemplateProcessor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class UserServlet extends HttpServlet {
    private final TemplateProcessor templateProcessor;
    private final UserDbService dbService;

    public UserServlet(UserDbService dbService,TemplateProcessor templateProcessor) throws IOException {
        this.dbService = dbService;
        this.templateProcessor = templateProcessor;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            String name = CookiesHelper.getName(cookies);
            UserDataSet user = dbService.readByName(name);
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("user",user);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(templateProcessor.getPage("useredit.html", pageVariables));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.sendRedirect("index.html");
        }

    }


}
