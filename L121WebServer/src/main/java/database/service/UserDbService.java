package database.service;

import database.model.Roles;
import database.model.UserDataSet;
import org.eclipse.jetty.server.Authentication;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDbService {

    private final DBServiceHibernate dbService;

    public UserDbService(SessionFactory sessionFactory) {
        this.dbService = new DBServiceHibernate(sessionFactory);
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

    public void deleteUserById(long id){
        dbService.deleteById(Long.valueOf(id),UserDataSet.class);
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

}
