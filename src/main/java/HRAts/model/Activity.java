package HRAts.model;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false, updatable = false)
    private User owner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "candidate_id", nullable = false, updatable = false)
    private User candidate;

    public Activity(){}

    public Activity(ActivityTypeLkp activityType, String note, Date dateEntered, User owner, User candidate) {
        super();
        this.activityType = activityType;
        this.note = note;
        this.dateEntered = dateEntered;
        this.owner = owner;
        this.candidate = candidate;
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
}
