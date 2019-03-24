package model;

import annotations.Coloumn;
import annotations.Entity;
import annotations.NotNullField;

@Entity
public class UserDataSetExtended extends DataSet {

    @Coloumn(colName = "name",colType = "VARCHAR(255)")
    @NotNullField
    private String name;

    @Coloumn(colName = "phone",colType = "VARCHAR(255)")
    @NotNullField
    private String phoneNumber;

    @Coloumn(colName = "age",colType = "INTEGER")
    @NotNullField
    private int age;

    @Coloumn(colName = "weight",colType = "FLOAT")
    @NotNullField
    private double weight;

    public UserDataSetExtended() {
    }

    public UserDataSetExtended(String name, String phoneNumber, int age, float weight) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.weight = weight;
    }
    public UserDataSetExtended(long id,String name, String phoneNumber, int age, float weight) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}

