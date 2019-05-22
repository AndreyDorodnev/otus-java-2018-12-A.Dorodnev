package messageSystem.messages;

import com.google.gson.Gson;
import database.model.UserDataSet;
import database.service.UserDbService;
import messageSystem.messageBase.MsgToDB;
import messageSystem.msBase.Address;
import webserver.sockets.MessageSocket;

public class MsgAddUser extends MsgToDB {

    private final String jsonStr;

    public MsgAddUser(Address from, Address to, String jsonStr) {
        super(from, to);
        this.jsonStr = jsonStr;
    }

    @Override
    public void exec(UserDbService dbService) {
        Gson gson = new Gson();
        String msg;
        try {
            UserDataSet user = gson.fromJson(jsonStr,UserDataSet.class);
            dbService.addUser(user);
            msg = "answer/addUser/true/add user success";
        }
        catch (Exception e){
            e.printStackTrace();
            msg = "answer/addUser/false/add user error!";
        }
        dbService.getMS().sendMessage(new MsgAnswer(getTo(), getFrom(), msg));
    }
}
