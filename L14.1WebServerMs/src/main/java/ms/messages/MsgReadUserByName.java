package ms.messages;

import database.service.UserDbService;
import ms.messageSystem.Address;
import ms.messagesBase.MsgToDB;

public class MsgReadUserByName extends MsgToDB {

    private final String name;

    public MsgReadUserByName(Address from, Address to, String name) {
        super(from, to);
        this.name = name;
    }

    @Override
    public void exec(UserDbService dbService) {
        dbService.getMS().sendMessage(new MsgReadUserAnswer(getTo(), getFrom(),dbService.readByName(name) ));
    }
}
