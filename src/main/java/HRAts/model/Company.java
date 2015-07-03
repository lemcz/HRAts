package HRAts.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="company")
public class Company implements Serializable{

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue
    private int id;

    @Column(name="name", nullable = false)
    private String name;
    @Column(name="note", nullable = true)
    private String note;
    @Column(name="date_entered", nullable = false)
    private Date dateEntered;
    @Column(name="date_modified", nullable = false)
    private Date dateModified;

    @OneToMany(mappedBy = "company", targetEntity = Department.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Department> department;

    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="sector_company",
            joinColumns = @JoinColumn(name="company_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="sector_id", referencedColumnName="id"))
    private List<Sector> sectorList;

    public Company() {}

    public Company(String name, String note, Date dateEntered, Date dateModified, List<Department> department, List<Sector> sectorList) {
        super();
        this.name = name;
        this.note = note;
        this.dateEntered = dateEntered;
        this.dateModified = dateModified;
        this.department = department;
        this.sectorList = sectorList;
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

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
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

    public List<Sector> getSectorList() {
        return sectorList;
    }

    public void setSectorList(List<Sector> sectorList) {
        this.sectorList = sectorList;
    }

    public List<Department> getDepartment() {
        return department;
    }

    public void setDepartment(List<Department> department) {
        this.department = department;
    }
}
