package webserver.sockets;

import messageSystem.messages.*;
import messageSystem.msBase.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import webserver.FrontendService;

import javax.websocket.Session;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;


@ServerEndpoint("/admin")
public class AdminSocket implements WebSocketBase {

    @Autowired
    private FrontendService frontendService;
    private Session session;
    private final Integer id;

    public AdminSocket() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.id = frontendService.addSocket(this);
    }

    @OnOpen
    public void OnOpen(Session session){
        try {
            this.session = session;
            session.getBasicRemote().sendText("Connect message");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String txt, Session session) throws IOException {
        try {
            processCommand(txt);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void answerCommand(String message){
        try {
            this.session.getBasicRemote().sendText(message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(CloseReason reason, Session session){
        frontendService.deleteSocket(id);
        System.out.println("Close!");
    }

    private void processCommand(String message) throws IOException {
        String[] lines = message.split("/");
        String cmd = lines[0];
        switch (cmd){
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