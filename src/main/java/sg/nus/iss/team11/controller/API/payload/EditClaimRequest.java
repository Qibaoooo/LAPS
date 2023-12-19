package sg.nus.iss.team11.controller.API.payload;

import sg.nus.iss.team11.model.ApplicationStatusEnum;
import sg.nus.iss.team11.model.CompensationClaimTimeEnum;

public class EditClaimRequest extends NewClaimRequest {

	private int id;
	private int userid;
	private String comment;
	private ApplicationStatusEnum status;
	
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ApplicationStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatusEnum status) {
		this.status = status;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	
}
