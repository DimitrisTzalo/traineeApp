package myy803.springboot.trainee.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "committees")
public class Committee {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "committee_id")
    private Integer committeeId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "committee_id")
    private User user;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "committee_name")
    private String committeeName;


    public Committee() {
        super();
    }

    public Committee(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }
}
