package messageSystem.messages;

import messageSystem.messageBase.MsgToFrontend;
import messageSystem.msBase.Address;
import webserver.FrontendService;
import webserver.sockets.MessageSocket;

public class MsgAnswer extends MsgToFrontend {

    private final String msg;

    public MsgAnswer(Address from, Address to,String msg) {
        super(from, to);
        this.msg = msg;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.getSocket().onText(msg);
    }
}
