package messageSystem.messages;

import database.service.UserDbService;
import messageSystem.messageBase.MsgToDB;
import messageSystem.msBase.Address;

public class MsgCheckAuth extends MsgToDB {
    private final String login;
    private final String password;
    private final Integer socketId;

    public MsgCheckAuth(Address from, Address to, String login, String password, Integer socketId) {
        super(from, to);
        this.login = login;
        this.password = password;
        this.socketId = socketId;
    }

    @Override
    public void exec(UserDbService dbService) {
        String msg;
        System.out.println("Msg check auth exec");
        if(dbService.authenticate(login,password)){
            msg = "answer/auth/true/" + dbService.getRoleByName(login);
            dbService.getMS().sendMessage(new MsgAnswer(getTo(), getFrom(), msg,socketId));
        }
        else {
            msg = "answer/auth/false/";
            dbService.getMS().sendMessage(new MsgAnswer(getTo(), getFrom(), msg,socketId));
        }

    }
}
