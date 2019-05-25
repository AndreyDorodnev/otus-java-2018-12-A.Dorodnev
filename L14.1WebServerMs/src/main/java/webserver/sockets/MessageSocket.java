package webserver.sockets;

import messageSystem.messages.*;
import messageSystem.msBase.Message;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webserver.FrontendService;

import java.io.IOException;

@WebSocket
public class MessageSocket {

    private Session session;
    private final FrontendService frontendService;
    private final Integer id;

    public MessageSocket(FrontendService frontendService) {
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
                authorization(lines[1],lines[2]);
                break;
            case "readUser":
                readUserById(lines[1]);
                break;
            case "deleteUser":
                deleteUserById(lines[1]);
                break;
            case "readAll":
                readAllUsers();
                break;
            case "addUser":
                addUser(lines[1]);
                break;
        }
    }

    private void authorization(String login,String password){
        Message msg = new MsgCheckAuth(frontendService.getFrontAddress(), frontendService.getDbAddress(), login, password,id);
        frontendService.getMS().sendMessage(msg);
    }

    private void readUserById(String userId){
        Message msg = new MsgReadUser(frontendService.getFrontAddress(),frontendService.getDbAddress(),userId,id);
        frontendService.getMS().sendMessage(msg);
    }

    private void deleteUserById(String userId){
        Message msg = new MsgDeleteUser(frontendService.getFrontAddress(),frontendService.getDbAddress(),userId,id);
        frontendService.getMS().sendMessage(msg);
    }

    private void readAllUsers(){
        Message msg = new MsgReadAllUsers(frontendService.getFrontAddress(),frontendService.getDbAddress(),id);
        frontendService.getMS().sendMessage(msg);
    }

    private void addUser(String jsonStr){
        Message msg = new MsgAddUser(frontendService.getFrontAddress(),frontendService.getDbAddress(),jsonStr,id);
        frontendService.getMS().sendMessage(msg);
    }

}
