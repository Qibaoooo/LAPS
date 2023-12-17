package sg.nus.iss.team11.controller.API.payload;

import sg.nus.iss.team11.model.CompensationClaimTimeEnum;

public class EditClaimRequest extends NewClaimRequest {

	private int id;
	
	public EditClaimRequest(String overtimeDate, CompensationClaimTimeEnum overtimeTime, String description, int id) {
		super(overtimeDate, overtimeTime, description);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
