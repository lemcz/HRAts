package HRAts.model;

import javax.persistence.*;

@Entity
@Table(name = "roledb")
public class RoleDB {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue
    private int id;
    @Column(name = "name", nullable = false)
    private String name;

    public RoleDB(){}

    public RoleDB(String name){
        super();
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
