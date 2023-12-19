package sg.nus.iss.team11.controller.API.payload;


public class EditLeaveRequest extends NewLeaveApplication{
	private int id;
	
	public EditLeaveRequest(String description, String type,
			String fromDate, String toDate) {
		super(description, type, fromDate,  toDate);
		
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
