package sockets;
import com.google.gson.Gson;
import fs.FrontendService;
import messages.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebSocket
public class LoginSocket implements WebSocketBase {

    private Session session;
    @Autowired
    private FrontendService frontendService;
    private Integer id;

    public LoginSocket(FrontendService frontendService) {
        this.frontendService = frontendService;
        this.id = frontendService.addSocket(this);
    }

    @OnWebSocketConnect
    public void OnConnect(Session session){
        try {
            this.session = session;
            session.getRemote().sendString("Connect message");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onText(String message){
        try {
            processCommand(message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void answerCommand(String message){
        try {
            this.session.getRemote().sendString(message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode,String reason){
        frontendService.deleteSocket(id);
        System.out.println("Close!");
    }

    private void processCommand(String message) throws IOException {
        String[] lines = message.split("/");
        String cmd = lines[0];
        switch (cmd){
            case "auth":
                if(lines.length>2)
                    authorization(lines[1],lines[2]);
                break;
            case "readUser":
                if(lines.length>1)
                    readUserById(lines[1]);
                break;
            case "deleteUser":
                if(lines.length>1)
                    deleteUserById(lines[1]);
                break;
            case "readAll":
                readAllUsers();
                break;
            case "addUser":
                if(lines.length>1)
                    addUser(lines[1]);
                break;
        }

    }

    private void authorization(String login,String password){
        System.out.println("auth command start");
        MsgCheckAuth msg = new MsgCheckAuth(login,password);
        MessageDbFs sendMessage = new MessageDbFs(null, MessageCommand.AUTH,id,new Gson().toJson(msg));
        frontendService.sendMessage(sendMessage);
    }


    private void readUserById(String userId){
        MsgReadUser msg = new MsgReadUser(userId);
        MessageDbFs sendMessage = new MessageDbFs(null, MessageCommand.READ_BY_ID,id,new Gson().toJson(msg));
        frontendService.sendMessage(sendMessage);
    }

    private void deleteUserById(String userId){
        MsgDelUser msg = new MsgDelUser(userId);
        MessageDbFs sendMessage = new MessageDbFs(null, MessageCommand.DELETE_BY_ID,id,new Gson().toJson(msg));
        frontendService.sendMessage(sendMessage);
    }

    private void readAllUsers(){
        MessageDbFs sendMessage = new MessageDbFs(null,MessageCommand.READ_ALL,id,null);
        frontendService.sendMessage(sendMessage);
    }

    private void addUser(String jsonStr){
        MessageDbFs sendMessage = new MessageDbFs(null,MessageCommand.ADD_USER,id,jsonStr);
        frontendService.sendMessage(sendMessage);
    }

}