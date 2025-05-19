package myy803.springboot.trainee.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(nullable = false, name = "company_id")
    private Integer company_id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "company_id")
    private User user;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "company_name")
    private String companyName;

    @Column(nullable = false, name = "company_location")
    private String companyLocation;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TraineePosition> companyPositions;

    public Company() {super();}
    public Company(String username) {this.username = username;}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCompany_id() {return company_id;}
    public void setCompany_id(Integer company_id) {this.company_id = company_id;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getCompanyName() {return companyName;}
    public void setCompanyName(String companyName) {this.companyName = companyName;}

    public String getCompanyLocation() {return companyLocation;}
    public void setCompanyLocation(String companyLocation) {this.companyLocation = companyLocation;}

    public List<TraineePosition> getCompanyPositions() {return companyPositions;}
    public void setCompanyPositions(List<TraineePosition> companyPositions) {this.companyPositions = companyPositions;}

}
