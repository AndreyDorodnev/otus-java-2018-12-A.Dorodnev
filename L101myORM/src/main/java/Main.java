import base.DBService;
import base.DBServiceImpl;
import dao.DataSetDao;
import dao.DataSetExecutorDao;
import dbcommon.ConnectionHelper;
import executors.Executor;
import model.DataSet;
import model.UserDataSet;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try (final Connection connection = ConnectionHelper.getPostgresqlConnection()){

            Class<? extends DataSet>[] classes = new Class[2];
            classes[0] = DataSet.class;
            classes[1] = UserDataSet.class;

            final DBService dbService = new DBServiceImpl(connection,classes);
            dbService.getMetaData();

            DataSetExecutorDao dao = new DataSetExecutorDao(connection);

            UserDataSet userDataSet1 = dao.load(1,UserDataSet.class);
            System.out.println(userDataSet1.getName() + " " + userDataSet1.getAge());
            dao.update(new UserDataSet(1,"updatedUser",55));
            UserDataSet userDataSet2 = dao.load(1,UserDataSet.class);
            dao.delete(3,UserDataSet.class);
//            System.out.println(userDataSet2.getName() + " " + userDataSet2.getAge());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
