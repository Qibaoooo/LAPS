package sg.nus.iss.team11.controller.API.payload;

public class GetManagerClaimsRequest {
	private boolean pendingClaimsOnly;
	private int managerId;
	public boolean getPendingClaimsOnly() {
		return pendingClaimsOnly;
	}
	public void setPendingClaimsOnly(boolean pendingClaimsOnly) {
		this.pendingClaimsOnly = pendingClaimsOnly;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public GetManagerClaimsRequest(boolean pendingClaimsOnly, int managerId) {
		super();
		this.pendingClaimsOnly = pendingClaimsOnly;
		this.managerId = managerId;
	}
}
