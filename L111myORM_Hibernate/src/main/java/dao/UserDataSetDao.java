package dao;

import dbcommon.QueryCommand;
import dbcommon.ReflectionHelper;
import exceptions.DaoOperationException;
import exceptions.NoEntityException;
import executor.Executor;
import model.DataSet;
import tables.Table;
import tables.TableColumn;
import tables.TableInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDataSetDao implements DataSetDao {

    private final Connection connection;

    public UserDataSetDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void save(T user) {
        ReflectionHelper.checkClass(user.getClass());
        try {
            int fieldCount=1;
            final PreparedStatement statement = connection.prepareStatement(
                    TableInfo.getQuery(user.getClass(), QueryCommand.SAVE));
            String tableName = TableInfo.getTableName(user.getClass());
            for (Table table : TableInfo.getTables()) {
                if(table.getTableName().equals(tableName)){
                    for (TableColumn coloumn : table.getColoumns()) {
                        statement.setObject(fieldCount, ReflectionHelper.getFieldData(user,coloumn.getName()));
                        fieldCount++;
                    }
                    break;
                }
            }
            Executor.updatePrepared(statement);
        }
        catch (Exception ex){
            throw new DaoOperationException(ex.getMessage());
        }
    }

    @Override
    public <T extends DataSet> T read(long id, Class<T> clazz) {
        ReflectionHelper.checkClass(clazz);
        try {
            final PreparedStatement statement = connection.prepareStatement(TableInfo.getQuery(clazz,QueryCommand.LOAD));
            statement.setLong(1, id);
            return (T)Executor.queryPrepared(statement,UserDataSetDao::extract,clazz);
        }
        catch (Exception ex){
            throw new DaoOperationException(ex.getMessage());
        }
    }

    @Override
    public <T extends DataSet> List<T> readAll(Class<T> clazz) {
        ReflectionHelper.checkClass(clazz);
        try {
            final PreparedStatement statement = connection.prepareStatement(TableInfo.getQuery(clazz,QueryCommand.LOAD_ALL));
            return (List<T>) Executor.queryPrepared(statement,UserDataSetDao::extractList,clazz);
        }
        catch (Exception ex){
            throw new DaoOperationException(ex.getMessage());
        }
    }

    @Override
    public <T extends DataSet> void delete(T user) {
        ReflectionHelper.checkClass(user.getClass());
        try {
            final PreparedStatement statement = connection.prepareStatement(
                    TableInfo.getQuery(user.getClass(),QueryCommand.DELETE));
            statement.setLong(1, user.getId());
            Executor.updatePrepared(statement);
        }
        catch (Exception ex){
            throw new DaoOperationException(ex.getMessage());
        }
    }

    @Override
    public <T extends DataSet> void deleteById(long id, Class<T> clazz) {
        ReflectionHelper.checkClass(clazz);
        try {
            final PreparedStatement statement = connection.prepareStatement(
                    TableInfo.getQuery(clazz,QueryCommand.DELETE));
            statement.setLong(1, id);
            Executor.updatePrepared(statement);
        }
        catch (Exception ex){
            throw new DaoOperationException(ex.getMessage());
        }
    }

    static <T extends DataSet> T create(ResultSet resultSet, Class<T> clazz) {
        try {
            T instance = clazz.getConstructor().newInstance();
            String tableName = TableInfo.getTableName(clazz);
            for (Table table : TableInfo.getTables()) {
                if(table.getTableName().equals(tableName)){
                    for (TableColumn coloumn : table.getColoumns()) {
                        ReflectionHelper.setFieldData(instance,coloumn.getName(),resultSet.getObject(coloumn.getName()));
                    }
                }
            }
            instance.setId(resultSet.getLong("id"));
            return instance;
        }
        catch (Exception ex){
            throw new DaoOperationException(ex.getMessage());
        }
    }

    static <T extends DataSet> T extract(ResultSet resultSet, Class<T> clazz) {
        try{
            if (!resultSet.next()) {
                return null;
            }
            return create(resultSet,clazz);
        }
        catch (Exception ex){
            throw new DaoOperationException(ex.getMessage());
        }
    }

    static <T extends DataSet> List<T> extractList(ResultSet resultSet,Class<T> clazz) {
        try{
            List<T> resultList = new ArrayList<T>();
            while (resultSet.next()){
                resultList.add(create(resultSet,clazz));
            }
            return resultList;
        }
        catch (Exception ex){
            throw new DaoOperationException(ex.getMessage());
        }
    }
}
