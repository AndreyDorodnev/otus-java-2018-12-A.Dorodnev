package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "userOld")
public class UserDataSetOld extends DataSet {

    @Column(name = "name")
    String name;
    @Column(name = "age")
    int age;

    public UserDataSetOld() {
    }

    public UserDataSetOld(String name, int age) {
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

    @Override
    public String toString() {
        return "UserDataSetOld{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
