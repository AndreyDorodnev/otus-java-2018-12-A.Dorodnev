package webserver;

import messageSystem.msBase.Address;
import messageSystem.msBase.Addressee;
import messageSystem.msBase.MessageSystem;
import messageSystem.msBase.MessageSystemContext;
import webserver.sockets.MessageSocket;

public class FrontendService implements Addressee {

    private final MessageSystemContext msContext;
    private final Address address;
    private MessageSocket socket;

    public FrontendService(MessageSystemContext msContext, Address address) {

        this.msContext = msContext;
        this.address = address;
        msContext.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return msContext.getMessageSystem();
    }

    public MessageSocket getSocket() {
        return socket;
    }

    public void setSocket(MessageSocket socket) {
        this.socket = socket;
    }

    public Address getFrontAddress(){
        return msContext.getFrontAddress();
    }

    public Address getDbAddress(){
        return msContext.getDbAddress();
    }

}
