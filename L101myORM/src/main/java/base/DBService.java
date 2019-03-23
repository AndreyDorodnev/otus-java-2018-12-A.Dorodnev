package base;

import java.sql.SQLException;

public interface DBService {
    String getMetaData() throws SQLException;

    void prepareTables(Class[] classes) throws SQLException;

    void deleteTables(Class[] classes) throws SQLException;
}
