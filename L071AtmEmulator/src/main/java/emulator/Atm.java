package emulator;

import java.util.HashMap;
import java.util.Map;

public class Atm {
    private Card card;
    private Map<Banknote,MoneyCell> cellMap = new HashMap<>();

    public Atm(){
        card = null;
        initBanknotes();
    }

    private void initBanknotes(){
        for(Banknote.Values value: Banknote.Values.values()){
            Banknote banknote = Banknote.getBanknote(value);
            cellMap.put(banknote,new MoneyCell(banknote));
        }
    }

    public void putBanknote(Banknote banknote,int val){
        if(banknote==null)
            throw new NullPointerException("No banknote");
        if(val<=0)
            throw new IllegalArgumentException("Wrong input data");
        cellMap.get(banknote).add(val);
    }

    void checkCard(){
        if(card==null)
            throw new NullPointerException("No card");
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

    public boolean inputCard(Card userCard, String pinNumber){
        if(userCard==null)
            throw new NullPointerException("no card");
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

    public String getCardBalance(){
        checkCard();
        return String.valueOf(card.getBalance());
    }

    public String getCardCurrency(){
        checkCard();
        return String.valueOf(card.getCurrency());
    }

    public void putMoneyOnCard(Banknote banknote,int amount){
        checkCard();
        if(banknote==null)
            throw new NullPointerException("Wrong banknote");
        if((amount<0)||(!banknote.getCurrency().equals(card.getCurrency())))
            throw new IllegalArgumentException("Wrong input data");
        cellMap.get(banknote).add(amount);
        card.addMoney(banknote.getValue()*amount);
    }

    public boolean getMoneyFromCard(int summ,Banknote.Currency currency) throws NotEnoughMoneyException {
        checkCard();
        if(summ>0){
            if(card.getCurrency().equals(currency)){
                if((card.getBalance()>summ)){
                    if(getAtmBalance(currency)>summ){
                        return getSumm(calcRequiredBanknotes(summ,currency),summ);
                    }
                    throw new NotEnoughMoneyException("Not enough money in ATM");
                }
                throw new NotEnoughMoneyException("Not enough money on card");
            }
            throw new IllegalArgumentException("Wrong card currency");
        }
        throw new IllegalArgumentException("Wrong input data");
    }

    private Map<Banknote,Integer> calcRequiredBanknotes(int summ, final Banknote.Currency currency){
        Map<Banknote,Integer> requiredBanknotes = new HashMap<>();
        int val = 0;
        int calcSumm = summ;
        MoneyCell[] cells = cellMap.values().stream()
                .filter(a->a.getCurrency().equals(currency))
                .sorted((o1, o2) -> {
                    if(o1.getBanknoteValue()>o2.getBanknoteValue())
                        return -1;
                    else if(o1.getBanknoteValue()==o2.getBanknoteValue())
                        return 0;
                    return 1;
                }).toArray(MoneyCell[]::new);
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

}
