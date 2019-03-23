package model;

import annotations.Coloumn;
import annotations.Entity;
import annotations.NotNullField;

@Entity
public class UserDataSet extends DataSet {
    @Coloumn(colName = "name",colType = "VARCHAR(255)")
    @NotNullField
    String name;
    @Coloumn(colName = "age",colType = "SMALLINT")
    @NotNullField
    int age;

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
