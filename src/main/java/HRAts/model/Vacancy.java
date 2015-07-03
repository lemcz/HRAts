package HRAts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(name = "date_entered")
    private Date dateEntered;
    @Column(name = "date_modified")
    private Date dateModified;

    @JsonManagedReference("vacancy-vacancy_user")
    @OneToMany(mappedBy = "vacancy", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<VacancyUser> vacancyUserList;

    //TODO add nullable = true, optional = false where needed (omitted to make it easier to develop front-end)
    @JsonBackReference("department-vacancy")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    public Vacancy(){}

    public Vacancy(String name, int numberOfVacancies, String description, String note, Date dateEntered,
                   Date dateModified, List<VacancyUser> vacancyUserList, Department department){
        super();
        this.name = name;
        this.numberOfVacancies = numberOfVacancies;
        this.description = description;
        this.note = note;
        this.dateEntered = dateEntered;
        this.dateModified = dateModified;
        this.vacancyUserList = vacancyUserList;
        this.department = department;
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
}