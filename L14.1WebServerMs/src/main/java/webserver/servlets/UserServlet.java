package webserver.servlets;

import database.model.PhoneDataSet;
import database.model.Roles;
import database.model.UserDataSet;
import database.service.UserDbService;
import ms.messageSystem.*;
import ms.messages.MsgGetRole;
import ms.messages.MsgReadUserByName;
import webserver.helpers.CookiesHelper;
import webserver.template.TemplateProcessor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class UserServlet extends HttpServlet implements Addressee {
    private final MessageSystemContext msContext;
    private final Address address;
    private final TemplateProcessor templateProcessor;
    private volatile boolean answerReady = false;
    private UserDataSet user;

    public UserServlet(MessageSystemContext msContext,TemplateProcessor templateProcessor) throws IOException {
        this.msContext = msContext;
        this.templateProcessor = templateProcessor;
        this.address = new Address(this.toString());
        msContext.setFrontAddress(this.toString(),new Address(this.toString()));
        msContext.getMessageSystem().addAddressee(this);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            String name = CookiesHelper.getName(cookies);
            Message message = new MsgReadUserByName(address, msContext.getDbAddress(), name);
            msContext.getMessageSystem().sendMessage(message);

            while (!answerReady);
            answerReady = false;
            if(user!=null){
                Map<String, Object> pageVariables = new HashMap<>();
                pageVariables.put("user",user);
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().println(templateProcessor.getPage("useredit.html", pageVariables));
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else {
            response.sendRedirect("index.html");
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

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    public boolean isAnswerReady() {
        return answerReady;
    }

    public void setAnswerReady(boolean answerReady) {
        this.answerReady = answerReady;
    }
}
