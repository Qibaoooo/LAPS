package sg.nus.iss.team11.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {

	public Admin() {
		super();
	}
	
	public Admin(String username, String password) {
		super(username, password);
	}
		

}
