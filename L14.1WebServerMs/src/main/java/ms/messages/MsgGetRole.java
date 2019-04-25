package ms.messages;

import database.model.Roles;
import database.service.UserDbService;
import ms.messageSystem.Address;
import ms.messagesBase.MsgToDB;

public class MsgGetRole extends MsgToDB {

    private final String login;

    public MsgGetRole(Address from, Address to, String login) {
        super(from, to);
        this.login = login;
    }

    @Override
    public void exec(UserDbService dbService) {
        Roles role = dbService.getRoleByName(login);
        dbService.getMS().sendMessage(new MsgGetRoleAnswer(getTo(),getFrom(),role));
    }
}
