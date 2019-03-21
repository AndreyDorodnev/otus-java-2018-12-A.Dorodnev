package model;

public class User {
    private String name;
    private Integer age;
    private FullAdress adress;

    public User(String name, int age, FullAdress adress) {
        this.name = name;
        this.age = age;
        this.adress = adress;
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

    public FullAdress getAdress() {
        return adress;
    }

    public void setAdress(FullAdress adress) {
        this.adress = adress;
    }
}
