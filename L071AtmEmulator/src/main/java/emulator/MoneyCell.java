package emulator;

public class MoneyCell implements Comparable {
    private Banknote banknote;
    private int amount;

    public MoneyCell(Banknote banknote){
        this.banknote = banknote;
        amount = 0;
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

    public int compareTo(Object o) {
        if(this.getBanknoteValue()>((MoneyCell)o).getBanknoteValue())
            return 1;
        else if(this.getBanknoteValue()==((MoneyCell)o).getBanknoteValue())
            return 0;
        return -1;
    }
}