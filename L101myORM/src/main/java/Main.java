import base.DBServiceImpl;
import dbcommon.ConnectionHelper;
import exceptions.DaoOperationException;
import exceptions.NoEntityException;
import model.DataSet;
import model.UserDataSet;
import model.UserDataSetExtended;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        try (final Connection connection = ConnectionHelper.getPostgresqlConnection()){

            Class<? extends DataSet>[] classes = new Class[3];
            classes[0] = DataSet.class;
            classes[1] = UserDataSet.class;
            classes[2] = UserDataSetExtended.class;


            final DBServiceImpl dbService = new DBServiceImpl(connection,classes);
            dbService.getMetaData();
            //UserDataSet
            dbService.save(new UserDataSet("User1",10));
            dbService.save(new UserDataSet("User2",55));
            dbService.save(new UserDataSet("User3",27));
            UserDataSet user = dbService.load(2,UserDataSet.class);
            System.out.println("User id: " + user.getId() + " name: " + user.getName() + " age: " + user.getAge());
            dbService.update(2,new UserDataSet("updatedUser",88));
            user = dbService.load(2,UserDataSet.class);
            System.out.println("User id: " + user.getId() + " name: " + user.getName() + " age: " + user.getAge());
            dbService.delete(2,UserDataSet.class);
            user = dbService.load(2,UserDataSet.class);

            //UserDataSetExtended
            dbService.save(new UserDataSetExtended("User1","123-456",20,60.4F));
            dbService.save(new UserDataSetExtended("User2","987-654",30,80.6F));
            UserDataSetExtended userExt = dbService.load(1,UserDataSetExtended.class);
            System.out.println("UserExtended id: " + userExt.getId() + " name: " + userExt.getName()
                    + " phone: " + userExt.getPhoneNumber() + " age: "
                    + userExt.getAge() + " weight " + userExt.getWeight());

            dbService.deleteTables();

        } catch (DaoOperationException e) {
            e.printStackTrace();
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
