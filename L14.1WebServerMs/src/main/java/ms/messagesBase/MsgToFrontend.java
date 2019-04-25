package ms.messagesBase;

import ms.messageSystem.Address;
import ms.messageSystem.Addressee;
import ms.messageSystem.Message;
import webserver.JettyService;
import webserver.auth.AuthorizationFilter;
import webserver.servlets.LoginServlet;

public abstract class MsgToFrontend extends Message {

    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

//    @Override
//    public void exec(Addressee addressee) throws Exception {
//        if (addressee instanceof AuthorizationFilter) {
//            exec((AuthorizationFilter) addressee);
//        }
//        else {
//            //todo error!
//        }
//    }
//
//    public abstract void exec(AuthorizationFilter frontend) throws Exception;

    @Override
    public void exec(Addressee addressee) throws Exception {
        if(addressee!=null)
            execAdr(addressee);
        else {
            //todo error!
        }
    }

    public abstract void execAdr(Addressee addressee) throws Exception;
}
