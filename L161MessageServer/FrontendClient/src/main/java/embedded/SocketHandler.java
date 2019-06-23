package embedded;

import fs.FrontendService;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class SocketHandler extends WebSocketHandler {

    private final FrontendService frontendService;

    public SocketHandler(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.setCreator(new CustomWebSocketCreator(frontendService));
    }

}
