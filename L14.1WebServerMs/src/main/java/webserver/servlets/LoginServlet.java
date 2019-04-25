package webserver.servlets;

import database.model.Roles;
import ms.messageSystem.*;
import ms.messages.MsgGetRole;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet implements Addressee {

    private final MessageSystemContext msContext;
    private final Address address;

    private volatile boolean answerReady = false;
    private Roles role;

    public LoginServlet(MessageSystemContext msContext) {
        this.msContext = msContext;
        this.address = new Address(this.toString());
        msContext.setFrontAddress(this.toString(),new Address(this.toString()));
        msContext.getMessageSystem().addAddressee(this);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        Message message = new MsgGetRole(address, msContext.getDbAddress(), name);
        msContext.getMessageSystem().sendMessage(message);

        while (!answerReady);
        answerReady = false;
        if(role.equals(Roles.ADMIN))
            resp.sendRedirect("/admin.html");
        else
            resp.sendRedirect("/user");
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

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
