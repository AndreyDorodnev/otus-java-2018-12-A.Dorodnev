package dao;

import model.DataSet;

import java.sql.SQLException;
import java.util.List;

public interface DataSetDao {
//    static User create(ResultSet resultSet) throws SQLException {
//        return new User(resultSet.getLong("id"), resultSet.getString("user_name"), resultSet.getInt("age"));
//    }
//
//    static User extract(ResultSet resultSet) throws SQLException {
//        if (!resultSet.next()) {
//            return null;
//        }
//        return create(resultSet);
//    }
//
//    static List<User> extractList(ResultSet resultSet) throws SQLException {
//        final List<User> result = new ArrayList<>();
//        while (resultSet.next()) {
//            result.add(create(resultSet));
//        }
//        return result;
//    }

    void create(DataSet user) throws SQLException;

    DataSet getById(int id) throws SQLException;

    List<DataSet> getAll() throws SQLException;

    void update(DataSet user) throws SQLException;

    void delete(int id) throws SQLException;
}
