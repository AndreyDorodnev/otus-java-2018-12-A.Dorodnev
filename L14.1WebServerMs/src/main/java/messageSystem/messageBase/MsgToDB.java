package messageSystem.messageBase;


import database.service.UserDbService;
import messageSystem.msBase.*;


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
