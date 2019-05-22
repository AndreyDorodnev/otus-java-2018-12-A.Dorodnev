package webserver.handlers;

import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import webserver.FrontendService;

public class SocketHandler extends WebSocketHandler {

    private final FrontendService frontendService;

    public SocketHandler(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
//        webSocketServletFactory.register(MessageSocket.class);
        webSocketServletFactory.setCreator(new CustomWebSocketCreator(frontendService));
    }

}
