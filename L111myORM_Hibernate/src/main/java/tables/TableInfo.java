package tables;

import dbcommon.QueryCommand;
import exceptions.NoEntityException;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.*;

public class TableInfo {

    private TableInfo(){

    }

    private static Map<String,String> typesMap = new HashMap<>();
    private static Map<String, HashMap<QueryCommand,String>> queryMap = new HashMap<>();
    private static List<Table> tables = new ArrayList<>();

    public static List<Table> getTables() {
        return tables;
    }

    private static Class[] getEntityClasses(Class[] classes){
        createTypesMap();
        return Arrays.stream(classes).filter(aClass -> {
                    if(aClass.isAnnotationPresent(Entity.class))
                        return true;
                    return false;
                }).toArray(Class[]::new);
    }

    private static void getTablesList(Class[] entityClasses) {
        if(entityClasses.length==0)
            throw new NoEntityException("No entity classes");
        for (Class entityClass : entityClasses) {
            if(entityClass.isAnnotationPresent(javax.persistence.Table.class)){
                Table table = getTable(entityClass);
                tables.add(table);
            }
        }
    }

    private static Table getTable(Class entityClass){
        javax.persistence.Table table = (javax.persistence.Table) entityClass.getAnnotation(javax.persistence.Table.class);
        Table resultTable = new Table(table.name());
        for (Field declaredField : entityClass.getDeclaredFields()) {
            if(declaredField.isAnnotationPresent(Column.class)){
                Column column = declaredField.getAnnotation(Column.class);
                resultTable.addColoumn(new TableColumn(column.name(),typesMap.get(declaredField.getType().getSimpleName()),column.nullable()));
            }
        }
        return resultTable;
    }

    private static void joinColumns(){

    }

    public static void generateQueryes(Class[] classes) {
        getTablesList(getEntityClasses(classes));
        for (Table table : tables) {
            HashMap<QueryCommand,String> map = new HashMap<>();
            map.put(QueryCommand.CREATE_TABLE,createTableQuery(table));
            map.put(QueryCommand.DROP_TABLE,dropTableQuery(table));
            map.put(QueryCommand.SAVE,saveQuery(table));
            map.put(QueryCommand.LOAD,loadQuery(table));
            map.put(QueryCommand.UPDATE,updateQuery(table));
            map.put(QueryCommand.DELETE,deleteQuery(table));
            map.put(QueryCommand.LOAD_ALL,loadAllQuery(table));
            queryMap.put(table.getTableName(), map);
//            System.out.println(createTableQuery(table));
//            System.out.println(dropTableQuery(table));
//            System.out.println(saveQuery(table));
//            System.out.println(loadQuery(table));
//            System.out.println(updateQuery(table));
//            System.out.println(deleteQuery(table));
        }

    }

    public static String getTableName(Class clazz){
        if(clazz.isAnnotationPresent(javax.persistence.Table.class)){
            javax.persistence.Table table = (javax.persistence.Table) clazz.getAnnotation(javax.persistence.Table.class);
            return table.name();
        }
        return null;
    }

    public static String getQuery(Class clazz,QueryCommand command){
        if(queryMap==null)
            throw new NullPointerException("no queryMap");
        return queryMap.get(getTableName(clazz)).get(command);
    }
    public static String getQuery(String tableName,QueryCommand command){
        if(queryMap==null)
            throw new NullPointerException("no queryMap");
        return queryMap.get(tableName).get(command);
    }

    public static Set<String> getTablesNames(){
        if(queryMap==null)
            throw new NullPointerException("no queryMap");
        return queryMap.keySet();
    }

    private static String createTableQuery(Table table){
       StringBuilder stringBuilder = new StringBuilder();
       stringBuilder.append("CREATE TABLE IF NOT EXISTS " + table.getTableName() + "( id BIGSERIAL NOT NULL PRIMARY KEY,");
        for (TableColumn coloumn : table.getColoumns()) {
            stringBuilder.append(coloumn.getName() + " " + coloumn.getType() + " ");
            if(coloumn.notNull)
                stringBuilder.append("NOT NULL ");
            stringBuilder.append(",");
        }
        stringBuilder.setCharAt(stringBuilder.length()-1,')');
        stringBuilder.append(";");
        return stringBuilder.toString();
    }
    private static String dropTableQuery(Table table){
        return "DROP TABLE IF EXISTS " + table.getTableName() + ";";
    }
    private static String saveQuery(Table table){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO " + table.getTableName() + " (");
        for (TableColumn coloumn : table.getColoumns()) {
            stringBuilder.append(coloumn.getName() + ",");
        }
        stringBuilder.setCharAt(stringBuilder.length()-1,')');
        stringBuilder.append(" VALUES (");
        for(int i=0;i<table.getColoumns().size();i++){
            stringBuilder.append("?,");
        }
        stringBuilder.setCharAt(stringBuilder.length()-1,')');
        stringBuilder.append(";");
        return stringBuilder.toString();
    }
    private static String loadQuery(Table table){
        return  "SELECT * FROM " + table.getTableName() + " WHERE id = ?;";
    }
    private static String loadAllQuery(Table table){
        return "SELECT * FROM " + table.getTableName();
    }
    private static String updateQuery(Table table){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE " + table.getTableName() + " SET ");
        for (TableColumn coloumn : table.getColoumns()) {
            stringBuilder.append(coloumn.getName() + "=?,");
        }
        stringBuilder.setCharAt(stringBuilder.length()-1,' ');
        stringBuilder.append("WHERE id = ?");
        stringBuilder.append(";");
        return stringBuilder.toString();
    }
    private static String deleteQuery(Table table){
        return "DELETE FROM " + table.getTableName() + " WHERE id = ?;";
    }

    private static void createTypesMap(){
        typesMap.put("String","VARCHAR(255)");
        typesMap.put("Integer","INT4");
        typesMap.put("int","INT4");
        typesMap.put("Long","INT8");
        typesMap.put("long","INT8");
        typesMap.put("Float","FLOAT4");
        typesMap.put("float","FLOAT4");
        typesMap.put("Double","FLOAT8");
        typesMap.put("double","FLOAT8");
        typesMap.put("Boolean","BOOL");
        typesMap.put("boolean","BOOL");
    }

}
