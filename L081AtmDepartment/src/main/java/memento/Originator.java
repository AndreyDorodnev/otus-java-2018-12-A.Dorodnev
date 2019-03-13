package memento;

import memento.Memento;

public interface Originator {
    Memento OriginatorMemento();
    void Revert(Memento memento);
}
