package database;

import com.google.gson.Gson;
import database.ds.ClientSocketMessageWorker;
import messageServer.messages.*;
import database.model.AddressDataSet;
import database.model.PhoneDataSet;
import database.model.Roles;
import database.model.UserDataSet;
import database.service.UserDbService;
import messageServer.workers.SocketMessageWorker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataBaseService {

    private static final String HOST = "localhost";
    private static final int PORT = 5052;

    private SocketMessageWorker client;
    private UserDbService userDbService;

    public DataBaseService(UserDbService userDbService) throws IOException {
        this.userDbService = userDbService;
//        client = new ClientSocketMessageWorker(HOST, PORT);
//        client.init();
//        System.out.println("Start database.ds client ");
    }

    public DataBaseService() throws IOException {
//        client = new ClientSocketMessageWorker(HOST, PORT);
//        client.init();
//        System.out.println("Start database.ds client ");
    }

    public void init(int port) throws IOException {
        client = new ClientSocketMessageWorker(HOST, port);
        client.init();
        System.out.println("Start database.ds client ");
    }

    public void createUsers(){
        try{
            List<PhoneDataSet> phones = new ArrayList<>();
            phones.add(new PhoneDataSet("123-456"));
            phones.add(new PhoneDataSet("987-654"));
            userDbService.addUser(new UserDataSet("admin","12345",27,new AddressDataSet("New York"), Roles.ADMIN,phones));

            List<PhoneDataSet> phones3 = new ArrayList<>();
            phones3.add(new PhoneDataSet("000-001"));
            phones3.add(new PhoneDataSet("000-002"));
            userDbService.addUser(new UserDataSet("user1","321",34,new AddressDataSet("Moscow"),Roles.USER,phones3));

            List<PhoneDataSet> phones2 = new ArrayList<>();
            phones2.add(new PhoneDataSet("000-004"));
            phones2.add(new PhoneDataSet("000-005"));
            userDbService.addUser(new UserDataSet("user2","321",40,new AddressDataSet("London"), Roles.USER,phones2));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void setUserDbService(UserDbService userDbService) {
        this.userDbService = userDbService;
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
                answerMessage(msg);
            }
        });
    }

    private void answerMessage(Object msg){
        try {
            MessageDbFs inputMessage = (MessageDbFs)msg;
            MessageDbFs outputMessage = new MessageDbFs();
            outputMessage.setDbAddress(inputMessage.getDbAddress());
            outputMessage.setRemoteAddr(inputMessage.getRemoteAddr());
            outputMessage.setCommand(inputMessage.getCommand());
            outputMessage.setWebSocketId(inputMessage.getWebSocketId());
            switch (inputMessage.getCommand()){
                case AUTH:
                    authAnswer(inputMessage,outputMessage);
                    break;
                case READ_BY_ID:
                    readUserAnswer(inputMessage,outputMessage);
                    break;
                case READ_ALL:
                    readAllAnswer(inputMessage,outputMessage);
                    break;
                case DELETE_BY_ID:
                    deleteUserAnswer(inputMessage,outputMessage);
                    break;
                case ADD_USER:
                    addUserAnswer(inputMessage,outputMessage);
                    break;
            }
            System.out.println("SEND MSG: " + outputMessage.toString() + " command: " + outputMessage.getCommand() + " data: " + outputMessage.getData());
            client.send(outputMessage);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void authAnswer(MessageDbFs inputMessage, MessageDbFs outputMessage){
        String login = inputMessage.getData().substring(0,inputMessage.getData().indexOf('/'));
        String password = inputMessage.getData().substring(inputMessage.getData().indexOf('/')+1,inputMessage.getData().length());
        if(userDbService.authenticate(login,password)){
            outputMessage.setData("true/" + userDbService.getRoleByName(login).toString());
        }else {
            outputMessage.setData("false");
        }
    }

    private void readUserAnswer(MessageDbFs inputMessage, MessageDbFs outputMessage){
        String id = inputMessage.getData();
        UserDataSet user = userDbService.readUserById(Integer.valueOf(id));
        if(user!=null)
            outputMessage.setData("true/" + new Gson().toJson(user));
        else
            outputMessage.setData("false");
    }

    private void readAllAnswer(MessageDbFs inputMessage, MessageDbFs outputMessage){
        List<UserDataSet> users = userDbService.readAll();
        if(users!=null)
            outputMessage.setData("true/" + new Gson().toJson(users));
        else
            outputMessage.setData("false");
    }

    private void deleteUserAnswer(MessageDbFs inputMessage, MessageDbFs outputMessage){
        String id = inputMessage.getData();
        if(userDbService.deleteUserById(Integer.valueOf(id)))
            outputMessage.setData("true");
        else
            outputMessage.setData("false");
    }

    private void addUserAnswer(MessageDbFs inputMessage, MessageDbFs outputMessage){
        UserDataSet createUser = new Gson().fromJson(inputMessage.getData(),UserDataSet.class);
        if(userDbService.addUser(createUser))
            outputMessage.setData("true");
        else
            outputMessage.setData("false");
    }

}
