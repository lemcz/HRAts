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

    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private String zipCode;
    private String website;

    @Column(name="date_entered", nullable = false)
    private Date dateEntered;
    @Column(name="date_modified", nullable = false)
    private Date dateModified;

    @OneToMany(mappedBy = "company", targetEntity = Department.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Department> departmentList;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Attachment> attachmentList;

    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="sector_company",
            joinColumns = @JoinColumn(name="company_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="sector_id", referencedColumnName="id"))
    private List<Sector> sectorList;

    public Company() {}

    public Company(String name, String note, String phoneNumber, String address, String zipCode,
                   String city, String country, String website, Date dateEntered, Date dateModified,
                   List<Department> departmentList, List<Attachment> attachmentList, List<Sector> sectorList) {
        super();
        this.name = name;
        this.note = note;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.website = website;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
