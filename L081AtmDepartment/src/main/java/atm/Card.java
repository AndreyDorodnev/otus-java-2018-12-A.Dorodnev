package atm;

public class Card {
    private int balance;
    private String serialNumber;
    private String pinCode;
    private String userName;
    private Banknote.Currency currency;

    public Card(String pinCode){
        balance = 0;
        userName = "User";
        this.pinCode = pinCode;
        serialNumber =  SerialNumberGenerator.getSerialNumber();
        currency = Banknote.Currency.RUB;
    }

    public Card(String userName,String pinCode,Banknote.Currency currency){
        balance = 0;
        this.userName = userName;
        this.pinCode = pinCode;
        this.currency = currency;
        serialNumber = SerialNumberGenerator.getSerialNumber();
    }

    public int getBalance(){
        return balance;
    }

    public Banknote.Currency getCurrency(){
        return currency;
    }

    public void addMoney(int amount){
        balance += amount;
    }
    public boolean takeMoney(int amount){
        if(balance>=amount){
            balance -= amount;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkPinCode(String pin){
        if(pinCode.equals(pin))
            return true;
        return false;
    }

    public void setUserName(String name){
        userName = name;
    }

    public String getUserName(){
        return userName;
    }
    public String getSerialNumber(){
        return serialNumber;
    }


}
