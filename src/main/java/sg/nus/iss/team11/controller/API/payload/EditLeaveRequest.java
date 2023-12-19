package sg.nus.iss.team11.controller.API.payload;

import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;

public class EditLeaveRequest extends NewLeaveApplication{
	private int id;
	
	public EditLeaveRequest(String description, LeaveApplicationTypeEnum leaveapplicationtype,
			String fromDate, String toDate) {
		super(description, leaveapplicationtype, fromDate,  toDate);
		
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
