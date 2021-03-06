package dbcommon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    public static Connection getPostgresqlConnection() throws SQLException {

        final String connString = "jdbc:postgresql://" +    // db type
                "localhost:" +                              // host name
                "5432/" +                                   // port
                "dataSetBase" +                                   // db name
                "user=postgres&" +                         // login
                "password=123";                   // password

        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/dataSetBase","postgres", "15041");
    }
}
