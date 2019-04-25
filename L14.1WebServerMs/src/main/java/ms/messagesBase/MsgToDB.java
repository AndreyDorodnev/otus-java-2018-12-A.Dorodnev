package ms.messagesBase;

import database.service.DBServiceHibernate;
import database.service.UserDbService;
import ms.messageSystem.Address;
import ms.messageSystem.Addressee;
import ms.messageSystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof UserDbService) {
            exec((UserDbService) addressee);
        }
    }

    public abstract void exec(UserDbService dbService);
}