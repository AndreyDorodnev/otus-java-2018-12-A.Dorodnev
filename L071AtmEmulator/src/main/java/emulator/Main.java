package emulator;

public class Main {

    public static void main(String[] args) {
        useAtm();
    }

    private static void useAtm(){
        Card ruCard = new Card("User","1234", Banknote.Currency.RUB);
        Card usCard = new Card("User","1234", Banknote.Currency.USD);
        ruCard.addMoney(31500);
        usCard.addMoney(15150);
        Atm atm = new Atm();
        AtmManager atmManager = new AtmManager(atm);
        atmManager.printAtmBalance(Banknote.Currency.RUB);
        atmManager.setAtmStartState();
        atmManager.getAtmBanknotesInfo();
        atmManager.printAtmBalance(Banknote.Currency.RUB);
        atmManager.printAtmBalance(Banknote.Currency.USD);

        atmManager.inputCardInAtm(ruCard,"1234");
        atmManager.printCardBalance();
        atmManager.putMoneyOnCard(Banknote.getBanknote(Banknote.Values.Rub_100),2);
        atmManager.putMoneyOnCard(Banknote.getBanknote(Banknote.Values.Usd_100),2);
        atmManager.printCardBalance();
        atmManager.printAtmBalance(Banknote.Currency.RUB);
        atmManager.getAtmBanknotesInfo();
        atmManager.getMoneyFromCard(27500, Banknote.Currency.RUB);
        atmManager.getAtmBanknotesInfo();
        atmManager.printAtmBalance(Banknote.Currency.RUB);
        atmManager.takeCardFromAtm();

        atmManager.inputCardInAtm(usCard,"1234");
        atmManager.printCardBalance();
        atmManager.putMoneyOnCard(Banknote.getBanknote(Banknote.Values.Rub_100),2);
        atmManager.putMoneyOnCard(Banknote.getBanknote(Banknote.Values.Usd_100),2);
        atmManager.printCardBalance();
        atmManager.printAtmBalance(Banknote.Currency.USD);
        atmManager.getAtmBanknotesInfo();
        atmManager.getMoneyFromCard(1550, Banknote.Currency.USD);
        atmManager.printCardBalance();
        atmManager.printAtmBalance(Banknote.Currency.USD);

    }


}
