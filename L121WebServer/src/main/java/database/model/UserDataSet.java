package database.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "userData")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @Column(name = "role")
    private Roles role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",nullable = false)
    private List<PhoneDataSet> phones = new ArrayList<PhoneDataSet>();




    public UserDataSet() {
    }

    public UserDataSet(String name,String password,int age,AddressDataSet address,Roles role,List<PhoneDataSet> phones){
       this.setId(-1);
       this.name = name;
       this.password = password;
       this.age = age;
       this.address = address;
       this.role = role;
       this.phones.addAll(phones);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
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
        return "{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", role=" + role +
                ", phones=" + phones +
                '}';
    }
}
