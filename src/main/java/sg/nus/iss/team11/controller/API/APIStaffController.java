package sg.nus.iss.team11.controller.API;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sg.nus.iss.team11.controller.API.payload.EditClaimRequest;
import sg.nus.iss.team11.controller.API.payload.NewClaimRequest;
import sg.nus.iss.team11.controller.service.CompensationClaimService;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.RoleService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;
import sg.nus.iss.team11.model.ApplicationStatusEnum;
import sg.nus.iss.team11.model.CompensationClaim;
import sg.nus.iss.team11.model.CompensationClaimTimeEnum;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;

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

	@GetMapping(value = "/claims")
	public ResponseEntity<String> getClaimList(Principal principal) {
		LAPSUser user = userService.findUserByUsername(principal.getName());

		JSONArray claimList = new JSONArray();

		claimService.findCompensationClaimsByUserId(user.getUserId()).forEach((c) -> {

			if (c.getStatus() == ApplicationStatusEnum.DELETED) {
				return;
			}

			JSONObject claim = new JSONObject();
			claim.put("id", c.getId());
			claim.put("userid", c.getUser().getUserId());
			claim.put("overtimeDate", c.getOverTimeDate().toString());
			claim.put("overtimeTime", c.getOvertimeTime().toString());
			claim.put("description", c.getDescription());
			claim.put("status", c.getStatus());
			claim.put("comment", c.getComment());
			claimList.put(claim);
		});

		return new ResponseEntity<>(claimList.toString(), HttpStatus.OK);
	}


	
	
	@PutMapping(value = "leave/cancel/{id}")
	public ResponseEntity<String> cancelLeave(Authentication authentication, @PathVariable("id") int id){
		LeaveApplication la = leaveApplicationService.findLeaveApplicationById(id);
		la.setStatus(ApplicationStatusEnum.CANCELLED);
		leaveApplicationService.updateLeaveApplication(la);
		return new ResponseEntity<>(HttpStatus.OK);
	}	
	
	@PutMapping(value = "/leave/edit")
	public ResponseEntity<String> editLeave(Principal principal, @RequestBody EditClaimRequest editLeaveRequest) {
		
		LAPSUser user = userService.findUserByUsername(principal.getName());
		
		LeaveApplication la = new LeaveApplication();
//		la.setDescription(editLeaveRequest.getDescription());
//		la.setFromDate(editLeaveRequest.get)
//		
//		
		
		
		
		return new ResponseEntity<String>("leave updated: " + editLeaveRequest.getId(), HttpStatus.OK);
	}

	@PostMapping(value = "/claims")
	public ResponseEntity<String> createNewClaim(Principal principal, @RequestBody NewClaimRequest claimRequest) {

		LAPSUser user = userService.findUserByUsername(principal.getName());

		CompensationClaim claim = new CompensationClaim();
		claim.setDescription(claimRequest.getDescription());
		claim.setOvertimeTime(claimRequest.getOvertimeTime());
		claim.setOverTimeDate(LocalDate.parse(claimRequest.getOvertimeDate()));
		claim.setStatus(ApplicationStatusEnum.APPLIED);
		claim.setUser(user);

		CompensationClaim created = claimService.createCompensationClaim(claim);

		return new ResponseEntity<String>("claim created: " + created.getId(), HttpStatus.OK);
	}

	@PutMapping(value = "/claims")
	public ResponseEntity<String> editClaim(Principal principal, @RequestBody EditClaimRequest editClaimRequest) {

		LAPSUser user = userService.findUser(editClaimRequest.getUserid());

		String newStatus = editClaimRequest.getStatus().toString();

		if (Arrays.asList("DELETED", "CANCELLED").contains(newStatus)) {
			if (!principal.getName().equals(user.getUsername())) {
				return new ResponseEntity<String>("You are not allowed to delete/cancel this claim.",
						HttpStatus.BAD_REQUEST);
			}
		}

		if (Arrays.asList("APPROVED", "REJECTED").contains(newStatus)) {
			LAPSUser manager = userService.findUser(user.getManagerId());
			if (!principal.getName().equals(manager.getUsername())) {
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

		claimService.updateCompensationClaim(claim);

		if (newStatus.equals("APPROVED")) {
			incrementCompLeave(user, claim.getOvertimeTime());
		}

		return new ResponseEntity<String>("claim updated: " + editClaimRequest.getId(), HttpStatus.OK);
	}

	private void incrementCompLeave(LAPSUser user, CompensationClaimTimeEnum time) {
		userService.incrementCompensationLeaveBy((time == CompensationClaimTimeEnum.WHOLEDAY) ? 1 : 0.5, user.getUserId());
	}

	@DeleteMapping(value = "/claims")
	public ResponseEntity<String> deleteClaim(Principal principal, @RequestBody EditClaimRequest editClaimRequest) {

		LAPSUser user = userService.findUserByUsername(principal.getName());

		List<CompensationClaim> userClaims = claimService.findCompensationClaimsByUserId(user.getUserId());

		Optional<CompensationClaim> claim = userClaims.stream().filter(c -> c.getId() == editClaimRequest.getId())
				.findFirst();

		if (claim == null) {
			return new ResponseEntity<String>("You are not allowed to delete this claim.", HttpStatus.BAD_REQUEST);
		} else {
//			claimService.removeCompensationClaim(claim.get());
			// instead of really deleting, we set the status as deleted
			CompensationClaim updated = claim.get();
			updated.setStatus(ApplicationStatusEnum.DELETED);
			claimService.updateCompensationClaim(updated);
			return new ResponseEntity<String>("claim deleted: " + editClaimRequest.getId(), HttpStatus.OK);
		}
	}
}
