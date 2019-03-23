package dao;

import model.DataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DataSetExecutorDao implements DataSetDao {

    private final Connection connection;

    public DataSetExecutorDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(DataSet user) throws SQLException {

    }

    @Override
    public DataSet getById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<DataSet> getAll() throws SQLException {
        return null;
    }

    @Override
    public void update(DataSet user) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }
}
