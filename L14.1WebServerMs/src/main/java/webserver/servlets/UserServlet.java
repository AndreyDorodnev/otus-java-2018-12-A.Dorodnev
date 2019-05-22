package webserver.servlets;

import database.model.UserDataSet;
import webserver.template.TemplateProcessor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class UserServlet extends HttpServlet {
    private final TemplateProcessor templateProcessor;

    public UserServlet(TemplateProcessor templateProcessor) throws IOException {

        this.templateProcessor = templateProcessor;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            String name = cookies[0].getValue();
            UserDataSet userDataSet = new UserDataSet();
            userDataSet.setName(name);
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("user",userDataSet);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(templateProcessor.getPage("useredit.html", pageVariables));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.sendRedirect("index.html");
        }

    }


}
