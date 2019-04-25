package ms.messages;

import database.service.UserDbService;
import ms.messageSystem.Address;
import ms.messagesBase.MsgToDB;

public class MsgCheckAuth extends MsgToDB {
    private final String login;
    private final String password;

    public MsgCheckAuth(Address from, Address to, String login, String password) {
        super(from, to);
        this.login = login;
        this.password = password;
    }

    @Override
    public void exec(UserDbService dbService) {
        if(dbService.authenticate(login,password)){
            dbService.getMS().sendMessage(new MsgAuthAnswer(getTo(), getFrom(), true,dbService.getRoleByName(login)));
        }
        else {
            dbService.getMS().sendMessage(new MsgAuthAnswer(getTo(), getFrom(), false,null));
        }

    }
}
