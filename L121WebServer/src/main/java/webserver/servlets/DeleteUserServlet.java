package webserver.servlets;

import database.model.Roles;
import database.model.UserDataSet;
import database.service.UserDbService;
import webserver.template.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeleteUserServlet extends HttpServlet {

    private final TemplateProcessor templateProcessor;
    private final UserDbService dbService;

    public DeleteUserServlet(UserDbService dbService) throws IOException {
        this.dbService = dbService;
        this.templateProcessor = new TemplateProcessor();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        UserDataSet user = dbService.readUserById(Long.valueOf(id));
        if(user!=null){
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("id",user.getId());
            pageVariables.put("name",user.getName());
            if(user.getRole().equals(Roles.USER)){
                dbService.deleteUserById(Long.valueOf(id));
                resp.getWriter().println(templateProcessor.getPage("userdel.html", pageVariables));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,"User not found");
        }

    }
}
