package model;

import java.util.ArrayList;

public class UserWithCollection {
    private String name;
    private Integer age;
    private Adress adress;
    private ArrayList<Phone> phones;
    private int[] buff = new int[3];

    public UserWithCollection(String name, int age, Adress adress) {
        this.name = name;
        this.age = age;
        this.adress = adress;
        phones = new ArrayList<Phone>();
        buff[0] = 5;
        buff[1] = 5;
        buff[2] = 5;
    }

    public void addPhone(String number){
        phones.add(new Phone(number));
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

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }
}
