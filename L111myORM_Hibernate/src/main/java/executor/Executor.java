package executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Executor {
    public static <T> T queryPrepared(PreparedStatement statement, TResultHandler<T> handler, Class clazz) throws SQLException {
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
