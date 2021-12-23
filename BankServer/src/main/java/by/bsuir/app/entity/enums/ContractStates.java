package by.bsuir.app.entity.enums;

public enum ContractStates {
    IN_PROCESSING("На рассмотрении"),
    DENIED("Отклонено"),
    APPROVED("Одобрено");

    private String state;

    ContractStates(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
