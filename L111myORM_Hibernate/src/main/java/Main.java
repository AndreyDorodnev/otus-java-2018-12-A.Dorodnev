import dbcommon.ConnectionHelper;
import exceptions.*;
import model.*;
import services.DBServiceHibernate;
import services.DBServiceImpl;
import services.HibernateService;
import services.SchemaService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        hibernateTest();
//        myOrmTest();
    }

    private static void hibernateTest(){
        try{
            HibernateService hibernateService = new HibernateService(AddressDataSet.class,PhoneDataSet.class,UserDataSet.class);
            DBServiceHibernate dbservice = new DBServiceHibernate(hibernateService.getSessionFactory());
            List<PhoneDataSet> phones = new ArrayList<>();
            phones.add(new PhoneDataSet("123-456"));
            phones.add(new PhoneDataSet("987-654"));
            dbservice.save(new UserDataSet("user1",27,new AddressDataSet("New York"),phones));

            List<PhoneDataSet> phones2 = new ArrayList<>();
            phones2.add(new PhoneDataSet("000-001"));
            phones2.add(new PhoneDataSet("000-002"));
            dbservice.save(new UserDataSet("user2",40,new AddressDataSet("London"),phones2));

            System.out.println("READ BY ID");
            UserDataSet resultUser1 = dbservice.read(2,UserDataSet.class);
            System.out.println(resultUser1);

            System.out.println("READ ALL:");
            ArrayList<UserDataSet> resultList = (ArrayList<UserDataSet>) dbservice.readAll(UserDataSet.class);
            for (UserDataSet userDataSet : resultList) {
                System.out.println(userDataSet);
            }

            System.out.println("DELETE");
            UserDataSet delUser = new UserDataSet();
            delUser.setId(2);
            dbservice.delete(delUser);
        }
        catch (NoEntityException e){
            System.out.println(e.getMessage());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private static void myOrmTest(){
        try (final Connection connection = ConnectionHelper.getPostgresqlConnection()){
            SchemaService schemaService = new SchemaService(connection, UserDataSetOld.class);
            schemaService.prepareTables();
            DBServiceImpl dbService = new DBServiceImpl(connection);
            dbService.save(new UserDataSetOld("User1",10));
            dbService.save(new UserDataSetOld("User2",22));
            dbService.save(new UserDataSetOld("User3",55));

            System.out.println("READ");
            UserDataSetOld olUser2 = dbService.read(2,UserDataSetOld.class);
            System.out.println(olUser2);

            System.out.println("READ ALL");
            List<UserDataSetOld> resultList = dbService.readAll(UserDataSetOld.class);
            for (UserDataSetOld userDataSetOld : resultList) {
                System.out.println(userDataSetOld);
            }

            System.out.println("DELETE");
            dbService.deleteById(3,UserDataSetOld.class);


            schemaService.dropTables();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
        }
        catch (DaoOperationException e){
            System.out.println(e.getMessage());
        }

    }

}