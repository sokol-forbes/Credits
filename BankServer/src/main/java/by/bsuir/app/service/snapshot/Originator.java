package by.bsuir.app.service.snapshot;

import by.bsuir.app.entity.BaseEntity;

import java.util.List;

public class Originator {

    private List<? extends BaseEntity> storage;

    public List<? extends BaseEntity> getStorage() {
        return storage;
    }

    public void setStorage(List<? extends BaseEntity> storage) {
        this.storage = storage;
    }

    public Memento createMemento() {
        return new Memento(storage);
    }

    public void setMemento(Memento memento) {
        this.storage = memento.getStorage();
    }
}
