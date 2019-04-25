package webserver.servlets;

import database.model.Roles;
import database.model.UserDataSet;
import database.service.UserDbService;
import ms.messageSystem.*;
import ms.messages.MsgAddUser;
import ms.messages.MsgDeleteUser;
import ms.messages.MsgReadUserById;
import webserver.template.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeleteUserServlet extends HttpServlet implements Addressee {

    private final TemplateProcessor templateProcessor;
    private final MessageSystemContext msContext;
    private final Address address;

    private volatile boolean answerReady = false;
    private volatile boolean confirm = false;
    private UserDataSet user;

    public DeleteUserServlet(MessageSystemContext msContext,TemplateProcessor templateProcessor) throws IOException {
        this.templateProcessor = templateProcessor;
        this.msContext = msContext;
        this.address = new Address(this.toString());
        msContext.setFrontAddress(this.toString(),new Address(this.toString()));
        msContext.getMessageSystem().addAddressee(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        Message message = new MsgReadUserById(address, msContext.getDbAddress(), id);
        msContext.getMessageSystem().sendMessage(message);

        while (!answerReady);
        answerReady = false;
        if(user!=null){
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("id",user.getId());
            pageVariables.put("name",user.getName());
            if(user.getRole().equals(Roles.USER)) {
                message = new MsgDeleteUser(address,msContext.getDbAddress(),id);
                msContext.getMessageSystem().sendMessage(message);

                while (!answerReady);
                answerReady = false;

                if(confirm){
                    resp.sendRedirect("/admin.html");
                }
                else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,"User not found");
        }
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

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }
}
