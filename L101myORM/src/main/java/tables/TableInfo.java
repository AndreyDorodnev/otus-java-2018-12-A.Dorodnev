package tables;

import annotations.Coloumn;
import annotations.Entity;
import annotations.NotNullField;
import dbcommon.QueryCommand;

import java.lang.reflect.Field;
import java.util.*;

public class TableInfo {

    private TableInfo(){

    }

    private static Map<String, HashMap<QueryCommand,String>> queryMap = new HashMap<>();
    private static List<Table> tables = new ArrayList<>();

    public static List<Table> getTables() {
        return tables;
    }

    private static Class[] getEntityClasses(Class[] classes){
        return Arrays.stream(classes).filter(aClass -> {
                    if(aClass.isAnnotationPresent(Entity.class))
                        return true;
                    return false;
                }).toArray(Class[]::new);
    }

    private static void getTablesList(Class[] entityClasses) {
        if(entityClasses.length==0)
            throw new IllegalArgumentException("No entity classes");
        for (Class entityClass : entityClasses) {
            Table table = new Table(entityClass.getSimpleName());
            for (Field declaredField : entityClass.getDeclaredFields()) {
                if(declaredField.isAnnotationPresent(Coloumn.class)){
                    if(declaredField.isAnnotationPresent(NotNullField.class)){
                        table.addColoumn(new TableColoumn(declaredField.getName(),
                                declaredField.getAnnotation(Coloumn.class).colType(),true));
                    }
                    else {
                        table.addColoumn(new TableColoumn(declaredField.getName(),
                                declaredField.getAnnotation(Coloumn.class).colType(),false));
                    }
                }
            }
            tables.add(table);
        }
    }

    public static void generateQueryes(Class[] classes) throws IllegalAccessException {
        getTablesList(getEntityClasses(classes));
        for (Table table : tables) {
            HashMap<QueryCommand,String> map = new HashMap<>();
            map.put(QueryCommand.CREATE_TABLE,createTableQuery(table));
            map.put(QueryCommand.DROP_TABLE,dropTableQuery(table));
            map.put(QueryCommand.SAVE,saveQuery(table));
            map.put(QueryCommand.LOAD,loadQuery(table));
            map.put(QueryCommand.UPDATE,updateQuery(table));
            map.put(QueryCommand.DELETE,deleteQuery(table));
            queryMap.put(table.getTableName(), map);
        }

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
        for (TableColoumn coloumn : table.getColoumns()) {
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
        for (TableColoumn coloumn : table.getColoumns()) {
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
    private static String updateQuery(Table table){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE " + table.getTableName() + " SET ");
        for (TableColoumn coloumn : table.getColoumns()) {
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


}
