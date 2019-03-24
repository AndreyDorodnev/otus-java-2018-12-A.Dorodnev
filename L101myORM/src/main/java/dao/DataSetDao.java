package dao;

import annotations.Coloumn;
import exceptions.NoEntityException;
import model.DataSet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DataSetDao {
    static <T extends DataSet> T create(ResultSet resultSet,Class<T> clazz) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        T instance = clazz.getConstructor().newInstance();
        for (Field declaredField : clazz.getDeclaredFields()) {
            if(declaredField.isAnnotationPresent(Coloumn.class)){
                declaredField.setAccessible(true);
                declaredField.set(instance,resultSet.getObject(declaredField.getAnnotation(Coloumn.class).colName()));
            }
        }
        instance.setId(resultSet.getLong("id"));
        return instance;
    }

    static <T extends DataSet> T extract(ResultSet resultSet,Class<T> clazz) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (!resultSet.next()) {
            return null;
        }
        return create(resultSet,clazz);
    }

    <T extends DataSet> void save(T user) throws SQLException, IllegalAccessException, NoEntityException;

    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoEntityException;

    <T extends DataSet> void update(T user) throws SQLException, IllegalAccessException, NoEntityException;

    <T extends DataSet> void update(long id,T user) throws SQLException, IllegalAccessException, NoEntityException;

    <T extends DataSet> void delete(int id, Class<T> clazz) throws SQLException, NoEntityException;
}
