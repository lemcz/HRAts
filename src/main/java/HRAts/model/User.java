package HRAts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_account")
public class User implements Serializable{

    @Id
    @GenericGenerator(name="user_id_gen" , strategy="increment")
    @GeneratedValue(generator="user_id_gen")
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;
    @Column
    private String name;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String note;
    //@Column(columnDefinition = "boolean default true")
    private String enabled;
    @JsonIgnore
    private String password;
    @Column(name="date_entered", nullable = false)
    private Date dateEntered;
    @Column(name="date_modified", nullable = false)
    private Date dateModified;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

    @OneToOne(optional = true, cascade = CascadeType.ALL, targetEntity=CandidateInformation.class)
    private CandidateInformation candidateInformation;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("candidate-vacancy_user")
    private List<VacancyUser> vacancyUserCandidateList;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonManagedReference("owner-vacancy_user")
    private List<VacancyUser> vacancyUserOwnerList;

    @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("manager-department")
    private List<Department> departmentList;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("attachment-candidate")
    private List<Attachment> candidateAttachmentList;

    @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("attachment-contact")
    private List<Attachment> contactAttachmentList;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("attachment-owner")
    private List<Attachment> ownerAttachmentList;

    @ManyToOne
    private User owner;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<User> contactList;

    public User () {}

    public User(String email, String name, String middleName, String lastName, String phoneNumber,
                String note, String enabled, String password, Date dateEntered, Date dateModified, Role role,
                CandidateInformation candidateInformation, List<VacancyUser> vacancyUserCandidateList,
                List<VacancyUser> vacancyUserOwnerList, List<Department> departmentList, List<Attachment> candidateAttachmentList, List<Attachment> contactAttachmentList, List<Attachment> ownerAttachmentList, User owner, List<User> contactList) {
        this.email = email;
        this.name = name;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.note = note;
        this.enabled = enabled;
        this.password = password;
        this.dateEntered = dateEntered;
        this.dateModified = dateModified;
        this.role = role;
        this.candidateInformation = candidateInformation;
        this.vacancyUserCandidateList = vacancyUserCandidateList;
        this.vacancyUserOwnerList = vacancyUserOwnerList;
        this.departmentList = departmentList;
        this.candidateAttachmentList = candidateAttachmentList;
        this.contactAttachmentList = contactAttachmentList;
        this.ownerAttachmentList = ownerAttachmentList;
        this.owner = owner;
        this.contactList = contactList;
    }

    @PrePersist
    protected void onCreate() {
        Date currentDate = new Date();
        setDateEntered(currentDate);
        setDateModified(currentDate);
    }

    @PreUpdate
    protected void onUpdate() {
        setDateEntered(this.getDateEntered());
        setDateModified(new Date());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public CandidateInformation getCandidateInformation() {
        return candidateInformation;
    }

    public void setCandidateInformation(CandidateInformation candidateInformation) {
        this.candidateInformation = candidateInformation;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getContactList() {
        return contactList;
    }

    public void setContactList(List<User> contactList) {
        this.contactList = contactList;
    }

    public List<VacancyUser> getVacancyUserCandidateList() {
        return vacancyUserCandidateList;
    }

    public void setVacancyUserCandidateList(List<VacancyUser> vacancyUserCandidateList) {
        this.vacancyUserCandidateList = vacancyUserCandidateList;
    }

    public List<VacancyUser> getVacancyUserOwnerList() {
        return vacancyUserOwnerList;
    }

    public void setVacancyUserOwnerList(List<VacancyUser> vacancyUserOwnerList) {
        this.vacancyUserOwnerList = vacancyUserOwnerList;
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

    public List<Attachment> getOwnerAttachmentList() {
        return ownerAttachmentList;
    }

    public void setOwnerAttachmentList(List<Attachment> ownerAttachmentList) {
        this.ownerAttachmentList = ownerAttachmentList;
    }

    public List<Attachment> getCandidateAttachmentList() {
        return candidateAttachmentList;
    }

    public void setCandidateAttachmentList(List<Attachment> candidateAttachmentList) {
        this.candidateAttachmentList = candidateAttachmentList;
    }

    public List<Attachment> getContactAttachmentList() {
        return contactAttachmentList;
    }

    public void setContactAttachmentList(List<Attachment> contactAttachmentList) {
        this.contactAttachmentList = contactAttachmentList;
    }
}