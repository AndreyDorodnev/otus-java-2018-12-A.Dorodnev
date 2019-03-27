package dao;

import dbcommon.QueryCommand;
import dbcommon.ReflectionHelper;
import exceptions.DaoOperationException;
import exceptions.NoEntityException;
import executors.Executor;
import model.DataSet;
import tables.Table;
import tables.TableColoumn;
import tables.TableInfo;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DataSetExecutorDao implements DataSetDao {

    private final Connection connection;

    public DataSetExecutorDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void save(T user) throws DaoOperationException, IllegalAccessException, NoEntityException, NoSuchFieldException {
        ReflectionHelper.checkClass(user.getClass());
        int fieldCount=1;
        try {
            final PreparedStatement statement = connection.prepareStatement(
                    TableInfo.getQuery(user.getClass().getSimpleName(),QueryCommand.SAVE));
            for (Table table : TableInfo.getTables()) {
                if(table.getTableName().equals(user.getClass().getSimpleName())){
                    for (TableColoumn coloumn : table.getColoumns()) {
                        statement.setObject(fieldCount, ReflectionHelper.getFieldData(user,coloumn.getName()));
                        fieldCount++;
                    }
                    break;
                }
            }
            Executor.updatePrepared(statement);
        }
        catch (SQLException ex){
            throw new DaoOperationException(ex.getMessage());
        }
    }
    
    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws DaoOperationException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoEntityException, NoSuchFieldException {
        ReflectionHelper.checkClass(clazz);
        try {
            final PreparedStatement statement = connection.prepareStatement(TableInfo.getQuery(clazz.getSimpleName(),QueryCommand.LOAD));
            statement.setLong(1, id);
            return (T)Executor.queryPrepared(statement,DataSetExecutorDao::extract,clazz);
        }
        catch (SQLException ex){
            throw new DaoOperationException(ex.getMessage());
        }

    }

    @Override
    public <T extends DataSet> void update(T user) throws DaoOperationException, IllegalAccessException, NoEntityException, NoSuchFieldException {
        ReflectionHelper.checkClass(user.getClass());
        int fieldCount=1;
        try {
            final PreparedStatement statement = connection.prepareStatement(
                    TableInfo.getQuery(user.getClass().getSimpleName(),QueryCommand.UPDATE));
            for (Table table : TableInfo.getTables()) {
                if(table.getTableName().equals(user.getClass().getSimpleName())){
                    for (TableColoumn coloumn : table.getColoumns()) {
                        statement.setObject(fieldCount, ReflectionHelper.getFieldData(user,coloumn.getName()));
                        fieldCount++;
                    }
                    break;
                }
            }
            statement.setLong(fieldCount,user.getId());
            Executor.updatePrepared(statement);
        }
        catch (SQLException ex){
            throw new DaoOperationException(ex.getMessage());
        }
    }

    @Override
    public <T extends DataSet> void update(long id,T user) throws DaoOperationException, IllegalAccessException, NoEntityException, NoSuchFieldException {
        ReflectionHelper.checkClass(user.getClass());
        int fieldCount=1;
        try {
            final PreparedStatement statement = connection.prepareStatement(
                    TableInfo.getQuery(user.getClass().getSimpleName(),QueryCommand.UPDATE));
            for (Table table : TableInfo.getTables()) {
                if(table.getTableName().equals(user.getClass().getSimpleName())){
                    for (TableColoumn coloumn : table.getColoumns()) {
                        statement.setObject(fieldCount, ReflectionHelper.getFieldData(user,coloumn.getName()));
                        fieldCount++;
                    }
                    break;
                }
            }
            statement.setLong(fieldCount,id);
            Executor.updatePrepared(statement);
        }
        catch (SQLException ex){
            throw new DaoOperationException(ex.getMessage());
        }
    }

    @Override
    public <T extends DataSet> void delete(int id, Class<T> clazz) throws DaoOperationException, NoEntityException {
        ReflectionHelper.checkClass(clazz);
        try {
            final PreparedStatement statement = connection.prepareStatement(
                    TableInfo.getQuery(clazz.getSimpleName(),QueryCommand.DELETE));
            statement.setInt(1, id);
            Executor.updatePrepared(statement);
        }
        catch (SQLException ex){
            throw new DaoOperationException(ex.getMessage());
        }
    }

    static <T extends DataSet> T create(ResultSet resultSet, Class<T> clazz) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        T instance = clazz.getConstructor().newInstance();
        for (Table table : TableInfo.getTables()) {
            if(table.getTableName().equals(clazz.getSimpleName())){
                for (TableColoumn coloumn : table.getColoumns()) {
                    ReflectionHelper.setFieldData(instance,coloumn.getName(),resultSet.getObject(coloumn.getName()));
                }
            }
        }
        instance.setId(resultSet.getLong("id"));
        return instance;
    }

    static <T extends DataSet> T extract(ResultSet resultSet,Class<T> clazz) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        if (!resultSet.next()) {
            return null;
        }
        return create(resultSet,clazz);
    }

}
