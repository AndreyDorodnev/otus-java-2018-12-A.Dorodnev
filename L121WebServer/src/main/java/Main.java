import database.model.*;
import database.service.DBServiceHibernate;
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
import webserver.servlets.UserServlet;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private final static int PORT = 8080;
    private final static String STATIC = "/static";

    public static void main(String[] args) {
        try{
            HibernateService hibernateService = new HibernateService(AddressDataSet.class,PhoneDataSet.class,UserDataSet.class);
            createUsers(hibernateService);
            start(hibernateService);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static void createUsers(HibernateService hibernateService){
        try{

            DBServiceHibernate dbservice = new DBServiceHibernate(hibernateService.getSessionFactory());

            List<PhoneDataSet> phones = new ArrayList<>();
            phones.add(new PhoneDataSet("123-456"));
            phones.add(new PhoneDataSet("987-654"));
            dbservice.save(new UserDataSet("admin","12345",27,new AddressDataSet("New York"),Roles.ADMIN,phones));

            List<PhoneDataSet> phones3 = new ArrayList<>();
            phones3.add(new PhoneDataSet("000-001"));
            phones3.add(new PhoneDataSet("000-002"));
            dbservice.save(new UserDataSet("user1","321",34,new AddressDataSet("Moscow"),Roles.USER,phones3));

            List<PhoneDataSet> phones2 = new ArrayList<>();
            phones2.add(new PhoneDataSet("000-004"));
            phones2.add(new PhoneDataSet("000-005"));
            dbservice.save(new UserDataSet("user2","321",40,new AddressDataSet("London"),Roles.USER,phones2));

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static void start(HibernateService hibernateService) throws Exception {

        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(STATIC);
        resourceHandler.setBaseResource(resource);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new LoginServlet(new UserDbService(hibernateService.getSessionFactory()))), "/login");
        context.addFilter(new FilterHolder(new AuthorizationFilter(new UserDbService(hibernateService.getSessionFactory()))), "/login", null);

        context.addServlet(new ServletHolder(new UserServlet(new UserDbService(hibernateService.getSessionFactory()))),"/user");
        context.addFilter(new FilterHolder(new AuthentificationUserFilter()),"/user",null);

        context.addServlet(new ServletHolder(new AddUserServlet(new UserDbService(hibernateService.getSessionFactory()))),"/admin-add");
        context.addFilter(new FilterHolder(new AuthentificationAdminFilter()),"/admin-add",null);
        context.addServlet(new ServletHolder(new ReadUserServlet
                (new UserDbService(hibernateService.getSessionFactory()))),"/admin-user");
        context.addFilter(new FilterHolder(new AuthentificationAdminFilter()),"/admin-user",null);
        context.addServlet(new ServletHolder(new DeleteUserServlet
                (new UserDbService(hibernateService.getSessionFactory()))),"/admin-delete");
        context.addFilter(new FilterHolder(new AuthentificationAdminFilter()),"/admin-delete",null);
        context.addServlet(new ServletHolder(new ReadAllUserServlet
                (new UserDbService(hibernateService.getSessionFactory()))),"/admin-all");
        context.addFilter(new FilterHolder(new AuthentificationAdminFilter()),"/admin-all",null);

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler,context));
        server.start();
        server.join();
    }
}
