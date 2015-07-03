package HRAts.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="activity_type_lkp")
public class ActivityTypeLkp {

    @Id
    @GenericGenerator(name="activity_type_lkp_id_gen" , strategy="increment")
    @GeneratedValue(generator="activity_type_lkp_id_gen")
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column
    private String name;

    public ActivityTypeLkp() {}

    public ActivityTypeLkp(int id, String name) {
        this.id = id;
        this.name = name;
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
}
