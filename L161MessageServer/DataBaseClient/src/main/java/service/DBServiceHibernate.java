package service;

import dao.UserDataSetDaoHibernate;
import model.DataSet;
import model.Roles;
import model.UserDataSet;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Function;

public class DBServiceHibernate implements DBService {

    private final SessionFactory sessionFactory;

    public DBServiceHibernate(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public <T extends DataSet> void save(T user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            UserDataSetDaoHibernate dao = new UserDataSetDaoHibernate(session);
            dao.save(user);
            transaction.commit();
        }
        catch (Exception ex){
            if(transaction!=null)
                transaction.rollback();
            ex.printStackTrace();
        }
    }
    @Override
    public <T extends DataSet> T read(long id, Class<T> clazz) {
        return runInSession(session -> {
            UserDataSetDaoHibernate dao = new UserDataSetDaoHibernate(session);
            T object = dao.read(id,clazz);
            Hibernate.initialize(object);
            return object;
        });
    }

    public <T extends DataSet> T readByName(String name,Class<T> clazz){
        return runInSession(session -> {
            UserDataSetDaoHibernate dao = new UserDataSetDaoHibernate(session);
            T object = dao.readByName(name,clazz);
            Hibernate.initialize(object);
            return object;
        });
    }

    @Override
    public <T extends DataSet> List<T> readAll(Class<T> clazz) {
        return runInSession(session -> {
            UserDataSetDaoHibernate dao = new UserDataSetDaoHibernate(session);
            List<T> objects = dao.readAll(clazz);
            for (T object : objects) {
                Hibernate.initialize(object);
            }
            return objects;
        });
    }

    @Override
    public <T extends DataSet> void delete(T user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            UserDataSetDaoHibernate dao = new UserDataSetDaoHibernate(session);
            dao.delete(user);
            transaction.commit();
        }
        catch (Exception ex){
            if(transaction!=null)
                transaction.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public <T extends DataSet> boolean deleteById(long id, Class<T> clazz) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            UserDataSetDaoHibernate dao = new UserDataSetDaoHibernate(session);
            dao.deleteById(id,clazz);
            transaction.commit();
            return true;
        }
        catch (Exception ex){
            if(transaction!=null)
                transaction.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    private <R> R runInSession(Function<Session,R> function){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
        catch (Exception ex){
            if(transaction!=null)
                transaction.rollback();
            ex.printStackTrace();
        }
        return null;
    }

    public boolean authenticate(String name,String password){
        try{
            UserDataSet user = readByName(name,UserDataSet.class);
            if(user.getPassword().equals(password))
                return true;
            return false;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public Roles getRoleByName(String name){
        try{
            UserDataSet user = readByName(name,UserDataSet.class);
            return user.getRole();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}