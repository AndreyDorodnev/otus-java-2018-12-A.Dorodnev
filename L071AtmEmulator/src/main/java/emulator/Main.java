package emulator;

public class Main {

    public static void main(String[] args) {
        UseManager();
    }

    private static void UseManager(){
        Card card = new Card("1234",31500);
        Atm atm = new Atm();
        AtmManager atmManager = new AtmManager(atm);
        atmManager.printAtmBalance();
        atmManager.setAtmStartState();
        atmManager.printAtmBalance();
        atmManager.inputCardInAtm(card,"123");
        atmManager.inputCardInAtm(card,"1234");
        atmManager.printCardBalance();
        atmManager.putMoneyOnCard(Banknote.getBanknote(Banknote.Values.Rub_100),5);
        atmManager.printCardBalance();
        atmManager.getMoneyFromCard(22500);
        atmManager.printAtmBalance();
        atmManager.printCardBalance();
        atmManager.getAtmBanknotesInfo();
        atmManager.getMoneyFromCard(1350);
        atmManager.printAtmBalance();
        atmManager.printCardBalance();
        atmManager.getAtmBanknotesInfo();
        atmManager.getMoneyFromCard(1300);
        atmManager.printAtmBalance();
        atmManager.printCardBalance();
        atmManager.getAtmBanknotesInfo();

    }

}
