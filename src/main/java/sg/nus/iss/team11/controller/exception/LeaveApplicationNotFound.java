package sg.nus.iss.team11.controller.exception;

public class LeaveApplicationNotFound extends Exception {
	private static final long serialVersionUID = 1L;

	public LeaveApplicationNotFound() {
	}

	public LeaveApplicationNotFound(String msg) {
		super(msg);
	}
}
