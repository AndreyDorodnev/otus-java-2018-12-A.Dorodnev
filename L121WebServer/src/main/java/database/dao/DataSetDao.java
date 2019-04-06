package database.dao;

import database.model.DataSet;

import java.util.List;

public interface DataSetDao {

    <T extends DataSet> void save(T user);
    <T extends DataSet> T read(long id, Class<T> clazz);
    <T extends DataSet> void delete(T user);
    <T extends DataSet> void deleteById(long id, Class<T> clazz);
    <T extends DataSet> List<T> readAll(Class<T> clazz);
}
