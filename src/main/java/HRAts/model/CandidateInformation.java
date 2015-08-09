package HRAts.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="candidate_information")
public class CandidateInformation implements Serializable{

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue
    private int id;
    @Column
    private String address;
    private String city;
    private String country;
    private String zipCode;
    private String financialReqNetto;
    private String financialReqBrutto;
    private Date datePrevJobTermination;
    private Date startDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "contract_type_id", nullable = false, updatable = false, unique = false)
    private ContractTypeLkp contractType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "candidate_skill", joinColumns = @JoinColumn(name="candidate_information_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skillList;

/*    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User userId;*/

    public CandidateInformation() {}

    public CandidateInformation(String address, String city, String country, String zipCode, String financialReqNetto,
                                String financialReqBrutto, Date datePrevJobTermination, Date startDate, ContractTypeLkp contractType, List<Skill> skillList/*, User userId*/) {
        super();
        this.address = address;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.financialReqNetto = financialReqNetto;
        this.financialReqBrutto = financialReqBrutto;
        this.datePrevJobTermination = datePrevJobTermination;
        this.startDate = startDate;
        this.contractType = contractType;
        this.skillList = skillList;
/*
        this.userId = userId;
*/
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDatePrevJobTermination() {
        return datePrevJobTermination;
    }

    public void setDatePrevJobTermination(Date datePrevJobTermination) {
        this.datePrevJobTermination = datePrevJobTermination;
    }

    public String getFinancialReqBrutto() {
        return financialReqBrutto;
    }

    public void setFinancialReqBrutto(String financialReqBrutto) {
        this.financialReqBrutto = financialReqBrutto;
    }

    public String getFinancialReqNetto() {
        return financialReqNetto;
    }

    public void setFinancialReqNetto(String financialReqNetto) {
        this.financialReqNetto = financialReqNetto;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZipcode() {
        return zipCode;
    }

    public void setZipcode(String zipCode) {
        this.zipCode = zipCode;
    }

    public ContractTypeLkp getContractType() {
        return contractType;
    }

    public void setContractType(ContractTypeLkp contractType) {
        this.contractType = contractType;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

/*    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }*/
}
