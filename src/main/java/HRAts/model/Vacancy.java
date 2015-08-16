package HRAts.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vacancy")
public class Vacancy implements Serializable {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "number_of_vacancies", nullable = false)
    private int numberOfVacancies;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "note")
    private String note;
    @Column(name = "salary")
    private int salary;
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "date_entered")
    private Date dateEntered;
    @Column(name = "date_modified")
    private Date dateModified;

    @JsonManagedReference("vacancy-vacancy_user")
    @OneToMany(mappedBy = "vacancy", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<VacancyUser> vacancyUserList;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @JsonManagedReference("attachment-vacancy")
    @OneToMany(mappedBy = "vacancy", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Attachment> attachmentList;

    @JsonManagedReference("activity-vacancy")
    @OneToMany(mappedBy = "vacancy", fetch = FetchType.EAGER)
    private List<Activity> activityList;

    public Vacancy(){}

    public Vacancy(String name, int numberOfVacancies, String description, String note, int salary, Date dateEntered,
                   Date dateModified, List<VacancyUser> vacancyUserList, Department department, List<Attachment> attachmentList, List<Activity> activityList ){
        super();
        this.name = name;
        this.numberOfVacancies = numberOfVacancies;
        this.description = description;
        this.note = note;
        this.salary = salary;
        this.dateEntered = dateEntered;
        this.dateModified = dateModified;
        this.vacancyUserList = vacancyUserList;
        this.department = department;
        this.attachmentList = attachmentList;
        this.activityList = activityList;
    }

    @PrePersist
    protected void onCreate() {
        Date currentDate = new Date();
        setDateEntered(currentDate);
        setDateModified(currentDate);
    }

    @PreUpdate
    protected void onUpdate() {
        setDateModified(new Date());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfVacancies() {
        return numberOfVacancies;
    }

    public void setNumberOfVacancies(int numberOfVacancies) {
        this.numberOfVacancies = numberOfVacancies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<VacancyUser> getVacancyUserList() {
        return vacancyUserList;
    }

    public void setVacancyUserList(List<VacancyUser> vacancyUserList) {
        this.vacancyUserList = vacancyUserList;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }
}
