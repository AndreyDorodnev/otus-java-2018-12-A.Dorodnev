import database.DataBaseService;
import database.model.AddressDataSet;
import database.model.PhoneDataSet;
import database.model.UserDataSet;
import messageSystem.MessageSystemService;
import webserver.JettyService;
import webserver.template.TemplateProcessor;

import java.io.IOException;

public class MyWebServer {

    public void Start() {
        try{
            MessageSystemService msService = new MessageSystemService("database","frontend");
            DataBaseService dataBaseService = new DataBaseService(msService.getMsContext(),msService.getMsContext().getDbAddress(),
                    AddressDataSet.class, PhoneDataSet.class, UserDataSet.class);
            dataBaseService.createUsers();
            JettyService jettyService = new JettyService(msService.getMsContext(),
                    msService.getMsContext().getFrontAddress(),new TemplateProcessor());
            jettyService.config();
            msService.start();
            jettyService.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
