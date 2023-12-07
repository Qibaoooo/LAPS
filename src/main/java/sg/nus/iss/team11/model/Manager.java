package sg.nus.iss.team11.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Manager")
public class Manager extends ProfessionalEmployee {

	public Manager() {
		super();
	}

	public Manager(String username, String password, String leaveApplication) {
		super(username, password, leaveApplication);
		// TODO Auto-generated constructor stub
	}

	
}
