package HRAts.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skill_category")
public class SkillCategory implements Serializable{

    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "skillCategory", targetEntity = Skill.class, fetch=FetchType.EAGER)
    private List<Skill> skills = new ArrayList<>();

    public SkillCategory(){}

    public SkillCategory(String name, List<Skill> skills){
        this.name = name;
        this.skills = skills;
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

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
