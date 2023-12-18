package sg.nus.iss.team11.controller.API;

import java.security.Principal;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import sg.nus.iss.team11.controller.API.payload.ProcessLeaveAndClaimRequest;
import sg.nus.iss.team11.controller.service.CompensationClaimService;
import sg.nus.iss.team11.controller.service.HolidayService;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.ApplicationStatusEnum;
import sg.nus.iss.team11.model.CompensationClaim;
import sg.nus.iss.team11.model.CompensationClaimTimeEnum;

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

	@Autowired
	HolidayService holidayService;

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
				userLeave.put(buildLeave(l));
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
				userLeave.put(buildLeave(l));
			}
			leaveList.put(userLeave);
		}

		return new ResponseEntity<>(leaveList.toString(), HttpStatus.OK);
	}

	private JSONObject buildLeave(LeaveApplication l) {
		JSONObject leave = new JSONObject();
		leave.put("id", l.getId());
		leave.put("username", l.getUser().getUsername());
		leave.put("comment", l.getComment());
		leave.put("description", l.getDescription());
		leave.put("fromDate", l.getFromDate());
		leave.put("toDate", l.getToDate());
		leave.put("status", l.getStatus().toString());
		leave.put("type", l.getType());
		return leave;
	}

	@PostMapping(value = "/leave/approve")
	public ResponseEntity<String> approveLeave(Principal principal, Authentication authentication,
			@RequestBody ProcessLeaveAndClaimRequest processLeaveRequest) {

		if (authentication.getAuthorities().toString().contains("ROLE_manager")) {
			// TODO: further improve check to make sure the staff belongs to this manager
			LeaveApplication leave = leaveApplicationService.findLeaveApplicationById(processLeaveRequest.getId());
			LAPSUser applier = leave.getUser();
			LAPSUser currentManager = userService.findUserByUsername(principal.getName());
			List<LAPSUser> subordinates = userService.findSubordinates(currentManager.getUserId());
			if (subordinates.contains(applier)) {
				leave.setStatus(ApplicationStatusEnum.APPROVED);
				leave.setComment(processLeaveRequest.getComment());
				leaveApplicationService.updateLeaveApplication(leave);
				return new ResponseEntity<>("approved leave " + processLeaveRequest.getId(), HttpStatus.OK);
			}
			return new ResponseEntity<>("You are not the manager of this staff", HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>("You are not a manager", HttpStatus.UNAUTHORIZED);
	}

	@PostMapping(value = "/leave/checkEntitle")
	public ResponseEntity<String> checkEntitlementLeft(Principal principal, Authentication authentication, @RequestParam int id) {
		LeaveApplication appli = leaveApplicationService.findLeaveApplicationById(id);
		LAPSUser applier = appli.getUser();
		String type = appli.getType().toString();
		List<LeaveApplication> typeAppli = leaveApplicationService.findLeaveApplicationsApprovedByType(type);
		double entitle = 0, used = 0;
		switch (type) {
		case "MedicalLeave":
			entitle = applier.getMedicalLeaveEntitlement();
			break;
		case "AnnualLeave":
			entitle = applier.getAnnualLeaveEntitlement();
			break;
		case "CompensationLeave":
			entitle = applier.getCompensationLeaveEntitlement();
			break;
		}
		for (LeaveApplication a : typeAppli) {
			used += holidayService.getEntitlement(a);
		}
		JSONObject res = new JSONObject();
		if(used+holidayService.getEntitlement(appli)<entitle) {
			res.put("result","true");
		}
		else {
			res.put("result","false");
		}
		res.put("left",entitle-used);
		return new ResponseEntity<>(res.toString(), HttpStatus.OK);
	}

	@GetMapping(value = "/claim/list")
	public ResponseEntity<String> getClaimsForApproval(Authentication authentication, Principal principal) {

		// Need to add session-related codes, to retrieve subordinates
		LAPSUser currentManager = userService.findUserByUsername(principal.getName());
		List<LAPSUser> subordinates = userService.findSubordinates(currentManager.getUserId());

		JSONArray claimList = new JSONArray();

		for (LAPSUser u : subordinates) {
			JSONArray userClaim = new JSONArray();
			List<CompensationClaim> userClaimList = compensationClaimService
					.findCompensationClaimsToProcess(u.getUserId());
			if (userClaimList.isEmpty()) {
				continue;
			}
			for (CompensationClaim c : userClaimList) {
				userClaim.put(buildClaimJson(c));

			}

			if (!userClaim.isEmpty()) {
				claimList.put(userClaim);
			}
		}

		return new ResponseEntity<>(claimList.toString(), HttpStatus.OK);
	}

	@GetMapping(value = "/claim/history")
	public ResponseEntity<String> getClaimsHistory(Authentication authentication, Principal principal) {

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
				userClaim.put(buildClaimJson(c));
			}

			if (!userClaim.isEmpty()) {
				claimList.put(userClaim);
			}
		}

		return new ResponseEntity<>(claimList.toString(), HttpStatus.OK);
	}

	private JSONObject buildClaimJson(CompensationClaim c) {
		JSONObject claim = new JSONObject();
		claim.put("id", c.getId());
		claim.put("username", c.getUser().getUsername());
		claim.put("comment", c.getComment());
		claim.put("description", c.getDescription());
		claim.put("status", c.getStatus().toString());
		claim.put("time", c.getOvertimeTime().toString());
		claim.put("date", c.getOverTimeDate());
		return claim;
	}

	@PostMapping(value = "/claim/approve")
	public ResponseEntity<String> approveClaim(Principal principal, Authentication authentication,
			@RequestBody ProcessLeaveAndClaimRequest processClaimRequest) {

		if (authentication.getAuthorities().toString().contains("ROLE_manager")) {
			// TODO: further improve check to make sure the staff belongs to this manager

			CompensationClaim claim = compensationClaimService.findCompensationClaimById(processClaimRequest.getId());
			claim.setStatus(ApplicationStatusEnum.APPROVED);
			claim.setComment(processClaimRequest.getComment());
			compensationClaimService.updateCompensationClaim(claim);

			// increment subordinate compleave entitlement
			userService.incrementCompensationLeaveBy(
					(claim.getOvertimeTime() == CompensationClaimTimeEnum.WHOLEDAY) ? 1 : 0.5,
					claim.getUser().getUserId());

			return new ResponseEntity<>("approved claim " + processClaimRequest.getId(), HttpStatus.OK);
		}

		return new ResponseEntity<>("You are not a manager", HttpStatus.UNAUTHORIZED);
	}

	@PostMapping(value = "/claim/reject")
	public ResponseEntity<String> rejectClaim(Principal principal, Authentication authentication,
			@RequestBody ProcessLeaveAndClaimRequest processClaimRequest) {

		if (authentication.getAuthorities().toString().contains("ROLE_manager")) {
			// TODO: further improve check to make sure the staff belongs to this manager

			CompensationClaim claim = compensationClaimService.findCompensationClaimById(processClaimRequest.getId());
			claim.setStatus(ApplicationStatusEnum.REJECTED);
			claim.setComment(processClaimRequest.getComment());
			compensationClaimService.updateCompensationClaim(claim);

			return new ResponseEntity<>("rejected claim " + processClaimRequest.getId(), HttpStatus.OK);
		}

		return new ResponseEntity<>("You are not a manager", HttpStatus.UNAUTHORIZED);
	}

}
