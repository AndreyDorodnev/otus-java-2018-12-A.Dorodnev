package webserver.auth;

import database.model.Roles;
import ms.messageSystem.*;
import ms.messages.MsgCheckAuth;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthorizationFilter implements Filter, Addressee {

    private ServletContext context;
    private final MessageSystemContext msContext;
    private final Address address;
    private volatile boolean isAuthorized = false;
    private volatile boolean answerReady;
    private Roles role;

    public AuthorizationFilter(MessageSystemContext msContext) {
        this.msContext = msContext;
        this.address = new Address(this.toString());
        msContext.setFrontAddress(this.toString(),new Address(this.toString()));
        msContext.getMessageSystem().addAddressee(this);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String name = req.getParameter("name");
        String password = req.getParameter("password");

        Message message = new MsgCheckAuth(address, msContext.getDbAddress(), name, password);
        msContext.getMessageSystem().sendMessage(message);

        while (!answerReady);
        answerReady = false;
        if(isAuthorized){
            Cookie userName = new Cookie("name",name);
            Cookie userPass = new Cookie("password",password);
            Cookie userRole = new Cookie("role",role.toString());

            res.addCookie(userName);
            res.addCookie(userPass);
            res.addCookie(userRole);

            res.setContentType("text/html");
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND,"Wrong name or password");
        }


//        while (!isAuthorized){
//
//        }


//        if (dbService.authenticate(name, password)){
//            Cookie userName = new Cookie("name",name);
//            Cookie userPass = new Cookie("password",password);
//            Cookie userRole = new Cookie("role",dbService.getRoleByName(name).toString());
//
//            res.addCookie(userName);
//            res.addCookie(userPass);
//            res.addCookie(userRole);
//
//            res.setContentType("text/html");
//            filterChain.doFilter(servletRequest,servletResponse);
//        } else {
//            res.sendError(HttpServletResponse.SC_NOT_FOUND,"Wrong name or password");
//        }

    }

    @Override
    public void destroy() {

    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return msContext.getMessageSystem();
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public Boolean getAnswerReady() {
        return answerReady;
    }

    public void setAnswerReady(Boolean answerReady) {
        this.answerReady = answerReady;
    }

    public void setAuthorized(boolean authorized) {
        this.isAuthorized = authorized;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
