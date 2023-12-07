package sg.nus.iss.team11.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Staff")
public class Staff extends ProfessionalEmployee {
	
//	TODO: how to implement self-referencing OneToOne mapping here?
	private int managerId;

	public Staff() {
		super();
	}

	public Staff(String username, String password, String leaveApplication, int managerId) {
		super(username, password, leaveApplication);
		this.managerId = managerId;
	}	

}
