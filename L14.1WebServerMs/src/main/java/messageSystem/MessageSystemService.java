package messageSystem;

import messageSystem.msBase.*;

public class MessageSystemService {
    private  final MessageSystem messageSystem;
    private final MessageSystemContext context;
    private Address dbAddress;

    public MessageSystemService(String databaseId, String frontendId) {
        messageSystem = new MessageSystem();
        context = new MessageSystemContext(messageSystem);
        dbAddress = new Address(databaseId);
        context.setDbAddress(dbAddress);
        Address frontAddress = new Address(frontendId);
        context.setFrontAddress(frontAddress);
    }

    public void start(){
        messageSystem.start();
    }

    public MessageSystemContext getMsContext(){
        return context;
    }
}
