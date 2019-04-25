package ms.messages;

import database.model.UserDataSet;
import ms.messageSystem.Address;
import ms.messageSystem.Addressee;
import ms.messagesBase.MsgToFrontend;
import webserver.servlets.ReadAllUserServlet;

import java.util.List;

public class MsgReadAllUsersAnswer extends MsgToFrontend {

    private List<UserDataSet> users;

    public MsgReadAllUsersAnswer(Address from, Address to, List<UserDataSet> users) {
        super(from, to);
        this.users = users;
    }


    @Override
    public void execAdr(Addressee addressee) throws Exception {
        if(addressee instanceof ReadAllUserServlet){
            ReadAllUserServlet readAllUserServlet = (ReadAllUserServlet)addressee;
            readAllUserServlet.setUsers(users);
            readAllUserServlet.setAnswerReady(true);
        }
    }
}
