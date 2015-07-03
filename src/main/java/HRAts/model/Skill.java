package HRAts.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Skill implements Serializable{

    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "skillList", fetch = FetchType.LAZY)
    private List<CandidateInformation> candidateInformationList;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private SkillCategory skillCategory;

    public Skill(){}

    public Skill(String name, List<CandidateInformation> candidateInformationList, SkillCategory skillCategory){
        this.name = name;
        this.candidateInformationList = candidateInformationList;
        this.skillCategory = skillCategory;
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

    public List<CandidateInformation> getCandidateInformationList() {
        return candidateInformationList;
    }

    public void setCandidateInformationList(List<CandidateInformation> candidateInformationList) {
        this.candidateInformationList = candidateInformationList;
    }

    public SkillCategory getSkillCategory() {
        return skillCategory;
    }

    public void setSkillCategory(SkillCategory skillCategory) {
        this.skillCategory = skillCategory;
    }
}
