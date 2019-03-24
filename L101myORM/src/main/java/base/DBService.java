package base;

import model.DataSet;

import java.sql.SQLException;

public interface DBService {
    String getMetaData() throws SQLException;

    void prepareTables(Class<? extends DataSet>[] classes) throws SQLException;

    void deleteTables(Class<? extends DataSet>[] classes) throws SQLException;
}
