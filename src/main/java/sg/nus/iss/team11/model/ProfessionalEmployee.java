package sg.nus.iss.team11.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class ProfessionalEmployee extends User {
//	TODO: change this to a real leaveApplication class
	private String leaveApplication;

	public ProfessionalEmployee(String username, String password, String leaveApplication) {
		super(username, password);
		this.leaveApplication = leaveApplication;
	}
	
	public ProfessionalEmployee() {
		super();
	}

	public String getLeaveApplication() {
		return leaveApplication;
	}

	public void setLeaveApplication(String leaveApplication) {
		this.leaveApplication = leaveApplication;
	}
}
