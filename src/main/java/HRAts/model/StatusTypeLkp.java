package HRAts.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="status_type_lkp")
public class StatusTypeLkp implements Serializable{

    @Id
    @GenericGenerator(name="status_type_lkp_id_gen" , strategy="increment")
    @GeneratedValue(generator="status_type_lkp_id_gen")
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column
    private String name;

    public StatusTypeLkp(){}

    public StatusTypeLkp(int id, String name) {
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

    public void setId(int id) {
        this.id = id;
    }
}
