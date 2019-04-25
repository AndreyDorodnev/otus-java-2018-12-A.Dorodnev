package ms.messages;

import database.service.UserDbService;
import ms.messageSystem.Address;
import ms.messagesBase.MsgToDB;

public class MsgDeleteUser extends MsgToDB {

    private final String id;

    public MsgDeleteUser(Address from, Address to, String id) {
        super(from, to);
        this.id = id;
    }

    @Override
    public void exec(UserDbService dbService) {
        dbService.getMS().sendMessage(new MsgConfirmAnswer(getTo(), getFrom(),dbService.deleteUserById(Long.valueOf(id))));
    }
}
