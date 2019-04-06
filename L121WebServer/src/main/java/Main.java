import database.model.*;
import database.service.DBServiceHibernate;
import database.service.HibernateService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import webserver.servlets.LoginServlet;

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
            dbservice.save(new UserDataSet("user1",27,new AddressDataSet("New York"),phones));

            List<PhoneDataSet> phones2 = new ArrayList<>();
            phones2.add(new PhoneDataSet("000-001"));
            phones2.add(new PhoneDataSet("000-002"));
            dbservice.save(new UserDataSet("user2",40,new AddressDataSet("London"),phones2));
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

//        context.addServlet(new ServletHolder(new UserServlet(userDao(), gson())), "/user");
        context.addServlet(new ServletHolder(new LoginServlet(new DBServiceHibernate(hibernateService.getSessionFactory()))), "/webserver/servlets");
        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler,context));

        server.start();
        server.join();
    }
}
