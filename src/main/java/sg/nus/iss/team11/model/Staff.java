package sg.nus.iss.team11.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Staff")
public class Staff extends User {
	
//	TODO: how to implement self-referencing OneToOne mapping here?
	private int managerId;
	
	public Staff() {
		super();
	}

	public Staff(String username, String password, int managerId) {
		super(username, password);
		this.managerId = managerId;
	}
}
