package messageSystem;

import messageSystem.msBase.*;

public class MessageSystemService {
    private  final MessageSystem messageSystem;
    private final MessageSystemContext context;
    private Address dbAddress;
    private Address frontAddress;

    public MessageSystemService(String databaseId, String frontendId) {
        messageSystem = new MessageSystem();
        context = new MessageSystemContext(messageSystem);
        dbAddress = new Address(databaseId);
        context.setDbAddress(dbAddress);
        frontAddress = new Address(frontendId);
        context.setFrontAddress(frontAddress);
    }

    public void start(){
        messageSystem.start();
    }

    public MessageSystemContext getMsContext(){
        return context;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }
}
