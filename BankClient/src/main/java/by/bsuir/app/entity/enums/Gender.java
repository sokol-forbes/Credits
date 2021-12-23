package by.bsuir.app.entity.enums;

public enum Gender {
    MALE("Мужской"),
    FEMALE("Женский"),
    UNDEFINED("---");

    private String rusName;

    Gender(String rusName) {
        this.rusName = rusName;
    }

    public String getGender() {
        return rusName;
    }

    public static Gender convertRusNameToEnum(String rusName) {
        for (Gender g: Gender.values()) {
            if (rusName.equals(g.getGender())){
                return g;
            }
        }
        return UNDEFINED;
    }
}
