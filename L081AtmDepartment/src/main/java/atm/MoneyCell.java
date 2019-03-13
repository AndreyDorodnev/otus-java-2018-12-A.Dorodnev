package atm;

public class MoneyCell {
    private Banknote banknote;
    private int amount;

    public MoneyCell(Banknote banknote){
        this.banknote = banknote;
        amount = 0;
    }

    public MoneyCell(Banknote banknote,int amount){
        this.banknote = banknote;
        this.amount = amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public void add(int amount){
        this.amount += amount;
    }

    public void take(int amount){
        this.amount -= amount;
    }

    public int getAmount(){
        return amount;
    }

    public int getBanknoteValue(){
        return banknote.getValue();
    }

    public int getBalance(){
        return amount*banknote.getValue();
    }

    public Banknote.Currency getCurrency(){
        return banknote.getCurrency();
    }

    public Banknote getBanknote(){
        return banknote;
    }

}
