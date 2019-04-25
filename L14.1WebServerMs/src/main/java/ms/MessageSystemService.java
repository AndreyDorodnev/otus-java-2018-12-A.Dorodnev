package ms;

import ms.messageSystem.MessageSystem;
import ms.messageSystem.MessageSystemContext;
import ms.messageSystem.Address;

public class MessageSystemService {

    private  final MessageSystem messageSystem;
    private final MessageSystemContext context;
    private Address dbAddress;

    public MessageSystemService(String databaseId) {
        messageSystem = new MessageSystem();
        context = new MessageSystemContext(messageSystem);
        dbAddress = new Address(databaseId);
        context.setDbAddress(dbAddress);
    }

//    public void config(){
//        dbAddress = new Address(databaseId);
//        context.setDbAddress(dbAddress);
//    }

    public void start(){
        messageSystem.start();
    }

    public MessageSystemContext getMsContext(){
        return context;
    }

}
