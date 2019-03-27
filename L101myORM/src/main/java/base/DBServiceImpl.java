package base;

import dao.DataSetDao;
import dao.DataSetExecutorDao;
import dbcommon.QueryCommand;
import exceptions.NoEntityException;
import model.DataSet;
import tables.TableInfo;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringJoiner;

public class DBServiceImpl implements DBService {

    private final Connection connection;
    private DataSetDao dao;

    public DBServiceImpl(Connection connection, Class<? extends DataSet>[] classes) throws SQLException, IllegalAccessException {
        this.connection = connection;
        dao = new DataSetExecutorDao(connection);
        TableInfo.generateQueryes(classes);
        prepareTables();
    }

    @Override
    public void prepareTables() throws SQLException{
        for (String key : TableInfo.getTablesNames()) {
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(TableInfo.getQuery(key,QueryCommand.CREATE_TABLE));
            }
        }
    }

    @Override
    public String getMetaData() throws SQLException {
        final StringJoiner joiner = new StringJoiner("\n");
        joiner.add("Autocommit: " + connection.getAutoCommit());
        final DatabaseMetaData metaData = connection.getMetaData();
        joiner.add("DB name: " + metaData.getDatabaseProductName());
        joiner.add("DB version: " + metaData.getDatabaseProductVersion());
        joiner.add("Driver name: " + metaData.getDriverName());
        joiner.add("Driver version: " + metaData.getDriverVersion());
        joiner.add("JDBC version: " + metaData.getJDBCMajorVersion() + '.' + metaData.getJDBCMinorVersion());
        System.out.println(joiner.toString());
        return joiner.toString();
    }

    @Override
    public void deleteTables() throws SQLException {
        for (String key : TableInfo.getTablesNames()) {
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(TableInfo.getQuery(key,QueryCommand.DROP_TABLE));
            }
        }
    }

    public <T extends DataSet> void save(T user) throws IllegalAccessException, NoEntityException, NoSuchFieldException {
        dao.save(user);
    }
    public <T extends DataSet> T load(long id, Class<T> clazz) throws NoSuchMethodException, NoEntityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        return dao.load(id,clazz);
    }
    public <T extends DataSet> void update(T user) throws IllegalAccessException, NoEntityException, NoSuchFieldException {
        dao.update(user);
    }
    public <T extends DataSet> void update(long id,T user) throws IllegalAccessException, NoEntityException, NoSuchFieldException {
        dao.update(id,user);
    }
    public <T extends DataSet> void delete(int id, Class<T> clazz) throws NoEntityException {
        dao.delete(id,clazz);
    }

}
