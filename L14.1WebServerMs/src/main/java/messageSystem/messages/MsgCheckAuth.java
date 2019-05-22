package messageSystem.messages;

import database.service.UserDbService;
import messageSystem.messageBase.MsgToDB;
import messageSystem.msBase.Address;
import webserver.sockets.MessageSocket;


public class MsgCheckAuth extends MsgToDB {
    private final String login;
    private final String password;
    private final MessageSocket socket;

    public MsgCheckAuth(Address from, Address to, String login, String password, MessageSocket socket) {
        super(from, to);
        this.login = login;
        this.password = password;
        this.socket = socket;
    }

    @Override
    public void exec(UserDbService dbService) {
        String msg;
        if(dbService.authenticate(login,password)){
            msg = "answer/auth/true/" + dbService.getRoleByName(login);
            dbService.getMS().sendMessage(new MsgAnswer(getTo(), getFrom(), msg));
        }
        else {
            msg = "answer/auth/false/";
            dbService.getMS().sendMessage(new MsgAnswer(getTo(), getFrom(), msg));
        }

    }
}
