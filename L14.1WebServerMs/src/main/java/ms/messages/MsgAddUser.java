package ms.messages;

import database.model.UserDataSet;
import database.service.UserDbService;
import ms.messageSystem.Address;
import ms.messagesBase.MsgToDB;

public class MsgAddUser extends MsgToDB {

    private final UserDataSet user;

    public MsgAddUser(Address from, Address to, UserDataSet user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void exec(UserDbService dbService) {
        if(dbService.addUser(user)){
            dbService.getMS().sendMessage(new MsgConfirmAnswer(getTo(),getFrom(),true));
        }
        else {
            dbService.getMS().sendMessage(new MsgConfirmAnswer(getTo(),getFrom(),false));
        }
    }
}
