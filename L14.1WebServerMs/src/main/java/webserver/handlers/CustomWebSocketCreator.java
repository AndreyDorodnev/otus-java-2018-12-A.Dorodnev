package webserver.handlers;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import webserver.FrontendService;
import webserver.sockets.MessageSocket;

public class CustomWebSocketCreator implements WebSocketCreator {

    private final FrontendService frontendService;

    public CustomWebSocketCreator(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        return new MessageSocket(frontendService);
    }
}
