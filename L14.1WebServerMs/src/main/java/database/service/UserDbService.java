package database.service;

import database.model.Roles;
import database.model.UserDataSet;
import messageSystem.msBase.Address;
import messageSystem.msBase.Addressee;
import messageSystem.msBase.MessageSystem;
import messageSystem.msBase.MessageSystemContext;
import org.eclipse.jetty.server.Authentication;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDbService implements Addressee {

    private final DBServiceHibernate dbService;
    private final MessageSystemContext msContext;
    private final Address address;

//    public UserDbService(SessionFactory sessionFactory) {
////        this.dbService = new DBServiceHibernate(sessionFactory);
////    }
    public UserDbService(MessageSystemContext msContext, Address address, SessionFactory sessionFactory) {
        this.dbService = new DBServiceHibernate(sessionFactory);
        this.msContext = msContext;
        this.address = address;
        msContext.getMessageSystem().addAddressee(this);
    }

    public boolean addUser(UserDataSet user){
        try {
            dbService.save(user);
            return true;
        }
        catch (Exception ex){
            return false;
        }

    }

    public UserDataSet readUserById(long id){
        try {
            return dbService.read(id,UserDataSet.class);
        }
        catch (Exception ex){
            return null;
        }

    }

    public boolean deleteUserById(long id){
        if(dbService.deleteById(Long.valueOf(id),UserDataSet.class))
            return true;
        return false;
    }

    public List<UserDataSet> readAll(){
        try {
            return dbService.readAll(UserDataSet.class);
        }
        catch (Exception ex){
            return null;
        }
    }

    public UserDataSet readByName(String name){

        try {
            return dbService.readByName(name,UserDataSet.class);
        }
        catch (Exception ex){
            return null;
        }
    }

    public boolean authenticate(String name,String password){
        UserDataSet user = readByName(name);
        if(user!=null){
            if(user.getPassword().equals(password))
                return true;
        }
        return false;
    }

    public Roles getRoleByName(String name){
        UserDataSet user = readByName(name);
        if (user!=null)
            return user.getRole();
        else return null;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return msContext.getMessageSystem();
    }

}
