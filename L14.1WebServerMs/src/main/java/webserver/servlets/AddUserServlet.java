package webserver.servlets;

import database.model.AddressDataSet;
import database.model.PhoneDataSet;
import database.model.Roles;
import database.model.UserDataSet;
import ms.messageSystem.*;
import ms.messages.MsgAddUser;
import ms.messages.MsgGetRole;
import webserver.template.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddUserServlet extends HttpServlet implements Addressee {

    private final TemplateProcessor templateProcessor;
    private final MessageSystemContext msContext;
    private final Address address;

    private volatile boolean answerReady = false;
    private volatile boolean confirm = false;

    public AddUserServlet(MessageSystemContext msContext,TemplateProcessor templateProcessor) throws IOException {
        this.templateProcessor = templateProcessor;
        this.msContext = msContext;
        this.address = new Address(this.toString());
        msContext.setFrontAddress(this.toString(),new Address(this.toString()));
        msContext.getMessageSystem().addAddressee(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String age = req.getParameter("age");
        String userAddress = req.getParameter("address");
        String role = req.getParameter("role");
        String phones = req.getParameter("phones");
        getPhonesList(phones);

        if(name!=null){
            UserDataSet user = new UserDataSet(name,password,Integer.valueOf(age),new AddressDataSet(userAddress), Roles.valueOf(Roles.class,role),getPhonesList(phones));
            Message message = new MsgAddUser(address,msContext.getDbAddress(),user);
            msContext.getMessageSystem().sendMessage(message);

            while (!answerReady);
            answerReady = false;

            if(confirm){
                resp.resetBuffer();
                resp.sendRedirect("/admin.html");
            }
            else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
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

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return msContext.getMessageSystem();
    }

    public boolean isAnswerReady() {
        return answerReady;
    }

    public void setAnswerReady(boolean answerReady) {
        this.answerReady = answerReady;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }
}
