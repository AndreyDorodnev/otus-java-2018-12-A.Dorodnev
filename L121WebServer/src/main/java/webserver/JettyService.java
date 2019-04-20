package webserver;

import database.service.HibernateService;
import database.service.UserDbService;
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

    private final HibernateService hibernateService;
    private final TemplateProcessor tmplProcessor;
    private final ServletContextHandler context;
    private final Server server;

    public JettyService(HibernateService hibernateService,TemplateProcessor templateProcessor) {
        this.hibernateService = hibernateService;
        this.tmplProcessor = templateProcessor;
        this.context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        this.server = new Server(PORT);
    }

    public void config() throws IOException {
        context.addServlet(new ServletHolder(new LoginServlet
                (new UserDbService(hibernateService.getSessionFactory()))), "/login");
        context.addFilter(new FilterHolder(new AuthorizationFilter
                (new UserDbService(hibernateService.getSessionFactory()))), "/login", null);

        context.addServlet(new ServletHolder(new UserServlet
                (new UserDbService(hibernateService.getSessionFactory()),tmplProcessor)),"/user");
        context.addFilter(new FilterHolder(new AuthentificationUserFilter()),"/user",null);

        context.addServlet(new ServletHolder(new AddUserServlet
                (new UserDbService(hibernateService.getSessionFactory()),tmplProcessor)),"/admin/add");
        context.addServlet(new ServletHolder(new ReadUserServlet
                (new UserDbService(hibernateService.getSessionFactory()),tmplProcessor)),"/admin/user");
        context.addServlet(new ServletHolder(new DeleteUserServlet
                (new UserDbService(hibernateService.getSessionFactory()),tmplProcessor)),"/admin/delete");
        context.addServlet(new ServletHolder(new ReadAllUserServlet
                (new UserDbService(hibernateService.getSessionFactory()),tmplProcessor)),"/admin/all");
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

}
