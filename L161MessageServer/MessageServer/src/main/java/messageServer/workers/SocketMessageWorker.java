package messageServer.workers;

import messageServer.annotations.Blocks;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import messageServer.messages.Message;
import messageServer.messages.MessageDbFs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketMessageWorker implements MessageWorker {

    private static final int WORKER_COUNT = 2;

    private final ExecutorService executorService;
    private final Socket socket;

    private final BlockingQueue<Message> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> input = new LinkedBlockingQueue<>();

    private final List<Runnable> closeObservers;

    public SocketMessageWorker(Socket socket) {
        this.socket = socket;
        executorService = Executors.newFixedThreadPool(WORKER_COUNT);
        this.closeObservers = new ArrayList<>();
    }

    public void init() {
        executorService.execute(this::sendMessage);
        executorService.execute(this::receiveMessage);
    }

    @Override
    public Message pool() {
        return input.poll();
    }

    @Override
    public void send(Message message) {
        output.add(message);
    }

    @Override
    public Message take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    @Blocks
    private void sendMessage(){
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)){
            while (socket.isConnected()){
                Message message = output.take();
                System.out.println("Send MSG:" + message.toString());
                String json = "";
                try{
                    json = new Gson().toJson(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
                out.println(json);
                out.println();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Blocks
    private void receiveMessage(){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while (socket.isConnected()) {
                while ((inputLine = reader.readLine()) != null) { //TODO тут есть проблема
                    stringBuilder.append(inputLine);
                    if (inputLine.isEmpty()) {
                        String json = stringBuilder.toString();
                        Message message = getMessageFromGson(json);
                        input.add(message);
                        stringBuilder = new StringBuilder();
                    }
                }
            }
        }  catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Message getMessageFromGson(String json) throws ClassNotFoundException {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(json);
//        //String className = String.valueOf(jsonObject.get(Message.CLASS_NAME_VARIABLE));
        Class<?> messageClass = MessageDbFs.class;
//
        return (Message) new Gson().fromJson(json, messageClass);
    }
}
