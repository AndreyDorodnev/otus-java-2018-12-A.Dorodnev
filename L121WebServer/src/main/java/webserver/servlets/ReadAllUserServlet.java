package webserver.servlets;

import database.model.UserDataSet;
import database.service.UserDbService;
import webserver.template.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadAllUserServlet extends HttpServlet {
    private final TemplateProcessor templateProcessor;
    private final UserDbService dbService;

    public ReadAllUserServlet(UserDbService dbService) throws IOException {
        this.dbService = dbService;
        this.templateProcessor = new TemplateProcessor();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDataSet> users = dbService.readAll();
        if(users!=null){
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("users",users);
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(templateProcessor.getPage("readall.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,"Users not found");
        }

    }

}
