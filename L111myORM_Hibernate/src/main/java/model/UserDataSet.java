package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "userData")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",nullable = false)
    private List<PhoneDataSet> phones = new ArrayList<PhoneDataSet>();

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;


    public UserDataSet() {
    }

    public UserDataSet(String name,int age,AddressDataSet address,List<PhoneDataSet> phones){
       this.setId(-1);
       this.name = name;
       this.age = age;
       this.address = address;
       this.phones.addAll(phones);
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

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phones=" + phones +
                ", address=" + address +
                '}';
    }
}
