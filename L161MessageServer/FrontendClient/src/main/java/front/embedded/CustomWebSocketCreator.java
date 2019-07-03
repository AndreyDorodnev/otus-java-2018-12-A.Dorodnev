package front.embedded;

import front.fs.FrontendService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import front.sockets.LoginSocket;

public class CustomWebSocketCreator implements WebSocketCreator {

    private final FrontendService frontendService;
    private final int dbAddress;

    public CustomWebSocketCreator(FrontendService frontendService,int dbAddress) {
        this.frontendService = frontendService;
        this.dbAddress = dbAddress;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        return new LoginSocket(frontendService,dbAddress);
    }
}
