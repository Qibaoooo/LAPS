package sg.nus.iss.team11.controller.service;

import java.util.List;

import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;

public interface LeaveApplicationService {
	List<LeaveApplication> findAllLeaveApplications();

	LeaveApplication findLeaveApplicationById(Integer id);

	LeaveApplication createLeaveApplication(LeaveApplication leaveApplication);

	LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication);

	void removeLeaveApplication(LeaveApplication leaveApplication);

	List<LeaveApplication> findLeaveApplicationsByUserId(Integer userId);

	List<LeaveApplication> findLeaveApplicationsToProcess(Integer userId);
	
	List<LeaveApplication> findLeaveApplicationsApprovedByType(LeaveApplicationTypeEnum type);

	List<LeaveApplication> filterForYear(List<LeaveApplication> leaves, List<Integer> years);

	List<LeaveApplication> onlyBeforeToday(List<LeaveApplication> leaves);
	
	List<LeaveApplication> findLeaveApplicationByYearMonth(Integer year, Integer month);
}
