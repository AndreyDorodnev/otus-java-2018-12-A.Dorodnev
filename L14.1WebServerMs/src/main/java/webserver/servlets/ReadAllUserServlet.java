package webserver.servlets;

import database.model.UserDataSet;
import ms.messageSystem.*;
import ms.messages.MsgReadAllUsers;
import webserver.template.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ReadAllUserServlet extends HttpServlet implements Addressee {
    private final TemplateProcessor templateProcessor;
    private final MessageSystemContext msContext;
    private final Address address;

    private volatile boolean answerReady = false;
    private List<UserDataSet> users;

    public ReadAllUserServlet(MessageSystemContext msContext,TemplateProcessor templateProcessor) throws IOException {
        this.templateProcessor = templateProcessor;
        this.msContext = msContext;
        this.address = new Address(this.toString());
        msContext.setFrontAddress(this.toString(),new Address(this.toString()));
        msContext.getMessageSystem().addAddressee(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Message message = new MsgReadAllUsers(address, msContext.getDbAddress());
        msContext.getMessageSystem().sendMessage(message);

        while (!answerReady);
        answerReady = false;
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

    public List<UserDataSet> getUsers() {
        return users;
    }

    public void setUsers(List<UserDataSet> users) {
        this.users = new ArrayList<>(users);
    }
}
