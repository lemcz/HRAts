package HRAts.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "status")
public class Status implements Serializable{

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue
    private int id;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Column(name = "note")
    private String note;

    @Column(name = "date_entered")
    private Date dateEntered;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "status_type_id", nullable = false, insertable = false, updatable = false)
    private StatusTypeLkp statusTypeId;

    @ManyToOne(optional = true)
    @JoinColumn(name = "vacancy_user_id", referencedColumnName = "id")
    private VacancyUser vacancyUser;

    public Status(){}

    public Status(boolean isActive, String note, Date dateEntered, StatusTypeLkp statusTypeId, VacancyUser vacancyUser){
        super();
        this.isActive = isActive;
        this.note = note;
        this.dateEntered = dateEntered;
        this.statusTypeId = statusTypeId;
        this.vacancyUser = vacancyUser;
    }

    @PrePersist
    protected void onCreate() {
        Date currentDate = new Date();
        setDateEntered(currentDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public StatusTypeLkp getStatusTypeId() {
        return statusTypeId;
    }

    public void setStatusTypeId(StatusTypeLkp statusTypeId) {
        this.statusTypeId = statusTypeId;
    }

    public VacancyUser getVacancyUser() {
        return vacancyUser;
    }

    public void setVacancyUser(VacancyUser vacancyUser) {
        this.vacancyUser = vacancyUser;
    }
}
