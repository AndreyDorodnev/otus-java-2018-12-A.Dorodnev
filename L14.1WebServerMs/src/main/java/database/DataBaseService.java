package database;

import database.model.AddressDataSet;
import database.model.PhoneDataSet;
import database.model.Roles;
import database.model.UserDataSet;
import database.service.DBServiceHibernate;
import database.service.HibernateService;
import database.service.UserDbService;
import messageSystem.msBase.Address;
import messageSystem.msBase.MessageSystemContext;

import java.util.ArrayList;
import java.util.List;

public class DataBaseService {

    HibernateService hibernateService;
    private final UserDbService userDbService;

    public DataBaseService(MessageSystemContext msContext, Address address, Class...classes){
        hibernateService = new HibernateService(classes);
        userDbService = new UserDbService(msContext,address,hibernateService.getSessionFactory());
    }

    public HibernateService getHibernateService(){
        return hibernateService;
    }

    public void createUsers(){
        try{
            DBServiceHibernate dbservice = new DBServiceHibernate(hibernateService.getSessionFactory());

            List<PhoneDataSet> phones = new ArrayList<>();
            phones.add(new PhoneDataSet("123-456"));
            phones.add(new PhoneDataSet("987-654"));
            dbservice.save(new UserDataSet("admin","12345",27,new AddressDataSet("New York"), Roles.ADMIN,phones));

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

}
