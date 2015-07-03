package HRAts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "department")
public class Department implements Serializable {

    @Id
    @GenericGenerator(name="department_id_gen" , strategy="increment")
    @GeneratedValue(generator="department_id_gen")
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name="name", nullable = false)
    private String name;
    @Column(name="note", nullable = true)
    private String note;
    @Column(name="date_entered", nullable = false)
    private Date dateEntered;
    @Column(name="date_modified", nullable = false)
    private Date dateModified;

    @JsonManagedReference("department-vacancy")
    @OneToMany(mappedBy="department", targetEntity=Vacancy.class, fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Vacancy> vacancies;

    @JsonBackReference("user-department")
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private User manager;

    @JsonBackReference("company-department")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public Department() {}

    public Department(String name, String note, Date dateEntered, Date dateModified, List<Vacancy> vacancies, User owner, User manager, Company company){
        super();
        this.name = name;
        this.note = note;
        this.dateEntered = dateEntered;
        this.dateModified = dateModified;
        this.vacancies = vacancies;
        this.owner = owner;
        this.manager = manager;
        this.company = company;
    }

    @PrePersist
    protected void onCreate() {
        Date creationDate = new Date();
        setDateEntered(creationDate);
        setDateModified(creationDate);
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
