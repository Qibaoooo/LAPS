package sg.nus.iss.team11.controller.API;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.nus.iss.team11.controller.API.payload.EditClaimRequest;
import sg.nus.iss.team11.controller.API.payload.GetManagerClaimsRequest;
import sg.nus.iss.team11.controller.API.payload.ProcessLeaveAndClaimRequest;
import sg.nus.iss.team11.controller.service.CompensationClaimService;
import sg.nus.iss.team11.controller.service.HolidayService;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;
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

			userLAList = leaveApplicationService.filterForYear(userLAList,
					Arrays.asList(LocalDate.now().getYear(), LocalDate.now().plusYears(1).getYear()));

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

			userLAList = leaveApplicationService.onlyBeforeToday(userLAList);

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
	public ResponseEntity<String> checkEntitlementLeft(Principal principal, Authentication authentication,
			@RequestParam int id) {
		LeaveApplication appli = leaveApplicationService.findLeaveApplicationById(id);
		LAPSUser applier = appli.getUser();
		String type = appli.getType().toString();
		LeaveApplicationTypeEnum enumType = LeaveApplicationTypeEnum.valueOf(type);
		List<LeaveApplication> typeAppli = leaveApplicationService.findLeaveApplicationsApprovedByType(enumType);
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
		if (used + holidayService.getEntitlement(appli) < entitle) {
			res.put("result", "true");
		} else {
			res.put("result", "false");
		}
		res.put("left", entitle - used);
		return new ResponseEntity<>(res.toString(), HttpStatus.OK);
	}

	@PostMapping(value = "/leave/reject")
	public ResponseEntity<String> rejectLeave(Principal principal, Authentication authentication,
			@RequestBody ProcessLeaveAndClaimRequest processLeaveRequest) {

		if (authentication.getAuthorities().toString().contains("ROLE_manager")) {
			// TODO: further improve check to make sure the staff belongs to this manager
			LeaveApplication leave = leaveApplicationService.findLeaveApplicationById(processLeaveRequest.getId());
			LAPSUser applier = leave.getUser();
			LAPSUser currentManager = userService.findUserByUsername(principal.getName());
			List<LAPSUser> subordinates = userService.findSubordinates(currentManager.getUserId());
			if (subordinates.contains(applier)) {
				leave.setStatus(ApplicationStatusEnum.REJECTED);
				leave.setComment(processLeaveRequest.getComment());
				leaveApplicationService.updateLeaveApplication(leave);

				return new ResponseEntity<>("rejected leave " + processLeaveRequest.getId(), HttpStatus.OK);
			}
			return new ResponseEntity<>("You are not the manager of this staff", HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>("You are not a manager", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping(value = "/claims")
	public ResponseEntity<String> getClaims(GetManagerClaimsRequest request) {

		// Need to add session-related codes, to retrieve subordinates
		LAPSUser currentManager = userService.findUser(request.getManagerId());
		List<LAPSUser> subordinates;
		try {
			subordinates = userService.findSubordinates(currentManager.getUserId());
		} catch (Exception e) {
			return new ResponseEntity<>("failed to get subordinates under managerid " + request.getManagerId(),
					HttpStatus.BAD_REQUEST);
		}

		JSONArray claimList = new JSONArray();

		for (LAPSUser u : subordinates) {
			JSONArray userClaim = new JSONArray();
			List<CompensationClaim> userClaimList;

			if (request.getPendingClaimsOnly()) {
				userClaimList = compensationClaimService.findCompensationClaimsToProcess(u.getUserId());
			} else {
				userClaimList = compensationClaimService.findCompensationClaimsByUserId(u.getUserId());
			}

			if (userClaimList.isEmpty()) {
				continue;
			}

			if (!request.getPendingClaimsOnly()) {
				// if not getPendingClaimsOnly, it's requesting history.
				// so we only return history, not anything in future
				userClaimList = compensationClaimService.onlyBeforeToday(userClaimList);
			} else {
				// else, it's requesting claims to be processed.
				// we return this and next year data
				userClaimList = compensationClaimService.filterForYear(userClaimList,
						Arrays.asList(LocalDate.now().getYear(), LocalDate.now().plusYears(1).getYear()));
			}

			for (CompensationClaim c : userClaimList) {
				userClaim.put(c.toJsonObject());
			}

			if (!userClaim.isEmpty()) {
				claimList.put(userClaim);
			}
		}

		return new ResponseEntity<>(claimList.toString(), HttpStatus.OK);
	}

	@PutMapping(value = "/claims")
	public ResponseEntity<String> editClaim(Principal principal, @RequestBody EditClaimRequest editClaimRequest) {

		// user is the logged-in manager.
		LAPSUser user = userService.findUserByUsername(principal.getName());

		LAPSUser claimOwner = userService.findUser(editClaimRequest.getUserid());

		String newStatus = editClaimRequest.getStatus().toString();

		if (Arrays.asList("APPROVED", "REJECTED").contains(newStatus)) {
			if (claimOwner.getManagerId() != user.getUserId()) {
				return new ResponseEntity<String>("You are not allowed to approve/reject this claim.",
						HttpStatus.BAD_REQUEST);
			}
		}

		CompensationClaim claim = new CompensationClaim();

		claim.setDescription(editClaimRequest.getDescription());
		claim.setOvertimeTime(editClaimRequest.getOvertimeTime());
		claim.setOverTimeDate(LocalDate.parse(editClaimRequest.getOvertimeDate()));
		claim.setComment(editClaimRequest.getComment());
		claim.setStatus(editClaimRequest.getStatus());
		claim.setId(editClaimRequest.getId());
		claim.setUser(user);

		compensationClaimService.updateCompensationClaim(claim);

		if (newStatus.equals("APPROVED")) {
			incrementCompLeave(claimOwner, claim.getOvertimeTime());
		}

		return new ResponseEntity<String>("claim updated: " + editClaimRequest.getId(), HttpStatus.OK);
	}

	private void incrementCompLeave(LAPSUser user, CompensationClaimTimeEnum time) {
		userService.incrementCompensationLeaveBy((time == CompensationClaimTimeEnum.WHOLEDAY) ? 1 : 0.5,
				user.getUserId());
	}
}
