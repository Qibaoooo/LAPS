package sg.nus.iss.team11.controller.API.payload;

import sg.nus.iss.team11.model.LAPSUser;

public class EditEmployee extends NewEmployee{
	private int id;
	
	public EditEmployee(String username, String password, String managerName, String roleName, int annualLeaveEntitlement,
			int medicalLeaveEntitlement, int compensationLeaveEntitlement, int id) {
		super(username, password, managerName, roleName, annualLeaveEntitlement,
				medicalLeaveEntitlement, compensationLeaveEntitlement);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
