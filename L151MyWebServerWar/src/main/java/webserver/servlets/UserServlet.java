package webserver.servlets;

import database.model.UserDataSet;
import webserver.helpers.CookiesHelper;
import webserver.template.TemplateProcessor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class UserServlet extends HttpServlet {
    private TemplateProcessor templateProcessor;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        templateProcessor = new TemplateProcessor();
        Cookie[] cookies = request.getCookies();
        String name = CookiesHelper.getName(cookies);
        UserDataSet userDataSet = new UserDataSet();
        userDataSet.setName(name);
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("user",userDataSet);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(templateProcessor.getPage("useredit.html", pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

//    public void setTemplateProcessor(TemplateProcessor templateProcessor) {
//        this.templateProcessor = templateProcessor;
//    }
//
//    public TemplateProcessor getTemplateProcessor() {
//        return templateProcessor;
//    }
}
