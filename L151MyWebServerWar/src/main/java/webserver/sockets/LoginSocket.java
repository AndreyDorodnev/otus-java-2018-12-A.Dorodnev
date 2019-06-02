package webserver.sockets;

import messageSystem.messages.*;
import messageSystem.msBase.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import webserver.FrontendService;

import javax.websocket.Session;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/")
public class LoginSocket implements WebSocketBase {

    private Session session;
    private final FrontendService frontendService;
    private final Integer id;

    public LoginSocket() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "SpringBeans.xml");
        frontendService = context.getBean("frontendService",FrontendService.class);
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
        if(cmd.equals("auth")){
            System.out.println("auth command start");
            authorization(lines[1],lines[2]);
        }
    }

    private void authorization(String login,String password){
        Message msg = new MsgCheckAuth(frontendService.getFrontAddress(), frontendService.getDbAddress(), login, password,id);
        frontendService.getMS().sendMessage(msg);
    }
}