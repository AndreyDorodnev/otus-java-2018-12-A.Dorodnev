import database.DataBaseService;
import database.model.AddressDataSet;
import database.model.PhoneDataSet;
import database.model.UserDataSet;
import ms.MessageSystemService;
import webserver.JettyService;
import webserver.template.TemplateProcessor;

import java.io.IOException;

public class MyWebServer {

    public void Start() throws Exception {
        MessageSystemService msService = new MessageSystemService("database");
        DataBaseService dataBaseService = new DataBaseService(msService.getMsContext(),msService.getMsContext().getDbAddress(),
                AddressDataSet.class, PhoneDataSet.class, UserDataSet.class);
        dataBaseService.createUsers();
        JettyService jettyService = new JettyService(msService.getMsContext(),new TemplateProcessor());
        jettyService.config();
        msService.start();
        jettyService.start();
    }

}
