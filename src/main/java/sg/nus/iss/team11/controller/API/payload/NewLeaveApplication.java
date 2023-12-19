package sg.nus.iss.team11.controller.API.payload;

import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;

public class NewLeaveApplication {
	private String description;
	private LeaveApplicationTypeEnum leaveapplicationtype;
	private String fromDate;
	private String toDate;
	
	public NewLeaveApplication(String description, LeaveApplicationTypeEnum leaveapplicationtype,
			String fromDate, String toDate) {
		super();
		this.description = description;
		this.leaveapplicationtype = leaveapplicationtype;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LeaveApplicationTypeEnum getLeaveapplicationtype() {
		return leaveapplicationtype;
	}

	public void setLeaveapplicationtype(LeaveApplicationTypeEnum leaveapplicationtype) {
		this.leaveapplicationtype = leaveapplicationtype;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	
	
	
}
