package fs;

import com.google.gson.Gson;
import messages.Message;
import messages.MessageDbFs;
import sockets.LoginSocket;
import sockets.WebSocketBase;
import workers.SocketMessageWorker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FrontendService {

    private static final String HOST = "localhost";
    private static final int PORT = 5051;

    private final SocketMessageWorker client;
    private final Map<Integer, WebSocketBase> socketMap = new HashMap<>();
    private int socketCounter=0;

    public FrontendService() throws IOException {
        client = new ClientSocketMessageWorker(HOST, PORT);
        client.init();
        System.out.println("Start fs client ");
        start();
    }

    public Integer addSocket(WebSocketBase socket){
        socketMap.put(++socketCounter,socket);
        return socketCounter;
    }

    public WebSocketBase getSocket(Integer id){
        return socketMap.get(id);
    }

    public void deleteSocket(Integer id){
        socketMap.remove(id);
    }


    public void sendMessage(Message message){
        System.out.println("Client.send");
        client.send(message);
    }

    public void start(){
        ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.submit(() -> {
            while (true){
                Object msg = client.take();
                System.out.println("Message received: " + msg.toString());
                readMessage((MessageDbFs)msg);
            }
        });
    }

    private void readMessage(MessageDbFs message){
        this.getSocket(message.getWebSocketId()).answerCommand("answer/" + message.getCommand() + "/" + message.getData());
    }
}
