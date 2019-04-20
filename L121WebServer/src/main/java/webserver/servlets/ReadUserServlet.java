package webserver.servlets;

import database.model.PhoneDataSet;
import database.model.UserDataSet;
import database.service.UserDbService;
import webserver.template.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadUserServlet extends HttpServlet {

    private final TemplateProcessor templateProcessor;
    private final UserDbService dbService;

    public ReadUserServlet(UserDbService dbService,TemplateProcessor templateProcessor) throws IOException {
        this.dbService = dbService;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        UserDataSet user = dbService.readUserById(Long.valueOf(id));

        Map<String, Object> pageVariables = new HashMap<>();
        if(user==null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,"User not found");
        }
        else {
            pageVariables.put("user",user);
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(templateProcessor.getPage("userinfo.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_OK);
        }

    }
}
