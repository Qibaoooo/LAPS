package sg.nus.iss.team11.controller.API.payload;

public class NewLeaveApplication {
	private String description;
	private String leaveapplicationtype;
	private String fromDate;
	private String toDate;
	
	public NewLeaveApplication(String description, String type,
			String fromDate, String toDate) {
		super();
		this.description = description;
		this.leaveapplicationtype = type;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getLeaveapplicationtype() {
		return leaveapplicationtype;
	}

	public void setLeaveapplicationtype(String leaveapplicationtype) {
		this.leaveapplicationtype = leaveapplicationtype;
	}
	
	
	
	
}
