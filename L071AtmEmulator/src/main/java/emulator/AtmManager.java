package emulator;

public class AtmManager {

    Atm atm;

    public AtmManager(Atm atm){
        this.atm = atm;
    }

    public void setAtmStartState(){
        System.out.println("put money in atm");
        atm.putBanknote(Banknote.getBanknote(Banknote.Values.Rub_100),5);
        atm.putBanknote(Banknote.getBanknote(Banknote.Values.Rub_200),5);
        atm.putBanknote(Banknote.getBanknote(Banknote.Values.Rub_500),5);
        atm.putBanknote(Banknote.getBanknote(Banknote.Values.Rub_1000),5);
        atm.putBanknote(Banknote.getBanknote(Banknote.Values.Rub_2000),5);
        atm.putBanknote(Banknote.getBanknote(Banknote.Values.Rub_5000),5);
    }

    public void printAtmBalance(){
        System.out.println("Atm balance: " + atm.getAtmTotalBalance());
    }

    public void printCardBalance(){
        System.out.println("Card balance: " + atm.getCardBalance());
    }

    public void inputCardInAtm(Card card,String pin){
        if(card!=null){
            if(atm.inputCard(card,pin))
                System.out.println("Card input success");
            else System.out.println("Wrong pin number!");
        }
        else
            System.out.println("Error!");
    }

    public void getAtmBanknotesInfo(){
        System.out.println("Atm banknotes information:");
        atm.getAtmBanknotesInfo();
    }

    public void putMoneyOnCard(Banknote banknote,int amount){
        System.out.println("Put on card banknote value: " + banknote.getValue() + " " + banknote.getCurrency() + ". Amount: " + amount);
        if(atm.putMoneyOnCard(banknote,amount))
            System.out.println("Success");
        else
            printMessage();
    }

    public void getMoneyFromCard(int summ){
        System.out.println("Try to isuue " + summ + " from card");
        if(atm.getMoneyFromCard(summ))
            System.out.println("Success");
        else
            printMessage();
    }

    public void takeCardFromAtm(){
        System.out.println("Please, take your card");
        atm.takeCard();
    }

    private void printMessage(){
        switch (atm.getMessage()){
            case No_card:
                System.out.println("Please, input a card in ATM");
                break;
            case Wrong_input:
                System.out.println("Wrong input data!");
                break;
            case Success_operation:
                System.out.println("Success");
                break;
            case Not_enough_atm:
                System.out.println("Sorry, ATM have no enough money for this operation");
                break;
            case Error_get_money:
                System.out.println("Error to issue money");
                break;
            case Not_enough_card:
                System.out.println("You have no enough money for this operation on your card");
                break;
                default:
                    System.out.println("Unknown error!");
        }
    }

}
