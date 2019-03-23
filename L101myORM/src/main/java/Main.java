import base.DBService;
import base.DBServiceImpl;
import dao.DataSetDao;
import dao.DataSetExecutorDao;
import dbcommon.ConnectionHelper;
import executors.Executor;
import model.DataSet;
import model.UserDataSet;

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
            dao.save(new UserDataSet("User2",12));
            dao.save(new UserDataSet("User3",64));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
