package HRAts.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="sector")
public class Sector implements Serializable {

    @Id
    @GenericGenerator(name="sector_id_gen" , strategy="increment")
    @GeneratedValue(generator="sector_id_gen")
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Sector() {
    }

    public Sector(String name) {
        super();
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
