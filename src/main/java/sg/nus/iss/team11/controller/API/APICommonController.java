package sg.nus.iss.team11.controller.API;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.iss.team11.controller.service.HolidayService;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/common")
public class APICommonController {
	@Autowired
	private UserService userService;
	@Autowired
	private LeaveApplicationService leaveApplicationService;
	@Autowired
	private HolidayService holidayService;

	@GetMapping(value = "user-details")
	public ResponseEntity<?> userDetails(Principal principal) {
		LAPSUser user = userService.findUserByUsername(principal.getName());
		
		List<LeaveApplication> annualAppli = leaveApplicationService.findLeaveApplicationsApprovedByType(LeaveApplicationTypeEnum.AnnualLeave);
		List<LeaveApplication> compensationAppli = leaveApplicationService
				.findLeaveApplicationsApprovedByType(LeaveApplicationTypeEnum.CompensationLeave);
		List<LeaveApplication> medicalAppli = leaveApplicationService
				.findLeaveApplicationsApprovedByType(LeaveApplicationTypeEnum.MedicalLeave);
		
		// we send both current year and next year info to frond end.
		int[] aUsed = findUsedDays(annualAppli, user);
		int[] cUsed = findUsedDays(compensationAppli, user);
		int[] mUsed = findUsedDays(medicalAppli, user);
		
		JSONObject userJson = new JSONObject();
		userJson.put("annualLeaveEntitlement", user.getAnnualLeaveEntitlement());
		userJson.put("compensationLeaveEntitlement", user.getCompensationLeaveEntitlement());
		userJson.put("medicalLeaveEntitlement", user.getMedicalLeaveEntitlement());

		userJson.put("annualLeaveUsed", aUsed[0]);
		userJson.put("compensationLeaveUsed", cUsed[0]);
		userJson.put("medicalLeaveUsed", mUsed[0]);
		userJson.put("annualLeaveLeft", user.getAnnualLeaveEntitlement() - aUsed[0]);
		userJson.put("compensationLeaveLeft", user.getCompensationLeaveEntitlement() - cUsed[0]);
		userJson.put("medicalLeaveLeft", user.getMedicalLeaveEntitlement() - mUsed[0]);
		
		userJson.put("annualLeaveUsedNextYear", aUsed[1]);
		userJson.put("compensationLeaveUsedNextYear", cUsed[1]);
		userJson.put("medicalLeaveUsedNextYear", mUsed[1]);
		userJson.put("annualLeaveLeftNextYear", user.getAnnualLeaveEntitlement() - aUsed[1]);
		userJson.put("compensationLeaveLeftNextYear", user.getCompensationLeaveEntitlement() - cUsed[1]);
		userJson.put("medicalLeaveLeftNextYear", user.getMedicalLeaveEntitlement() - mUsed[1]);
		
		if (user.getRole().getRoleId().equals("staff") && user.getManagerId() > 0) {
			String manager = userService.findUser(user.getManagerId()).getUsername();
			userJson.put("manager", manager);
		} else {
			userJson.put("manager", "No Manager");
		}
		userJson.put("role", user.getRole().getName());
		userJson.put("username", user.getUsername().toUpperCase());

		return new ResponseEntity<>(userJson.toString(), HttpStatus.OK);
	}
	
	private int[] findUsedDays(List<LeaveApplication> applications, LAPSUser user) {
		int[] result = new int[2];
		List<Integer> used = new ArrayList<Integer>();
		List<Integer> usedNextYear = new ArrayList<Integer>();
		
		applications.forEach(a->{
			if (a.getUser().getUserId() != user.getUserId()) {
				return;
			}
			
			if (a.getFromDate().getYear() == LocalDate.now().getYear()) {
				used.add(holidayService.getEntitlement(a));
			}
			if (a.getFromDate().getYear() == LocalDate.now().plusYears(1).getYear()) {
				usedNextYear.add(holidayService.getEntitlement(a));
			}
		});
		
		result[0] = getSum(used);
		result[1] = getSum(usedNextYear);
		
		return result;
	}
	
	private int getSum(List<Integer> list) {
		return list.stream().reduce(0, (a, b) -> a + b);
	}
}
