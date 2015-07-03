package HRAts.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class VacancyUser implements Serializable{

    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private int id;

    @ManyToOne
    @PrimaryKeyJoinColumn(name="vacancy_id", referencedColumnName="id")
    private Vacancy vacancy;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private User candidate;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @OneToMany(mappedBy = "vacancyUser", fetch = FetchType.EAGER)
    private List<Status> statusList;

    public VacancyUser() {}

    public VacancyUser(Vacancy vacancy, User candidate, User owner, List<Status> statusList) {
        super();
        this.vacancy = vacancy;
        this.candidate = candidate;
        this.owner = owner;
        this.statusList = statusList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public User getCandidate() {
        return candidate;
    }

    public void setCandidate(User candidate) {
        this.candidate = candidate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }
}
