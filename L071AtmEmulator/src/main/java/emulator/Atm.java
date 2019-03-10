package emulator;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Atm {
    private Card card;
    private boolean hasCard;
    private AtmMessage message;
    private Map<Banknote,Integer> banknotes = new TreeMap<Banknote, Integer>(Collections.reverseOrder());

    public Atm(){
        card = null;
        hasCard = false;
        initBanknotes();
    }

    public enum  AtmMessage {
        No_card,
        Not_enough_card,
        Not_enough_atm,
        Success_operation,
        Error_get_money,
        Wrong_input,
        Wrong_pin
    }

    private void initBanknotes(){
        for(Banknote.Values value: Banknote.Values.values()) {
            banknotes.put(Banknote.getBanknote(value), 0);
        }
    }

    public AtmMessage getMessage(){
        return message;
    }

    public boolean putBanknote(Banknote banknote,int val){
        if((banknote!=null)&&(val>0)){
            int amount = banknotes.get(banknote);
            banknotes.put(banknote,amount+val);
            return true;
        }
        return false;
    }

    public void putBanknotes(Map<Banknote,Integer> inputBanknotes){
        if(inputBanknotes!=null){
            for(Banknote banknote:inputBanknotes.keySet()){
                int amount = inputBanknotes.get(banknote);
                banknotes.put(banknote,banknotes.get(banknote) + amount);
            }
        }
    }

    public int getAtmTotalBalance(){
        int result = 0;
        for (Banknote banknote:banknotes.keySet()){
            result += banknote.getValue()*banknotes.get(banknote);
        }
        return result;
    }

    public void getAtmBanknotesInfo(){
        for(Banknote banknote:banknotes.keySet())
            System.out.println("Banknote val:" + banknote.getValue() + " " + banknote.getCurrency() + " amount:" + banknotes.get(banknote));
    }

    public boolean inputCard(Card userCard, String pinNumber){
        if(userCard!=null){
            if(userCard.checkPinCode(pinNumber)){
                card = userCard;
                hasCard = true;
                message = AtmMessage.Success_operation;
                return true;
            }
            message = AtmMessage.Wrong_pin;
        }
        else
            message = AtmMessage.Wrong_input;
        return false;

    }

    public void takeCard(){
        card = null;
        hasCard = false;
    }

    public String getCardBalance(){
        if(hasCard)
            return String.valueOf(card.getBalance());
        message = AtmMessage.No_card;
        return "No card available";
    }

    public boolean putMoneyOnCard(Banknote banknote,int amount){
        if((banknote!=null)&&(amount>0)){
            if(hasCard){
                    int num = banknotes.get(banknote);
                    banknotes.put(banknote,num + amount);
                    card.addMoney(banknote.getValue()*amount);
                    message = AtmMessage.Success_operation;
                    return true;
            }
            message = AtmMessage.No_card;
        }
        else
            message = AtmMessage.Wrong_input;
        return false;
    }

    public boolean getMoneyFromCard(int summ){
        if(summ>0){
            if(hasCard){
                if((card.getBalance()>=summ)){
                    if(getAtmTotalBalance()>=summ){
                        return getSumm(calcRequiredBanknotes(summ),summ);
                    }
                    else
                        message =  AtmMessage.Not_enough_atm;
                }
                else
                    message =   AtmMessage.Not_enough_card;
            }
            else
                message =   AtmMessage.No_card;
        }
        else
            message =   AtmMessage.Wrong_input;
        return false;
    }

    private Map<Banknote,Integer> calcRequiredBanknotes(int summ){
        Map<Banknote,Integer> requiredBanknotes = new TreeMap<Banknote, Integer>();
        int val = 0;
        for(Banknote banknote:banknotes.keySet()){
            for(int i = banknotes.get(banknote);i>0;i--){
                if(summ - banknote.getValue()>0){
                    summ -= banknote.getValue();
                    val++;
                    requiredBanknotes.put(banknote,val);
                    continue;
                }
                else if(summ - banknote.getValue()<0){
                    val=0;
                    break;
                }
                else {
                    val++;
                    requiredBanknotes.put(banknote,val);
                    return requiredBanknotes;
                }
            }
        }
        return null;
    }

    private boolean getSumm(Map<Banknote,Integer> requiredBanknotes,int summ){
        if(requiredBanknotes==null){
            message = AtmMessage.Error_get_money;
            return false;
        }
        for(Banknote banknote: requiredBanknotes.keySet()){
            banknotes.put(banknote,banknotes.get(banknote) - requiredBanknotes.get(banknote));
        }
        card.takeMoney(summ);
        message =  AtmMessage.Success_operation;
        return true;
    }

}
