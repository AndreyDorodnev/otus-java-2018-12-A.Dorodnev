package dao;

import annotations.Coloumn;
import executors.Executor;
import model.DataSet;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DataSetExecutorDao implements DataSetDao {

    private final Connection connection;

    public DataSetExecutorDao(Connection connection) {
        this.connection = connection;
    }

    public <T extends DataSet> void save(T user) throws SQLException, IllegalAccessException {
        int fieldCount=1;
        final PreparedStatement statement = connection.prepareStatement(insertSqlquery(user.getClass()));
        for (Field declaredField : user.getClass().getDeclaredFields()) {
            if(declaredField.isAnnotationPresent(Coloumn.class)){
                declaredField.setAccessible(true);
                statement.setObject(fieldCount,declaredField.get(user));
                fieldCount++;
            }
        }
        Executor.updatePrepared(statement);
    }

    private String insertSqlquery (Class entityClass){
        int fieldCount=0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO " + entityClass.getName() + " (");
        for (Field field : entityClass.getDeclaredFields()) {
            if(field.isAnnotationPresent(Coloumn.class)){
                fieldCount++;
                stringBuilder.append(field.getAnnotation(Coloumn.class).colName() + ",");
            }
        }
        stringBuilder.setCharAt(stringBuilder.length()-1,')');
        stringBuilder.append(" VALUES (");
        for(int i=0;i<fieldCount;i++){
            stringBuilder.append("?,");
        }
        stringBuilder.setCharAt(stringBuilder.length()-1,')');
        stringBuilder.append(";");
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }
//    <T extends DataSet> T load(long id, Class<T> clazz){
//
//    }

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
