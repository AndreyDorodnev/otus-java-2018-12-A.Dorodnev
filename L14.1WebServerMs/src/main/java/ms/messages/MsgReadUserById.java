package ms.messages;

import database.service.UserDbService;
import ms.messageSystem.Address;
import ms.messagesBase.MsgToDB;

public class MsgReadUserById extends MsgToDB {
    private final String id;

    public MsgReadUserById(Address from, Address to, String id) {
        super(from, to);
        this.id = id;
    }

    @Override
    public void exec(UserDbService dbService) {
        dbService.getMS().sendMessage(new MsgReadUserAnswer(getTo(), getFrom(),dbService.readUserById(Long.valueOf(id))));
    }
}
