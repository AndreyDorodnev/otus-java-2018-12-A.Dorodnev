package services;

import dao.DataSetDao;
import dao.UserDataSetDao;
import exceptions.NoEntityException;
import model.DataSet;

import java.sql.Connection;
import java.util.List;

public class DBServiceImpl implements DBService {

    private final Connection connection;

    public DBServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void save(T user) {
        DataSetDao dao = new UserDataSetDao(connection);
        dao.save(user);
    }

    @Override
    public <T extends DataSet> T read(long id, Class<T> clazz) {
        DataSetDao dao = new UserDataSetDao(connection);
        return dao.read(id,clazz);
    }

    @Override
    public <T extends DataSet> void delete(T user) {
        DataSetDao dao = new UserDataSetDao(connection);
        dao.delete(user);
    }

    @Override
    public <T extends DataSet> void deleteById(long id, Class<T> clazz) {
        DataSetDao dao = new UserDataSetDao(connection);
        dao.deleteById(id,clazz);
    }

    @Override
    public <T extends DataSet> List<T> readAll(Class<T> clazz) {
        DataSetDao dao = new UserDataSetDao(connection);
        return dao.readAll(clazz);
    }
}
