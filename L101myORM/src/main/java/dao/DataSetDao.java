package dao;

import exceptions.DaoOperationException;
import exceptions.NoEntityException;
import model.DataSet;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DataSetDao {

    <T extends DataSet> void save(T user) throws DaoOperationException, IllegalAccessException, NoEntityException, NoSuchFieldException;

    <T extends DataSet> T load(long id, Class<T> clazz) throws DaoOperationException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoEntityException, NoSuchFieldException;

    <T extends DataSet> void update(T user) throws DaoOperationException, IllegalAccessException, NoEntityException, NoSuchFieldException;

    <T extends DataSet> void update(long id,T user) throws DaoOperationException, IllegalAccessException, NoEntityException, NoSuchFieldException;

    <T extends DataSet> void delete(int id, Class<T> clazz) throws DaoOperationException, NoEntityException;
}
