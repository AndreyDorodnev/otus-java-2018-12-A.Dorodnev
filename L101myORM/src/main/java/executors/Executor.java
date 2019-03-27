package executors;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Executor {
    public static <T> T queryPrepared(PreparedStatement statement, TResultHandler<T> handler,Class clazz) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        try (statement) {
            final ResultSet resultSet = statement.executeQuery();
            return handler.handle(resultSet,clazz);
        }
    }

    public static void updatePrepared(PreparedStatement statement) throws SQLException {
        try (statement) {
            statement.executeUpdate();
        }
    }
}
