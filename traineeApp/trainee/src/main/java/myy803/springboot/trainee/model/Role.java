package myy803.springboot.trainee.model;

public enum Role {
	USER("User"),
    STUDENT("Student");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
