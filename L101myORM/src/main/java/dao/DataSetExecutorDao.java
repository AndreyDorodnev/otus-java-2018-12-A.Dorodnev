package dao;

import annotations.Coloumn;
import annotations.Entity;
import exceptions.NoEntityException;
import executors.Executor;
import model.DataSet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSetExecutorDao implements DataSetDao {

    private final Connection connection;

    public DataSetExecutorDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void save(T user) throws SQLException, IllegalAccessException, NoEntityException {
        checkClass(user.getClass());
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
//        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoEntityException {
        checkClass(clazz);
        final PreparedStatement statement = connection.prepareStatement(selectSqlQuery(clazz));
        statement.setLong(1, id);
        return (T)Executor.queryPrepared(statement,DataSetDao::extract,clazz);
    }

    private String selectSqlQuery(Class clazz){
        return "SELECT * FROM " + clazz.getName() + " WHERE id = ?";
    }

    @Override
    public <T extends DataSet> void update(T user) throws SQLException, IllegalAccessException, NoEntityException {
        checkClass(user.getClass());
        int fieldCount=1;
        updateSqlquery(user.getClass());
        final PreparedStatement statement = connection.prepareStatement(updateSqlquery(user.getClass()));
        for (Field declaredField : user.getClass().getDeclaredFields()) {
            if(declaredField.isAnnotationPresent(Coloumn.class)){
                declaredField.setAccessible(true);
                statement.setObject(fieldCount,declaredField.get(user));
                fieldCount++;
            }
        }
        statement.setLong(fieldCount,user.getId());
        Executor.updatePrepared(statement);
    }

    @Override
    public <T extends DataSet> void update(long id,T user) throws SQLException, IllegalAccessException, NoEntityException {
        checkClass(user.getClass());
        int fieldCount=1;
        updateSqlquery(user.getClass());
        final PreparedStatement statement = connection.prepareStatement(updateSqlquery(user.getClass()));
        for (Field declaredField : user.getClass().getDeclaredFields()) {
            if(declaredField.isAnnotationPresent(Coloumn.class)){
                declaredField.setAccessible(true);
                statement.setObject(fieldCount,declaredField.get(user));
                fieldCount++;
            }
        }
        statement.setLong(fieldCount,id);
        Executor.updatePrepared(statement);
    }


    private String updateSqlquery (Class entityClass){
        int fieldCount=0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE " + entityClass.getName() + " SET ");
        for (Field field : entityClass.getDeclaredFields()) {
            if(field.isAnnotationPresent(Coloumn.class)){
                fieldCount++;
                stringBuilder.append(field.getAnnotation(Coloumn.class).colName() + "=?,");
            }
        }
        stringBuilder.setCharAt(stringBuilder.length()-1,' ');
        stringBuilder.append("WHERE id = ?");
        stringBuilder.append(";");
//        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    @Override
    public <T extends DataSet> void delete(int id, Class<T> clazz) throws SQLException, NoEntityException {
        checkClass(clazz);
        final PreparedStatement statement = connection.prepareStatement(deleteSqlQuery(clazz));
        statement.setInt(1, id);
        Executor.updatePrepared(statement);
    }

    private String deleteSqlQuery(Class clazz){
        return "DELETE FROM " + clazz.getName() + " WHERE id = ?;";
    }

    private void checkClass(Class clazz) throws NoEntityException {
        if(!clazz.isAnnotationPresent(Entity.class))
            throw new NoEntityException("Class " + clazz.getName() + " is not entity");
    }
}
