package sg.nus.iss.team11.controller.service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;
import sg.nus.iss.team11.repository.LeaveApplicationRepository;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

	@Autowired
	LeaveApplicationRepository leaveRepo;

	@Override
	public List<LeaveApplication> findAllLeaveApplications() {
		return leaveRepo.findAll();
	}

	@Override
	public LeaveApplication findLeaveApplicationById(Integer id) {
		return leaveRepo.findById(id).orElse(null);
	}

	@Override
	public LeaveApplication createLeaveApplication(LeaveApplication leaveApplication) {
		return leaveRepo.saveAndFlush(leaveApplication);
	}

	@Override
	public LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication) {
		return leaveRepo.saveAndFlush(leaveApplication);
	}

	@Override
	public void removeLeaveApplication(LeaveApplication leaveApplication) {
		leaveRepo.delete(leaveApplication);
	}

	@Override
	public List<LeaveApplication> findLeaveApplicationsByUserId(Integer userId) {
		return leaveRepo.findLeaveApplicationsByUserId(userId);
	}

	@Override
	public List<LeaveApplication> findLeaveApplicationsToProcess(Integer userId) {
		return leaveRepo.findLeaveApplicationsToProcess(userId);
	}
	
	@Override
	public List<LeaveApplication> findLeaveApplicationsApprovedByType(LeaveApplicationTypeEnum type){
		return leaveRepo.findLeaveApplicationsApprovedByType(type);
	}
	
	@Override
	public List<LeaveApplication> filterForYear(List<LeaveApplication> leaves, List<Integer> years) {
		leaves.removeIf(l->{
			return !years.contains(l.getFromDate().getYear());
		});
		return leaves;
	}
	
	@Override
	public List<LeaveApplication> onlyBeforeToday(List<LeaveApplication> leaves) {
		leaves.removeIf(l->{
			return l.getFromDate().isAfter(LocalDate.now());
		});
		return leaves;
	}

}
