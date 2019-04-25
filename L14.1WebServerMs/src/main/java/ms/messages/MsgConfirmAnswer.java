package ms.messages;

import ms.messageSystem.Address;
import ms.messageSystem.Addressee;
import ms.messagesBase.MsgToFrontend;
import webserver.servlets.AddUserServlet;
import webserver.servlets.DeleteUserServlet;

public class MsgConfirmAnswer extends MsgToFrontend {

    private final boolean confirm;

    public MsgConfirmAnswer(Address from, Address to, boolean confirm) {
        super(from, to);
        this.confirm = confirm;
    }


    @Override
    public void execAdr(Addressee addressee) throws Exception {
        if(addressee instanceof AddUserServlet){
            execAdr((AddUserServlet)addressee);
        }
        else if(addressee instanceof DeleteUserServlet){
            execAdr((DeleteUserServlet)addressee);
        }
    }

    public void execAdr(AddUserServlet addUserServlet) throws Exception {
        if(confirm)
            addUserServlet.setConfirm(true);
        else
            addUserServlet.setConfirm(false);
        addUserServlet.setAnswerReady(true);
    }

    public void execAdr(DeleteUserServlet deleteUserServlet) throws Exception {
        if(confirm)
            deleteUserServlet.setConfirm(true);
        else
            deleteUserServlet.setConfirm(false);
        deleteUserServlet.setAnswerReady(true);
    }
}
