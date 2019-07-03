package front.embedded;

import front.fs.FrontendService;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class SocketHandler extends WebSocketHandler {

    private final FrontendService frontendService;
    private final int dbAddress;

    public SocketHandler(FrontendService frontendService,int dbAddress) {
        this.frontendService = frontendService;
        this.dbAddress = dbAddress;
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.setCreator(new CustomWebSocketCreator(frontendService,dbAddress));
    }

}
