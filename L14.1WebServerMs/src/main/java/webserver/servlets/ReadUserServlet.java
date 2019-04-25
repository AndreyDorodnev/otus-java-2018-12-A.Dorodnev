package webserver.servlets;

import database.model.PhoneDataSet;
import database.model.UserDataSet;
import database.service.UserDbService;
import ms.messageSystem.*;
import ms.messages.MsgReadUserById;
import ms.messages.MsgReadUserByName;
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

public class ReadUserServlet extends HttpServlet implements Addressee {

    private final TemplateProcessor templateProcessor;
    private final MessageSystemContext msContext;
    private final Address address;
    private volatile boolean answerReady = false;
    private UserDataSet user;

    public ReadUserServlet(MessageSystemContext msContext,TemplateProcessor templateProcessor) throws IOException {
        this.msContext = msContext;
        this.templateProcessor = templateProcessor;
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
            pageVariables.put("user",user);
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(templateProcessor.getPage("userinfo.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_OK);
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

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }
}
