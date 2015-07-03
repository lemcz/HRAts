package HRAts.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Candidate {

    @Id
    @GeneratedValue
    private int id;
	private String name;
	private String phoneNumber;
	private String email;
	
	public Candidate(){
		
	}
	
	public Candidate(String name, String phoneNumber, String email, int id) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    @Override
    public boolean equals(Object object) {
        if (object instanceof Candidate){
            Candidate candidate = (Candidate) object;
            return candidate.id == id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
