package model;

public class Adress {
    private String street;
    private String city;
    private int build;

    public Adress(String street, String city, int build) {
        this.setStreet(street);
        this.setCity(city);
        this.setBuild(build);
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.build = build;
    }
}
