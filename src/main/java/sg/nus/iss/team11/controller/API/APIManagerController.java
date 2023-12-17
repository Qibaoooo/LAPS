package sg.nus.iss.team11.controller.API;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.team11.controller.service.CompensationClaimService;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.ApplicationStatusEnum;
import sg.nus.iss.team11.model.CompensationClaim;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/manager")
public class APIManagerController {
	@Autowired
	UserService userService;

	@Autowired
	LeaveApplicationService leaveApplicationService;

	@Autowired
	CompensationClaimService compensationClaimService;

	@GetMapping(value = "/leave/list")
	public ResponseEntity<String> viewApplicationsForApproval(Authentication authentication, Principal principal) {

		// Need to add session-related codes, to retrieve subordinates
		LAPSUser currentManager = userService.findUserByUsername(principal.getName());
		List<LAPSUser> subordinates = userService.findSubordinates(currentManager.getUserId());

		JSONArray leaveList = new JSONArray();

		for (LAPSUser u : subordinates) {
			JSONArray userLeave = new JSONArray();
			List<LeaveApplication> userLAList = leaveApplicationService.findLeaveApplicationsToProcess(u.getUserId());
			if (userLAList.isEmpty()) {
				continue;
			}
			for (LeaveApplication l : userLAList) {
				JSONObject leave = new JSONObject();
				leave.put("id", l.getId());
				leave.put("username", l.getUser().getUsername());
				leave.put("comment", l.getComment());
				leave.put("description", l.getDescription());
				leave.put("fromDate", l.getFromDate());
				leave.put("toDate", l.getToDate());
				leave.put("status", l.getStatus().toString());
				leave.put("type", l.getType());

				userLeave.put(leave);
			}
			leaveList.put(userLeave);
		}

		return new ResponseEntity<>(leaveList.toString(), HttpStatus.OK);
	}

	@GetMapping(value = "/leave/history")
	public ResponseEntity<String> viewApplicationsHistory(Authentication authentication, Principal principal) {

		// Need to add session-related codes, to retrieve subordinates
		LAPSUser currentManager = userService.findUserByUsername(principal.getName());
		List<LAPSUser> subordinates = userService.findSubordinates(currentManager.getUserId());

		JSONArray leaveList = new JSONArray();

		for (LAPSUser u : subordinates) {
			JSONArray userLeave = new JSONArray();
			List<LeaveApplication> userLAList = leaveApplicationService.findLeaveApplicationsByUserId(u.getUserId());
			if (userLAList.isEmpty()) {
				continue;
			}
			for (LeaveApplication l : userLAList) {
				JSONObject leave = new JSONObject();
				leave.put("id", l.getId());
				leave.put("username", l.getUser().getUsername());
				leave.put("comment", l.getComment());
				leave.put("description", l.getDescription());
				leave.put("fromDate", l.getFromDate());
				leave.put("toDate", l.getToDate());
				leave.put("status", l.getStatus().toString());
				leave.put("type", l.getType());

				userLeave.put(leave);
			}
			leaveList.put(userLeave);
		}

		return new ResponseEntity<>(leaveList.toString(), HttpStatus.OK);
	}

	@GetMapping(value = "/claim/list")
	public ResponseEntity<String> viewClaimsForApproval(Authentication authentication, Principal principal) {

		// Need to add session-related codes, to retrieve subordinates
		LAPSUser currentManager = userService.findUserByUsername(principal.getName());
		List<LAPSUser> subordinates = userService.findSubordinates(currentManager.getUserId());

		JSONArray claimList = new JSONArray();

		for (LAPSUser u : subordinates) {
			JSONArray userClaim = new JSONArray();
			List<CompensationClaim> userClaimList = compensationClaimService
					.findCompensationClaimsByUserId(u.getUserId());
			if (userClaimList.isEmpty()) {
				continue;
			}
			for (CompensationClaim c : userClaimList) {
				if ((c.getStatus() == ApplicationStatusEnum.APPLIED)
						|| (c.getStatus() == ApplicationStatusEnum.UPDATED)) {
					
					JSONObject leave = new JSONObject();
					leave.put("id", c.getId());
					leave.put("username", c.getUser().getUsername());
					leave.put("comment", c.getComment());
					leave.put("description", c.getDescription());
					leave.put("status", c.getStatus().toString());
					leave.put("time", c.getOvertimeTime().toString());
					leave.put("date", c.getOverTimeDate());

					userClaim.put(leave);
				}
			}
			
			if (!userClaim.isEmpty()) {
				claimList.put(userClaim);				
			}
		}

		return new ResponseEntity<>(claimList.toString(), HttpStatus.OK);
	}

	@PostMapping(value = "/claim/approve")
	public ResponseEntity<String> approveClaim(Principal principal, Authentication authentication,
			@RequestBody int id) {

		if (authentication.getAuthorities().toString().contains("ROLE_manager")) {
			// TODO: further improve check to make sure the staff belongs to this manager

			CompensationClaim claim = compensationClaimService.findCompensationClaimById(id);
			claim.setStatus(ApplicationStatusEnum.APPROVED);
			compensationClaimService.updateCompensationClaim(claim);

			return new ResponseEntity<>("approved claim " + id, HttpStatus.OK);

		}

		return new ResponseEntity<>("You are not a manager", HttpStatus.UNAUTHORIZED);
	}

	@PostMapping(value = "/claim/reject")
	public ResponseEntity<String> rejectClaim(Principal principal, Authentication authentication, @RequestBody int id) {

		if (authentication.getAuthorities().toString().contains("ROLE_manager")) {
			// TODO: further improve check to make sure the staff belongs to this manager

			CompensationClaim claim = compensationClaimService.findCompensationClaimById(id);
			claim.setStatus(ApplicationStatusEnum.REJECTED);
			compensationClaimService.updateCompensationClaim(claim);

			return new ResponseEntity<>("rejected claim " + id, HttpStatus.OK);
		}

		return new ResponseEntity<>("You are not a manager", HttpStatus.UNAUTHORIZED);
	}

}
