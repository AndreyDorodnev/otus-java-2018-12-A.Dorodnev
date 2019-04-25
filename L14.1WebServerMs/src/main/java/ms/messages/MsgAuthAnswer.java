package ms.messages;

import database.model.Roles;
import ms.messageSystem.Address;
import ms.messageSystem.Addressee;
import ms.messagesBase.MsgToFrontend;
import webserver.auth.AuthorizationFilter;

public class MsgAuthAnswer extends MsgToFrontend {
    private final boolean isAuthorized;
    private final Roles role;

    public MsgAuthAnswer(Address from, Address to, boolean isAuthorized, Roles role) {
        super(from, to);
        this.role = role;
        this.isAuthorized = isAuthorized;
    }

    @Override
    public void execAdr(Addressee addressee) throws Exception {
        if(addressee instanceof AuthorizationFilter){
            AuthorizationFilter aFilter = (AuthorizationFilter)addressee;
            if(isAuthorized){
                aFilter.setRole(role);
                aFilter.setAuthorized(true);
                aFilter.setAnswerReady(true);
            }
            else {
                aFilter.setAuthorized(false);
                aFilter.setAnswerReady(true);
            }
        }
    }

//    @Override
//    public void exec(AuthorizationFilter frontendService) throws Exception {
//        if(isAuthorized){
//            frontendService.setRole(role);
//            frontendService.setAuthorized(true);
////            frontendService.getContext().getServletHandler().getServlets()[0].doStart();
//        }
////        frontendService.addUser(id, name);
//    }
}
