package front.embedded;

import front.filters.AuthFilter;
import front.fs.FrontendService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import front.servlets.AdminServlet;
import front.servlets.UserServlet;

import java.io.IOException;


public class JettyService {

    private final static int PORT = 8080;
    private final static String STATIC = "/static";

    private final ServletContextHandler context;
    private final Server server;
    private final FrontendService frontendService;
    private int dbAddress;

    public JettyService() throws IOException {
        this.context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        this.server = new Server(PORT);
        this.frontendService = new FrontendService();
    }

    public JettyService(int port,int dbAddress) throws IOException {
        this.context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        this.server = new Server(port);
        this.frontendService = new FrontendService();
        this.dbAddress = dbAddress;
    }

    public void config() throws IOException {
        context.addServlet(new ServletHolder(new AdminServlet()),"/admin");
        context.addServlet(new ServletHolder(new UserServlet()),"/user");
        context.addFilter(new FilterHolder(new AuthFilter()),"/admin",null);
        context.addFilter(new FilterHolder(new AuthFilter()),"/user",null);
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {new SocketHandler(frontendService,dbAddress), getResourceHandler(),context});
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
