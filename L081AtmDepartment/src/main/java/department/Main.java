package department;

import atm.Atm;
import atm.AtmManager;
import atm.Banknote;
import atm.Card;

public class Main {

    public static void main(String[] args) {
        departmentTest();
    }

    private static void departmentTest(){
        Department department = new Department();
        Atm atm1 = new Atm("#1");
        Atm atm2 = new Atm("#2");
        Atm atm3 = new Atm("#3");
        AtmManager manager = new AtmManager(atm1);
        manager.putBanknoteInAtm(Banknote.getBanknote(Banknote.Values.Rub_100),5);
        manager.putBanknoteInAtm(Banknote.getBanknote(Banknote.Values.Usd_50),5);
        manager.setAtm(atm2);
        manager.putBanknoteInAtm(Banknote.getBanknote(Banknote.Values.Rub_500),5);
        manager.setAtm(atm3);
        manager.putBanknoteInAtm(Banknote.getBanknote(Banknote.Values.Rub_2000),5);
        department.register(atm1);
        department.register(atm2);
        department.register(atm3);
        department.getAtmBalance();
        manager.setAtm(atm1);
        manager.putBanknoteInAtm(Banknote.getBanknote(Banknote.Values.Rub_5000),2);
        manager.setAtm(atm2);
        manager.putBanknoteInAtm(Banknote.getBanknote(Banknote.Values.Usd_50),5);
        department.getAtmBalance();
        department.notifyObservers();
        department.getAtmBalance();
        manager.setAtm(atm1);
        manager.putBanknoteInAtm(Banknote.getBanknote(Banknote.Values.Rub_5000),2);
        manager.setAtm(atm2);
        manager.putBanknoteInAtm(Banknote.getBanknote(Banknote.Values.Usd_50),5);
        department.getAtmBalance();
        department.notifyObservers();
        department.getAtmBalance();
    }

    private static void newAtmTest(){
        Card ruCard = new Card("User","1234", Banknote.Currency.RUB);
        Card usCard = new Card("User","1234", Banknote.Currency.USD);
        ruCard.addMoney(31500);
        usCard.addMoney(15150);
        Atm atm = new Atm("123");
        AtmManager atmManager = new AtmManager(atm);
        atmManager.printAtmBalance(Banknote.Currency.RUB);
        atmManager.setAtmStartState();
        atmManager.getAtmBanknotesInfo();
        atmManager.printAtmBalance(Banknote.Currency.RUB);
        atmManager.printAtmBalance(Banknote.Currency.USD);
        //ru card test
        atmManager.inputCardInAtm(ruCard,"1234");
        atmManager.printCardBalance();
        atmManager.putMoneyOnCard(Banknote.getBanknote(Banknote.Values.Rub_100),2);
        atmManager.putMoneyOnCard(Banknote.getBanknote(Banknote.Values.Usd_100),2);
        atmManager.printCardBalance();
        atmManager.printAtmBalance(Banknote.Currency.RUB);
        atmManager.getAtmBanknotesInfo();
        atmManager.takeCardFromAtm();
        //en card test
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
