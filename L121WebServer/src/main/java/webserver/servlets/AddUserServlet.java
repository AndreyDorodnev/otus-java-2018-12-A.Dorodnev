package webserver.servlets;

import database.model.AddressDataSet;
import database.model.PhoneDataSet;
import database.model.Roles;
import database.model.UserDataSet;
import database.service.UserDbService;
import webserver.template.TemplateProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddUserServlet extends HttpServlet {

    private final TemplateProcessor templateProcessor;
    private final UserDbService dbService;

    public AddUserServlet(UserDbService dbService,TemplateProcessor templateProcessor) throws IOException {
        this.dbService = dbService;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String age = req.getParameter("age");
        String address = req.getParameter("address");
        String role = req.getParameter("role");
        String phones = req.getParameter("phones");
        getPhonesList(phones);

        if(name!=null){
            UserDataSet user = new UserDataSet(name,password,Integer.valueOf(age),new AddressDataSet(address), Roles.valueOf(Roles.class,role),getPhonesList(phones));
            if(!dbService.addUser(user))
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.resetBuffer();
            resp.sendRedirect("/admin.html");
        }
    }

    private List<PhoneDataSet> getPhonesList(String phones){
        String[] lines = phones.split("\r\n|\r|\n");
        ArrayList<PhoneDataSet> phonesList = new ArrayList<>();
        for (String line : lines) {
            phonesList.add(new PhoneDataSet(line));
        }
        return phonesList;
    }

}
