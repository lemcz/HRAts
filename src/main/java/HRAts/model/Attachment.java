package HRAts.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Attachment {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue
    private int id;

    @Column
    private String name;
    @Column
    private String filePath;
    @Column
    private Date dateEntered;
    @Column
    private Date dateModified;

    //TODO all the junctions

    public Attachment(){}

    public Attachment(String name, Date dateEntered, Date dateModified){
        super();
        this.name = name;
        this.dateEntered = dateEntered;
        this.dateModified = dateModified;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
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
