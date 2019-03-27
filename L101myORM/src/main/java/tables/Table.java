package tables;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String tableName;
    private List<TableColoumn> coloumns = new ArrayList<>();

    public Table(String tableName) {
        this.tableName = tableName;
    }
    public void addColoumn(TableColoumn coloumn){
        coloumns.add(coloumn);
    }

    public List<TableColoumn> getColoumns() {
        return coloumns;
    }

    public String getTableName() {
        return tableName;
    }
}
