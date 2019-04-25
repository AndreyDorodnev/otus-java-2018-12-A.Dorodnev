package ms.messages;

import database.service.UserDbService;
import ms.messageSystem.Address;
import ms.messagesBase.MsgToDB;

public class MsgReadAllUsers extends MsgToDB {

    public MsgReadAllUsers(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(UserDbService dbService) {
        dbService.getMS().sendMessage(new MsgReadAllUsersAnswer(getTo(),getFrom(),dbService.readAll()));
    }
}
