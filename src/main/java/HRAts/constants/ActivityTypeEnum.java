package HRAts.constants;

public enum ActivityTypeEnum {
    ADD("Create"),
    UPDATE("Update"),
    OTHER("Other");

    private final String text;

    ActivityTypeEnum(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}