package HRAts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user_account")
public class User implements Serializable{

    @Id
    @GenericGenerator(name="user_id_gen" , strategy="increment")
    @GeneratedValue(generator="user_id_gen")
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(unique = true)
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

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

    @OneToOne(optional = true, cascade = CascadeType.ALL, targetEntity=CandidateInformation.class)
    private CandidateInformation candidateInformation;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER)
    private List<VacancyUser> vacancyUserCandidateList;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<VacancyUser> vacancyUserOwnerList;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Department> departmentList;

    @ManyToOne
    private User owner;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<User> contactList;

    public User () {}

    public User(String email, String name, String middleName, String lastName, String phoneNumber,
                String note, String enabled, String password, Role role,
                CandidateInformation candidateInformation, List<VacancyUser> vacancyUserCandidateList,
                List<VacancyUser> vacancyUserOwnerList, List<Department> departmentList, User owner, List<User> contactList) {
        this.email = email;
        this.name = name;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.note = note;
        this.enabled = enabled;
        this.password = password;
        this.role = role;
        this.candidateInformation = candidateInformation;
        this.vacancyUserCandidateList = vacancyUserCandidateList;
        this.vacancyUserOwnerList = vacancyUserOwnerList;
        this.departmentList = departmentList;
        this.owner = owner;
        this.contactList = contactList;
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
}