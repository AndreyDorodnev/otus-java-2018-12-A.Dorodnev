package ms.messages;

import database.model.UserDataSet;
import ms.messageSystem.Address;
import ms.messageSystem.Addressee;
import ms.messagesBase.MsgToFrontend;
import webserver.servlets.DeleteUserServlet;
import webserver.servlets.ReadUserServlet;
import webserver.servlets.UserServlet;

public class MsgReadUserAnswer extends MsgToFrontend {

    private final UserDataSet user;

    public MsgReadUserAnswer(Address from, Address to, UserDataSet user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void execAdr(Addressee addressee) throws Exception {
        if(addressee instanceof UserServlet){
            execAdr((UserServlet)addressee);
        }
        else if(addressee instanceof ReadUserServlet) {
            execAdr((ReadUserServlet)addressee);
        }
        else if(addressee instanceof DeleteUserServlet){
            execAdr((DeleteUserServlet)addressee);
        }
    }

    public void execAdr(UserServlet userServlet){
        userServlet.setUser(user);
        userServlet.setAnswerReady(true);
    }

    public void execAdr(ReadUserServlet readUserServlet){
        readUserServlet.setUser(user);
        readUserServlet.setAnswerReady(true);
    }

    public void execAdr(DeleteUserServlet deleteUserServlet){
        deleteUserServlet.setUser(user);
        deleteUserServlet.setAnswerReady(true);
    }
}
