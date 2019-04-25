package webserver;

import ms.messageSystem.MessageSystemContext;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import webserver.auth.AuthentificationAdminFilter;
import webserver.auth.AuthentificationUserFilter;
import webserver.auth.AuthorizationFilter;
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

    public JettyService(MessageSystemContext msContext,TemplateProcessor templateProcessor) {
        this.msContext = msContext;
        this.tmplProcessor = templateProcessor;
        this.context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        this.server = new Server(PORT);
    }

    public void config() throws IOException {
        context.addServlet(new ServletHolder(new LoginServlet(msContext)), "/login");
        context.addFilter(new FilterHolder(new AuthorizationFilter(msContext)), "/login", null);

        context.addServlet(new ServletHolder(new UserServlet(msContext,tmplProcessor)),"/user");
        context.addFilter(new FilterHolder(new AuthentificationUserFilter()),"/user",null);
        context.addServlet(new ServletHolder(new AddUserServlet(msContext,tmplProcessor)),"/admin/add");
        context.addServlet(new ServletHolder(new ReadUserServlet(msContext,tmplProcessor)),"/admin/user");
        context.addServlet(new ServletHolder(new DeleteUserServlet(msContext,tmplProcessor)),"/admin/delete");
        context.addServlet(new ServletHolder(new ReadAllUserServlet(msContext,tmplProcessor)),"/admin/all");
        context.addFilter(new FilterHolder(new AuthentificationAdminFilter()),"/admin/*",null);
    }

    private ResourceHandler getResourceHandler(){
        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(STATIC);
        resourceHandler.setBaseResource(resource);
        return  resourceHandler;
    }

    public void start() throws Exception {
        server.setHandler(new HandlerList(getResourceHandler(),context));
        server.start();
        server.join();
    }

    public ServletContextHandler getContext() {
        return context;
    }
}
