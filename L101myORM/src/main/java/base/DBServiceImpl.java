package base;

import model.DataSet;

import java.sql.Connection;
import java.sql.SQLException;

public class DBServiceImpl implements DBService {

    private final Connection connection;

    public DBServiceImpl(Connection connection) {
        this.connection = connection;
    }

    public DBServiceImpl(Connection connection, Class<? extends DataSet>...classes){
        this.connection = connection;
        for (Class aClass : classes) {
//            aClass.get
        }
    }

    @Override
    public String getMetaData() throws SQLException {
        return null;
    }

    @Override
    public void prepareTables() throws SQLException {

    }

    @Override
    public void deleteTables() throws SQLException {

    }
}
