package messageSystem.messages;

import com.google.gson.Gson;
import database.model.AddressDataSet;
import database.model.UserDataSet;
import database.service.UserDbService;
import messageSystem.messageBase.MsgToDB;
import messageSystem.msBase.Address;
import webserver.sockets.MessageSocket;

public class MsgReadUser extends MsgToDB {

    private final String id;
    private  final MessageSocket socket;

    public MsgReadUser(Address from, Address to,String id,MessageSocket socket) {
        super(from, to);
        this.id = id;
        this.socket = socket;
    }

    @Override
    public void exec(UserDbService dbService) {
        UserDataSet user = dbService.readUserById(Long.valueOf(id));
        String msg;
        if(user!=null){
            Gson gson = new Gson();
            msg = "answer/readUser/true/" + gson.toJson(user);
        }
        else {
            msg = "answer/readUser/false/no user with id " + id;
        }
        dbService.getMS().sendMessage(new MsgAnswer(getTo(), getFrom(), msg));
    }
}
