package sg.nus.iss.team11.controller.API.payload;

import sg.nus.iss.team11.model.CompensationClaimTimeEnum;

public class NewClaimRequest {
	private String overtimeDate;
	private CompensationClaimTimeEnum overtimeTime;
	private String description;
	public NewClaimRequest(String overtimeDate, CompensationClaimTimeEnum overtimeTime, String description) {
		super();
		this.overtimeDate = overtimeDate;
		this.overtimeTime = overtimeTime;
		this.description = description;
	}
	public String getOvertimeDate() {
		return overtimeDate;
	}
	public void setOvertimeDate(String overtimeDate) {
		this.overtimeDate = overtimeDate;
	}
	public CompensationClaimTimeEnum getOvertimeTime() {
		return overtimeTime;
	}
	public void setOvertimeTime(CompensationClaimTimeEnum overtimeTime) {
		this.overtimeTime = overtimeTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
