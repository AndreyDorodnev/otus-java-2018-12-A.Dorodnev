import base.DBService;
import base.DBServiceImpl;
import dao.DataSetDao;
import dao.DataSetExecutorDao;
import dbcommon.ConnectionHelper;
import exceptions.NoEntityException;
import executors.Executor;
import model.DataSet;
import model.UserDataSet;
import model.UserDataSetExtended;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try (final Connection connection = ConnectionHelper.getPostgresqlConnection()){

            Class<? extends DataSet>[] classes = new Class[3];
            classes[0] = DataSet.class;
            classes[1] = UserDataSet.class;
            classes[2] = UserDataSetExtended.class;

            final DBService dbService = new DBServiceImpl(connection,classes);
            dbService.getMetaData();

            DataSetExecutorDao dao = new DataSetExecutorDao(connection);

            //UserDataSet
            dao.save(new UserDataSet("User1",10));
            dao.save(new UserDataSet("User2",55));
            dao.save(new UserDataSet("User3",27));
            UserDataSet user = dao.load(2,UserDataSet.class);
            System.out.println("User id: " + user.getId() + " name: " + user.getName() + " age: " + user.getAge());
            dao.update(2,new UserDataSet("updatedUser",88));
            user = dao.load(2,UserDataSet.class);
            System.out.println("User id: " + user.getId() + " name: " + user.getName() + " age: " + user.getAge());
            dao.delete(2,UserDataSet.class);
            user = dao.load(2,UserDataSet.class);

            //UserDataSetExtended
            dao.save(new UserDataSetExtended("User1","123-456",20,60.4F));
            dao.save(new UserDataSetExtended("User2","987-654",30,80.6F));
            UserDataSetExtended userExt = dao.load(1,UserDataSetExtended.class);
            System.out.println("UserExtended id: " + userExt.getId() + " name: " + userExt.getName()
                    + " phone: " + userExt.getPhoneNumber() + " age: "
                    + userExt.getAge() + " weight " + userExt.getWeight());

            dbService.deleteTables(classes);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
