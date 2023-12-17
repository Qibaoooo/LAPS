package sg.nus.iss.team11.controller.API;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sg.nus.iss.team11.controller.API.payload.NewClaimRequest;
import sg.nus.iss.team11.controller.service.CompensationClaimService;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.RoleService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;
import sg.nus.iss.team11.model.ApplicationStatusEnum;
import sg.nus.iss.team11.model.CompensationClaim;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.validator.LeaveDateValidator;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/staff")
public class APIStaffController {
	
	@Autowired
	LeaveApplicationService leaveApplicationService;
	
	@Autowired
	CompensationClaimService claimService;	

	@Autowired
	RoleService roleService;

	@Autowired
	UserService userService;

	/*
	 * API to get the necessary info to populate new leave page.
	 */
	@GetMapping(value = "leave/form_info")
	public ResponseEntity<String> newLeave(HttpSession session, HttpServletResponse response) {

		JSONArray leaveTypes = new JSONArray(Arrays.asList(LeaveApplicationTypeEnum.values()));

		return new ResponseEntity<>(leaveTypes.toString(), HttpStatus.OK);
	}

	@GetMapping(value = "leave/list")
	public ResponseEntity<String> leaveList(Authentication authentication, Principal principal) {
		
		LAPSUser user = userService.findUserByUsername(principal.getName());

		JSONArray leaveList = new JSONArray();

		leaveApplicationService.findLeaveApplicationsByUserId(user.getUserId()).forEach((l) -> {
			JSONObject leave = new JSONObject();
			leave.put("id", l.getId());
			leave.put("comment", l.getComment());
			leave.put("description", l.getDescription());
			leave.put("fromDate", l.getFromDate());
			leave.put("toDate", l.getToDate());
			leave.put("status", l.getStatus().toString());
			leave.put("type", l.getType());
			
			leaveList.put(leave);
		});
		
		return new ResponseEntity<>(leaveList.toString(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/claim/new")
	public ResponseEntity<String> createNewClaim(Principal principal, @RequestBody NewClaimRequest claimRequest) {
		
		LAPSUser user = userService.findUserByUsername(principal.getName());

		CompensationClaim claim = new CompensationClaim();
		claim.setDescription(claimRequest.getDescription());
		claim.setOvertimeTime(claimRequest.getOvertimeTime());
		claim.setOverTimeDate(LocalDate.parse(claimRequest.getOvertimeDate()));
		claim.setStatus(ApplicationStatusEnum.APPLIED);
		claim.setUser(user);
		
		CompensationClaim created = claimService.createCompensationClaim(claim);		
		
		return new ResponseEntity<String>("claim created: "+created.getId(), HttpStatus.OK);
	}

}