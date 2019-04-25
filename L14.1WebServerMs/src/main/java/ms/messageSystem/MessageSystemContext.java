package ms.messageSystem;

import java.util.HashMap;
import java.util.Map;

public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Map<String,Address> frontAddresses;
    private Address dbAddress;

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        frontAddresses = new HashMap<>();
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress(String id) {
        return frontAddresses.get(id);
    }

    public void setFrontAddress(String id,Address frontAddress) {
        frontAddresses.put(id,frontAddress);
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }


}