package sg.nus.iss.team11.controller.service;

import java.util.List;

import sg.nus.iss.team11.model.LeaveApplication;

public interface LeaveApplicationService {
	  List<LeaveApplication> findAllLeaveApplications();

	  LeaveApplication findLeaveApplication(Integer id);

	  LeaveApplication createLeaveApplication(LeaveApplication leaveApplication);

	  LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication);

	  void removeLeaveApplication(LeaveApplication leaveApplication);
}
