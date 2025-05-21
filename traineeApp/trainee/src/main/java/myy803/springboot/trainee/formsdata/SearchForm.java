package myy803.springboot.trainee.formsdata;

public class SearchForm {
    private Integer studentId;
    private String criteria; // values: "location", "interest", "both"

    // Getters & Setters
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
