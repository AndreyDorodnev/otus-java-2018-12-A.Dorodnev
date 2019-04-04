package services;

import dbcommon.QueryCommand;
import tables.TableInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaService {

    private final Connection connection;

    public SchemaService(Connection connection,Class...classes) {
        this.connection = connection;
        TableInfo.generateQueryes(classes);
    }

    public void prepareTables() throws SQLException {
        for (String key : TableInfo.getTablesNames()) {
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(TableInfo.getQuery(key, QueryCommand.CREATE_TABLE));
            }
        }
    }

    public void dropTables() throws SQLException {
        for (String key : TableInfo.getTablesNames()) {
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(TableInfo.getQuery(key,QueryCommand.DROP_TABLE));
            }
        }
    }
}
