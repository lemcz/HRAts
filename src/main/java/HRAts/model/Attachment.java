package HRAts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Attachment implements Serializable{

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private String filePath;

    @JsonIgnore
    @Transient
    private List<MultipartFile> file;

    @Column(name="date_entered", nullable = false)
    private Date dateEntered;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JsonBackReference("attachment-candidate")
    @JoinColumn(name = "candidate_id", nullable = true, updatable = false)
    private User candidate;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JsonBackReference("attachment-company")
    @JoinColumn(name = "company_id", nullable = true, updatable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JsonBackReference("attachment-vacancy")
    @JoinColumn(name = "vacancy_id", nullable = true, updatable = false)
    private Vacancy vacancy;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JsonBackReference("attachment-contact")
    @JoinColumn(name = "contact_id", nullable = true, updatable = false)
    private User contact;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference("attachment-owner")
    @JoinColumn(name = "owner_id", nullable = false, updatable = false)
    private User owner;

    public Attachment(){}

    public Attachment(String name, String filePath, List<MultipartFile> file, Date dateEntered, User candidate, Company company, Vacancy vacancy, User contact, User owner){
        super();
        this.name = name;
        this.filePath = filePath;
        this.file = file;
        this.dateEntered = dateEntered;
        this.candidate = candidate;
        this.company = company;
        this.vacancy = vacancy;
        this.contact = contact;
        this.owner = owner;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public User getContact() {
        return contact;
    }

    public void setContact(User contact) {
        this.contact = contact;
    }

    public User getCandidate() {
        return candidate;
    }

    public void setCandidate(User candidate) {
        this.candidate = candidate;
    }

    public List<MultipartFile> getFile() {
        return file;
    }

    public void setFile(List<MultipartFile> file) {
        this.file = file;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
