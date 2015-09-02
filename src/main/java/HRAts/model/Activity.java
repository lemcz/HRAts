package HRAts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="activity")
public class Activity {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue
    private int id;

    @Column(name="note", nullable = true)
    private String note;
    @Column(name="date_entered", nullable = false)
    private Date dateEntered;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "activity_type_id", nullable = false, updatable = false)
    private ActivityTypeLkp activityType;

    @ManyToOne
    @JoinColumn(name = "owner_id", updatable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "candidate_id", updatable = false)
    private User candidate;

    @JsonBackReference("activity-vacancy")
    @ManyToOne
    @JoinColumn(name = "vacancy_id", updatable = false)
    private Vacancy vacancy;

    public Activity(){}

    public Activity(ActivityTypeLkp activityType, String note, Date dateEntered, User owner, User candidate, Vacancy vacancy) {
        super();
        this.activityType = activityType;
        this.note = note;
        this.dateEntered = dateEntered;
        this.owner = owner;
        this.candidate = candidate;
        this.vacancy = vacancy;
    }

    @PrePersist
    protected void onCreate() {
        setDateEntered(new Date());
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ActivityTypeLkp getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityTypeLkp activityType) {
        this.activityType = activityType;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getCandidate() {
        return candidate;
    }

    public void setCandidate(User candidate) {
        this.candidate = candidate;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }
}
