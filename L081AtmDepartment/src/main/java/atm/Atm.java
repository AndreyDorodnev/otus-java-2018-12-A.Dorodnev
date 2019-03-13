package atm;

import atmExceptions.BanknoteException;
import atmExceptions.CardException;
import atmExceptions.CurrencyException;
import atmExceptions.NotEnoughMoneyException;
import memento.Memento;
import memento.Originator;

import java.util.*;

public class Atm implements Originator {
    private Card card;
    private Map<Banknote,MoneyCell> cellMap = new HashMap<>();
    private String id;
    private Memento memento;

    public Atm(String id){
        this.id = id;
        card = null;
        initBanknotes();
    }

    public String getId(){
        return id;
    }

    private void initBanknotes(){
        for(Banknote.Values value: Banknote.Values.values()){
            Banknote banknote = Banknote.getBanknote(value);
            cellMap.put(banknote,new MoneyCell(banknote));
        }
    }

    public void putBanknote(Banknote banknote,int val) throws BanknoteException {
        AtmConditionHelper.checkBanknote(banknote);
        AtmConditionHelper.checkSumm(val);
        cellMap.get(banknote).add(val);
    }


    public int getAtmBalance(Banknote.Currency currency){
        int result = 0;
        for(MoneyCell moneyCell: cellMap.values()){
            if(moneyCell.getCurrency().equals(currency))
                result += moneyCell.getBalance();
        }
        return result;
    }

    public void getAtmBanknotesInfo(){
        for(MoneyCell moneyCell: cellMap.values()){
            System.out.println("Banknote val:" + moneyCell.getBanknoteValue() + " " + moneyCell.getCurrency() + " amount:" + moneyCell.getAmount());
        }
    }

    public boolean inputCard(Card userCard, String pinNumber) throws CardException {
        AtmConditionHelper.checkCard(userCard);
        if(userCard.checkPinCode(pinNumber)){
            card = userCard;
            return true;
        }
        else {
            return false;
        }
    }

    public void takeCard(){
        card = null;
    }

    public String getCardBalance() throws CardException {
        AtmConditionHelper.checkCard(card);
        return String.valueOf(card.getBalance());
    }

    public String getCardCurrency() throws CardException {
        AtmConditionHelper.checkCard(card);
        return String.valueOf(card.getCurrency());
    }

    public void putMoneyOnCard(Banknote banknote,int amount) throws CurrencyException, CardException, BanknoteException {
        if(AtmConditionHelper.checkConditionPutMoney(card,banknote,amount)){
            cellMap.get(banknote).add(amount);
            card.addMoney(banknote.getValue()*amount);
        }
    }

    public boolean getMoneyFromCard(int summ,Banknote.Currency currency) throws NotEnoughMoneyException, CurrencyException, CardException {
        if(AtmConditionHelper.checkConditionGetMoney(card,summ,getAtmBalance(currency),currency)){
            return getSumm(calcRequiredBanknotes(summ,currency),summ);
        }
        return false;
    }

    private MoneyCell[] filterCells(final int summ, final Banknote.Currency currency){
        return cellMap.values().stream()
                .filter(a->a.getCurrency().equals(currency))
                .filter(a->a.getBanknoteValue()<summ)
                .sorted((o1, o2) -> {
                    if(o1.getBanknoteValue()>o2.getBanknoteValue())
                        return -1;
                    else if(o1.getBanknoteValue()==o2.getBanknoteValue())
                        return 0;
                    return 1;
                }).toArray(MoneyCell[]::new);
    }

    private Map<Banknote,Integer> calcRequiredBanknotes(int summ, final Banknote.Currency currency){
        Map<Banknote,Integer> requiredBanknotes = new HashMap<>();
        int val = 0;
        int calcSumm = summ;
        MoneyCell[] cells = filterCells(summ,currency);
        for(int start= 0;start<cells.length;start++){
            for (int i = start; i < cells.length; i++){
                for(int amount = cells[i].getAmount();amount>0;amount--){
                    if(calcSumm - cells[i].getBanknoteValue()>0){
                        calcSumm -= cells[i].getBanknoteValue();
                        val++;
                        requiredBanknotes.put(cells[i].getBanknote(),val);
                    }
                    else if(calcSumm - cells[i].getBanknoteValue()<0){
                        val=0;
                        break;
                    }
                    else {
                        val++;
                        requiredBanknotes.put(cells[i].getBanknote(),val);
                        return requiredBanknotes;
                    }
                }
                val=0;
            }
            val=0;
            requiredBanknotes.clear();
            calcSumm = summ;
        }
        return null;
    }

    private boolean getSumm(Map<Banknote,Integer> requiredBanknotes,int summ){
        if(requiredBanknotes==null){
            throw new NullPointerException("Error get money");
        }
        for(Banknote val:requiredBanknotes.keySet()){
            cellMap.get(val).take(requiredBanknotes.get(val));
        }
        card.takeMoney(summ);
        return true;
    }

    @Override
    public Memento OriginatorMemento() {
        memento = new Memento(cellMap);
        return memento;
    }

    @Override
    public void Revert(Memento memento) {
        if(memento!=null){
            for(Banknote banknote:memento.getState().keySet()){
                cellMap.get(banknote).setAmount(memento.getState().get(banknote).getAmount());
            }
        }
    }

    public void update(Memento memento){
        System.out.println("Atm " + getId() +  " reset state....");
        Revert(memento);
    }
}
