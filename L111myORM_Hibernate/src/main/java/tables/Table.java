package tables;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String tableName;
    private List<TableColumn> coloumns = new ArrayList<>();

    public Table(String tableName) {
        this.tableName = tableName;
    }
    public void addColoumn(TableColumn coloumn){
        coloumns.add(coloumn);
    }

    public List<TableColumn> getColoumns() {
        return coloumns;
    }

    public String getTableName() {
        return tableName;
    }
}
