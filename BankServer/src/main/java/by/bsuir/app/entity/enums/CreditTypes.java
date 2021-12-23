package by.bsuir.app.entity.enums;

public enum CreditTypes {
    CONSUMED("Потребительский"),
    REALTY("На недвижимость");

    private String rus;

    CreditTypes(String rus) {
        this.rus = rus;
    }

    public String getRus() {
        return rus;
    }

}
