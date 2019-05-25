package messageSystem.messages;

import messageSystem.messageBase.MsgToFrontend;
import messageSystem.msBase.Address;
import webserver.FrontendService;

public class MsgAnswer extends MsgToFrontend {

    private final String msg;
    private final Integer socketId;

    public MsgAnswer(Address from, Address to,String msg, Integer socketId) {
        super(from, to);
        this.msg = msg;
        this.socketId = socketId;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.getSocket(socketId).answerCommand(msg);
    }
}
