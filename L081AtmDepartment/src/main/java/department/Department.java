package department;

import atm.Atm;
import atm.AtmManager;
import atm.Banknote;
import memento.Caretaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Department implements ISubject {

    private ArrayList<Atm> atmList = new ArrayList<Atm>();
    private Map<String, Caretaker> caretakerMap = new HashMap<String, Caretaker>();
    private AtmManager manager;

    @Override
    public void register(Atm atm) {
        if(atm!=null){
            atmList.add(atm);
            Caretaker caretaker = new Caretaker();
            caretaker.SaveMemento(atm.OriginatorMemento());
            caretakerMap.put(atm.getId(),caretaker);
        }
    }

    @Override
    public void unregister(Atm atm) {
        if(atm!=null)
            atmList.remove(atm);
    }

    @Override
    public void notifyObservers() {
        for(Atm atm:atmList){
            atm.update(caretakerMap.get(atm.getId()).RetrieveMemento());
        }
    }
    public void getAtmBanknotesInfo(){
        for(Atm atm:atmList){
            manager = new AtmManager(atm);
            manager.printAtmId();
            manager.getAtmBanknotesInfo();
            manager.printAtmBalance(Banknote.Currency.RUB);
            manager.printAtmBalance(Banknote.Currency.USD);
        }
    }

    public void getAtmBalance(){
        for(Atm atm:atmList){
            manager = new AtmManager(atm);
            manager.printAtmId();
            manager.printAtmBalance(Banknote.Currency.RUB);
            manager.printAtmBalance(Banknote.Currency.USD);
        }
    }
}
