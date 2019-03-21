package model;

public class FullAdress extends Adress {
    private long apartment;
    public FullAdress(String street, String city, int build) {
        super(street, city, build);
        this.apartment = 10L;
    }

}
