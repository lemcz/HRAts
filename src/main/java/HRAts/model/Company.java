package HRAts.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @JsonManagedReference("company-department")
    @OneToMany(mappedBy = "company", targetEntity = Department.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Department> departmentList;

    @JsonManagedReference("attachment-company")
    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Attachment> attachmentList;

    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="sector_company",
            joinColumns = @JoinColumn(name="company_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="sector_id", referencedColumnName="id"))
    private List<Sector> sectorList;

    public Company() {}

    public Company(String name, String note, Date dateEntered, Date dateModified, List<Department> departmentList, List<Attachment> attachmentList, List<Sector> sectorList) {
        super();
        this.name = name;
        this.note = note;
        this.dateEntered = dateEntered;
        this.dateModified = dateModified;
        this.departmentList = departmentList;
        this.attachmentList = attachmentList;
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

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
