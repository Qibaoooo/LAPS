package sg.nus.iss.team11.controller.service;

import java.util.List;

import sg.nus.iss.team11.model.LeaveApplication;

public interface LeaveApplicationService {
	  List<LeaveApplication> findAllLeaveApplications();

	  LeaveApplication findLeaveApplicationById(Integer id);
	  
	  List<LeaveApplication> findLeaveApplicationsByUserId(Integer userId); 

	  LeaveApplication createLeaveApplication(LeaveApplication leaveApplication);

	  LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication);

	  void removeLeaveApplication(LeaveApplication leaveApplication);
}
