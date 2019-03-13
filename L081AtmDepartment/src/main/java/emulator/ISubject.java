package emulator;

import atm.Atm;

public interface ISubject {
    void register(Atm atm);
    void unregister(Atm atm);
    void notifyObservers();
}
