package base;

import annotations.Coloumn;
import annotations.Entity;
import annotations.NotNullField;
import model.DataSet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.StringJoiner;

public class DBServiceImpl implements DBService {

    private final Connection connection;

    public DBServiceImpl(Connection connection) {
        this.connection = connection;
    }

    public DBServiceImpl(Connection connection, Class<? extends DataSet>[] classes) throws SQLException {
        this.connection = connection;
        Class[] entityClasses =
                Arrays.stream(classes).filter(aClass -> {
                    if(aClass.isAnnotationPresent(Entity.class))
                        return true;
                    return false;
                }).toArray(Class[]::new);
        prepareTables(entityClasses);
    }

    @Override
    public void prepareTables(Class[] classes) throws SQLException{
        for (Class aClass : classes) {
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(createSqlQueryForClass(aClass));
            }
        }
    }

    private String createSqlQueryForClass(Class entityClass){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS " + entityClass.getName() + " ( id BIGSERIAL NOT NULL PRIMARY KEY,");
        for (Field declaredField : entityClass.getDeclaredFields()) {
            if(declaredField.isAnnotationPresent(Coloumn.class)){
                stringBuilder.append(declaredField.getAnnotation(Coloumn.class).colName() + " " + declaredField.getAnnotation(Coloumn.class).colType());
                if(declaredField.isAnnotationPresent(NotNullField.class)){
                    stringBuilder.append(" NOT NULL,");
                }
                else stringBuilder.append(" , ");
            }
        }
        stringBuilder.setCharAt(stringBuilder.length()-1,')');
        stringBuilder.append(";");
//        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    @Override
    public String getMetaData() throws SQLException {
        final StringJoiner joiner = new StringJoiner("\n");
        joiner.add("Autocommit: " + connection.getAutoCommit());
        final DatabaseMetaData metaData = connection.getMetaData();
        joiner.add("DB name: " + metaData.getDatabaseProductName());
        joiner.add("DB version: " + metaData.getDatabaseProductVersion());
        joiner.add("Driver name: " + metaData.getDriverName());
        joiner.add("Driver version: " + metaData.getDriverVersion());
        joiner.add("JDBC version: " + metaData.getJDBCMajorVersion() + '.' + metaData.getJDBCMinorVersion());
        System.out.println(joiner.toString());
        return joiner.toString();
    }


    @Override
    public void deleteTables(Class[] classes) throws SQLException {
        for (Class aClass : classes) {
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(dropSqlQueryForClass(aClass));
            }
        }
    }

    private String dropSqlQueryForClass(Class entityClass){
        return "DROP TABLE IF EXISTS " + entityClass.getName();
    }

}
