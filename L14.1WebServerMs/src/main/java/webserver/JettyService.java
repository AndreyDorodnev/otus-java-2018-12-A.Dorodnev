package webserver;

import messageSystem.msBase.Address;
import messageSystem.msBase.MessageSystemContext;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import webserver.handlers.SocketHandler;
import webserver.servlets.*;
import webserver.template.TemplateProcessor;

import java.io.IOException;

public class JettyService {

    private final static int PORT = 8080;
    private final static String STATIC = "/static";

    private final TemplateProcessor tmplProcessor;
    private final ServletContextHandler context;
    private final Server server;

    private final MessageSystemContext msContext;
    private final FrontendService frontendService;

    public JettyService(MessageSystemContext msContext, Address address, TemplateProcessor templateProcessor) {
        this.msContext = msContext;
        this.tmplProcessor = templateProcessor;
        this.context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        this.server = new Server(PORT);
        frontendService = new FrontendService(msContext,address);
    }

    public void config() throws IOException {
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new UserServlet(tmplProcessor)),"/user");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {new SocketHandler(this.frontendService), getResourceHandler(),context});
        server.setHandler(handlers);
    }

    private ResourceHandler getResourceHandler(){
        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(STATIC);
        resourceHandler.setBaseResource(resource);
        return resourceHandler;
    }

    public void start() throws Exception {
        server.start();
        server.join();
    }

}
