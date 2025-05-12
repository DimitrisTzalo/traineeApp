package myy803.springboot.trainee.model;

public enum Role {
	STUDENT("Student"),
    PROFESSOR("Professor"),
    COMPANY("Company"),
    COMMITTEE("Committee"),;

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
