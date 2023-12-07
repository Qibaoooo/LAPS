package sg.nus.iss.team11.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Manager")
public class Manager extends Staff {

	public Manager() {
		super();
	}

	public Manager(String username, String password, int managerId) {
		super(username, password, managerId);
	}
	
}
