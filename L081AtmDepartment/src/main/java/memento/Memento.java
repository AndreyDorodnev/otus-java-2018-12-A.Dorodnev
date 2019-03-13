package memento;

import atm.Banknote;
import atm.MoneyCell;

import java.util.HashMap;
import java.util.Map;

public class Memento {
    public Map<Banknote, MoneyCell> state;

    public Memento(Map<Banknote,MoneyCell> source){
        this.state = new HashMap<>();
        for(Banknote banknote:source.keySet()){
            state.put(banknote,new MoneyCell(banknote,source.get(banknote).getAmount()));
        }
    }

    public Map<Banknote,MoneyCell> getState(){
        return state;
    }
}
