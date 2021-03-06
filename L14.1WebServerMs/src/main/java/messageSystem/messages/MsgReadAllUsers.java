package messageSystem.messages;

import com.google.gson.Gson;
import database.model.UserDataSet;
import database.service.UserDbService;
import messageSystem.messageBase.MsgToDB;
import messageSystem.msBase.Address;

import java.util.List;

public class MsgReadAllUsers extends MsgToDB {

    private  final Integer socketId;

    public MsgReadAllUsers(Address from, Address to, Integer socketId) {
        super(from, to);
        this.socketId = socketId;
    }

    @Override
    public void exec(UserDbService dbService) {
        List<UserDataSet> users = dbService.readAll();
        String msg;
        if(users!=null){
            Gson gson = new Gson();
            msg = "answer/readAll/true/" + gson.toJson(users);
        }
        else {
            msg = "answer/readAll/false/no users found";
        }
        dbService.getMS().sendMessage(new MsgAnswer(getTo(), getFrom(), msg,socketId));
    }
}
