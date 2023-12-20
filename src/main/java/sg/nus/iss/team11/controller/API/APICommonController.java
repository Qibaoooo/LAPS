package sg.nus.iss.team11.controller.API;

import java.security.Principal;
import java.util.List;

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
		List<LeaveApplication> annualAppli = leaveApplicationService.findLeaveApplicationsApprovedByType("AnnualLeave");
		List<LeaveApplication> compensationAppli = leaveApplicationService
				.findLeaveApplicationsApprovedByType("CompensationLeave");
		List<LeaveApplication> medicalAppli = leaveApplicationService
				.findLeaveApplicationsApprovedByType("MedicalLeave");
		int aUsed = 0, cUsed = 0, mUsed = 0;
		for (LeaveApplication a : annualAppli) {
			aUsed += holidayService.getEntitlement(a);
		}
		for (LeaveApplication a : compensationAppli) {
			cUsed += holidayService.getEntitlement(a);
		}
		for (LeaveApplication a : medicalAppli) {
			mUsed += holidayService.getEntitlement(a);
		}

		JSONObject userJson = new JSONObject();
		userJson.put("annualLeaveEntitlement", user.getAnnualLeaveEntitlement());
		userJson.put("compensationLeaveEntitlement", user.getCompensationLeaveEntitlement());
		userJson.put("medicalLeaveEntitlement", user.getMedicalLeaveEntitlement());
		userJson.put("annualLeaveUsed", aUsed);
		userJson.put("compensationLeaveUsed", cUsed);
		userJson.put("medicalLeaveUsed", mUsed);
		userJson.put("annualLeaveLeft", user.getAnnualLeaveEntitlement() - aUsed);
		userJson.put("compensationLeaveLeft", user.getCompensationLeaveEntitlement() - cUsed);
		userJson.put("medicalLeaveLeft", user.getMedicalLeaveEntitlement() - mUsed);
		if (user.getRole().getRoleId().equals("staff")) {
			String manager = userService.findUser(user.getManagerId()).getUsername();
			userJson.put("manager", manager);
		}
		userJson.put("role", user.getRole().getName());
		userJson.put("username", user.getUsername().toUpperCase());

		return new ResponseEntity<>(userJson.toString(), HttpStatus.OK);
	}
}
