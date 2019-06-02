package database;

import database.model.AddressDataSet;
import database.model.PhoneDataSet;
import database.model.Roles;
import database.model.UserDataSet;
import database.service.UserDbService;

import java.util.ArrayList;
import java.util.List;

public class DataBaseService {
    private UserDbService userDbService;

    public void createUsers(){
        try{
            List<PhoneDataSet> phones = new ArrayList<>();
            phones.add(new PhoneDataSet("123-456"));
            phones.add(new PhoneDataSet("987-654"));
            userDbService.addUser(new UserDataSet("admin","12345",27,new AddressDataSet("New York"), Roles.ADMIN,phones));

            List<PhoneDataSet> phones3 = new ArrayList<>();
            phones3.add(new PhoneDataSet("000-001"));
            phones3.add(new PhoneDataSet("000-002"));
            userDbService.addUser(new UserDataSet("user1","321",34,new AddressDataSet("Moscow"),Roles.USER,phones3));

            List<PhoneDataSet> phones2 = new ArrayList<>();
            phones2.add(new PhoneDataSet("000-004"));
            phones2.add(new PhoneDataSet("000-005"));
            userDbService.addUser(new UserDataSet("user2","321",40,new AddressDataSet("London"),Roles.USER,phones2));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void setUserDbService(UserDbService userDbService) {
        this.userDbService = userDbService;
    }

}
