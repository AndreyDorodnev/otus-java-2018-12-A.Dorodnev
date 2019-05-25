package messageSystem.messages;

import database.service.UserDbService;
import messageSystem.messageBase.MsgToDB;
import messageSystem.msBase.Address;

public class MsgDeleteUser extends MsgToDB {

    private final String id;
    private  final Integer socketId;

    public MsgDeleteUser(Address from, Address to, String id, Integer socketId) {
        super(from, to);
        this.id = id;
        this.socketId = socketId;
    }

    @Override
    public void exec(UserDbService dbService) {
        String msg;
        if(dbService.deleteUserById(Long.valueOf(id)))
            msg = "answer/deleteUser/true/delete user success";
        else
           msg =  "answer/deleteUser/false/delete user error";
        dbService.getMS().sendMessage(new MsgAnswer(getTo(), getFrom(), msg,socketId));
    }
}
