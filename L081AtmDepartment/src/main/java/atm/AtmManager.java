package atm;

public class AtmManager {
    Atm atm;

    public AtmManager(Atm atm){
        this.atm = atm;
    }

    public void setAtm(Atm atm){
        this.atm = atm;
    }

    public void setAtmStartState(){
        System.out.println("put money in atm");
        try {
            atm.putBanknote(Banknote.getBanknote(Banknote.Values.Rub_100),5);
            atm.putBanknote(Banknote.getBanknote(Banknote.Values.Rub_200),5);
            atm.putBanknote(Banknote.getBanknote(Banknote.Values.Rub_500),5);
            atm.putBanknote(Banknote.getBanknote(Banknote.Values.Rub_1000),5);
            atm.putBanknote(Banknote.getBanknote(Banknote.Values.Rub_2000),5);
            atm.putBanknote(Banknote.getBanknote(Banknote.Values.Rub_5000),5);
            atm.putBanknote(Banknote.getBanknote(Banknote.Values.Usd_50),5);
            atm.putBanknote(Banknote.getBanknote(Banknote.Values.Usd_100),5);
            atm.putBanknote(Banknote.getBanknote(Banknote.Values.Usd_1000),5);
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    public void putBanknoteInAtm(Banknote banknote, int amount){
        try {
            System.out.println("put " + banknote.getValue() + " " + banknote.getCurrency() + " amount " + amount + " in atm " + atm.getId());
            atm.putBanknote(banknote,amount);
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    public void printAtmBalance(Banknote.Currency currency){
        System.out.println("Atm balance " + currency + ": " + atm.getAtmBalance(currency));
    }

    public void printCardBalance(){
        try {
            System.out.println("Card balance: " + atm.getCardBalance() + " " + atm.getCardCurrency());
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    public void inputCardInAtm(Card card,String pin){
        try {
            if(atm.inputCard(card,pin))
                System.out.println("Card input success");
            else System.out.println("Wrong pin number!");
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    public void getAtmBanknotesInfo(){
        System.out.println("Atm banknotes information:");
        atm.getAtmBanknotesInfo();
    }

    public void putMoneyOnCard(Banknote banknote,int amount){
        try {
            System.out.println("Try to put on card banknote value: " + banknote.getValue() + " " + banknote.getCurrency() + ". Amount: " + amount);
            atm.putMoneyOnCard(banknote,amount);
            System.out.println("Success");
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    public void getMoneyFromCard(int summ,Banknote.Currency currency){
        try {
            System.out.println("Try to isuue " + summ + " " + currency + " from card");
            atm.getMoneyFromCard(summ,currency);
            System.out.println("Success");
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void takeCardFromAtm(){
        System.out.println("Please, take your card");
        atm.takeCard();
    }

    public void printAtmId(){
        System.out.println("Atm id: " + atm.getId());
    }

}
