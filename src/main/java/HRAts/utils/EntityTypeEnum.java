package HRAts.utils;

public enum EntityTypeEnum {
    CANDIDATE("Candidate"),
    COMPANY("Company"),
    CONTACT("Contact"),
    VACANCY("Vacancy");


    private final String text;

    EntityTypeEnum(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
