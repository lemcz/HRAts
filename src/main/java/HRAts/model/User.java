package HRAts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private boolean enabled;
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

    @OneToMany(mappedBy = "manager", targetEntity = Department.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Department> departmentList;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("attachment-user")
    private List<Attachment> attachmentList;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<User> contactList;

    @ManyToOne
    private User owner;

    public User () {}

    public User(String email, String name, String middleName, String lastName, String phoneNumber,
                String note, boolean enabled, String password, Date dateEntered, Date dateModified, Role role,
                CandidateInformation candidateInformation, List<VacancyUser> vacancyUserCandidateList,
                List<VacancyUser> vacancyUserOwnerList, List<Department> departmentList, List<Attachment> attachmentList, User owner, List<User> contactList ) {
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
        this.attachmentList = attachmentList;
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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
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

    @JsonIgnore
    public List<Department> getDepartmentList() {
        return departmentList;
    }

    @JsonProperty
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

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}