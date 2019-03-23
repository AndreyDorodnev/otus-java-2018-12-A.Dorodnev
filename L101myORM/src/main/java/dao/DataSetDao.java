package dao;

import annotations.Coloumn;
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
        return instance;
    }

    static <T extends DataSet> T extract(ResultSet resultSet,Class<T> clazz) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (!resultSet.next()) {
            return null;
        }
        return create(resultSet,clazz);
    }
//
//    static List<User> extractList(ResultSet resultSet) throws SQLException {
//        final List<User> result = new ArrayList<>();
//        while (resultSet.next()) {
//            result.add(create(resultSet));
//        }
//        return result;
//    }

    <T extends DataSet> void save(T user) throws SQLException, IllegalAccessException;

    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

    List<DataSet> getAll() throws SQLException;

    <T extends DataSet> void update(T user) throws SQLException, IllegalAccessException;

    <T extends DataSet> void delete(int id, Class<T> clazz) throws SQLException;
}
