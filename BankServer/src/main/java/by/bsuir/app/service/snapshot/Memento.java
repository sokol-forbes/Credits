package by.bsuir.app.service.snapshot;

import by.bsuir.app.entity.BaseEntity;

import java.util.List;

public record Memento(List<? extends BaseEntity> storage) {

    public List<? extends BaseEntity> getStorage() {
        return storage;
    }

}
