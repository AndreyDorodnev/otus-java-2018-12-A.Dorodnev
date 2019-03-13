package memento;

import memento.Memento;

public class Caretaker {
    private Memento _memento;
    public void SaveMemento(Memento m)
    {
        _memento = m;
    }
    public Memento RetrieveMemento()
    {
        return _memento;
    }
}
