import base.DBService;
import base.DBServiceImpl;
import dbcommon.ConnectionHelper;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try (final Connection connection = ConnectionHelper.getPostgresqlConnection()){

            final DBService dbService = new DBServiceImpl(connection);
            dbService.deleteTables();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
