package myy803.springboot.trainee.formsdata;

import jakarta.persistence.criteria.CriteriaBuilder;

public class SearchProfessorForm {

    private Integer selectedPosition; // ID θέσης
    private String criteria;       // "interest" ή "load"

    public Integer getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(Integer selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
