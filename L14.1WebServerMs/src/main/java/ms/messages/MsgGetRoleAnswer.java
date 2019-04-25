package ms.messages;

import database.model.Roles;
import ms.messageSystem.Address;
import ms.messageSystem.Addressee;
import ms.messagesBase.MsgToFrontend;
import webserver.servlets.LoginServlet;

public class MsgGetRoleAnswer extends MsgToFrontend {

    private final Roles role;

    public MsgGetRoleAnswer(Address from, Address to, Roles role) {
        super(from, to);
        this.role = role;
    }

    @Override
    public void execAdr(Addressee addressee) throws Exception {
        if(addressee instanceof LoginServlet){
            LoginServlet aFilter = (LoginServlet)addressee;
            if(role!=null){
                aFilter.setRole(role);
                aFilter.setAnswerReady(true);
            }
        }
    }
}
