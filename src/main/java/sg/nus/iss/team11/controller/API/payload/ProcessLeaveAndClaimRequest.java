package sg.nus.iss.team11.controller.API.payload;

public class ProcessLeaveAndClaimRequest {

	private int id;
	private String comment;
	
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
	public ProcessLeaveAndClaimRequest(int id, String comment) {
		super();
		this.id = id;
		this.comment = comment;
	}
	
	

}
