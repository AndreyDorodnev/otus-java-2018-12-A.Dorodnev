package messageSystem.messages;

import database.service.UserDbService;
import messageSystem.messageBase.MsgToDB;
import messageSystem.msBase.Address;
import webserver.sockets.MessageSocket;

public class MsgDeleteUser extends MsgToDB {

    private final String id;
    private  final MessageSocket socket;

    public MsgDeleteUser(Address from, Address to, String id, MessageSocket socket) {
        super(from, to);
        this.id = id;
        this.socket = socket;
    }

    @Override
    public void exec(UserDbService dbService) {
        String msg;
        if(dbService.deleteUserById(Long.valueOf(id)))
            msg = "answer/deleteUser/true/delete user success";
        else
           msg =  "answer/deleteUser/false/delete user error";
        dbService.getMS().sendMessage(new MsgAnswer(getTo(), getFrom(), msg));
    }
}
