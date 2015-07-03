package HRAts.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="contract_type_lkp")
public class ContractTypeLkp {

    @Id
    @GenericGenerator(name="contract_type_lkp_id_gen" , strategy="increment")
    @GeneratedValue(generator="contract_type_lkp_id_gen")
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public ContractTypeLkp() {}

    public ContractTypeLkp(int id, String name) {
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
