package by.bsuir.app.entity.enums;

public enum SecurityType {
    SURETY("Поручительство"),
    PLEDGE("Залог"),
    FORFEIT("Неустойка"),
    INSURANCE("Страхование"),
    ALL("Все");

    private String rus;

    SecurityType(String rus) {
        this.rus = rus;
    }

    public String getRus() {
        return rus;
    }

}
