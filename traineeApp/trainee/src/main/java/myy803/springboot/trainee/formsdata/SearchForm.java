package myy803.springboot.trainee.formsdata;

public class SearchForm {
    private String selectedUsername;
    private String criteria; // values: "location", "interest", "both"

    // Getters & Setters
    public String getSelectedUsername() {return selectedUsername;}

    public void setSelectedUsername(String studentUserName) {this.selectedUsername = studentUserName;}

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
