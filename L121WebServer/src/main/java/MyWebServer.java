import database.DataBaseService;
import database.model.AddressDataSet;
import database.model.PhoneDataSet;
import database.model.UserDataSet;
import webserver.JettyService;
import webserver.template.TemplateProcessor;

import java.io.IOException;

public class MyWebServer {

    public void Start() throws Exception {

        DataBaseService dataBaseService = new DataBaseService(AddressDataSet.class, PhoneDataSet.class, UserDataSet.class);
        dataBaseService.createUsers();
        JettyService jettyService = new JettyService(dataBaseService.getHibernateService(),new TemplateProcessor());
        jettyService.config();
        jettyService.start();
    }

}
