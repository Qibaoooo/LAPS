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
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sg.nus.iss.team11.controller.API.payload.EditClaimRequest;
import sg.nus.iss.team11.controller.API.payload.EditLeaveRequest;
import sg.nus.iss.team11.controller.API.payload.NewClaimRequest;
import sg.nus.iss.team11.controller.API.payload.NewLeaveApplication;
import sg.nus.iss.team11.controller.service.CompensationClaimService;
import sg.nus.iss.team11.controller.service.HolidayService;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.RoleService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.ApplicationStatusEnum;
import sg.nus.iss.team11.model.CompensationClaim;
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

	@Autowired
	HolidayService holidayservice;

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

	@PutMapping(value = "leave/cancel/{id}")
	public ResponseEntity<String> cancelLeave(Authentication authentication, @PathVariable("id") int id) {
		LeaveApplication la = leaveApplicationService.findLeaveApplicationById(id);
		la.setStatus(ApplicationStatusEnum.CANCELLED);
		leaveApplicationService.updateLeaveApplication(la);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PutMapping(value = "leaves")
	public ResponseEntity<String> cancelLeave(Principal principal, @RequestParam String leaveId, @RequestParam String methodRequest) {
		LeaveApplication la = leaveApplicationService.findLeaveApplicationById(Integer.parseInt(leaveId));
		if (methodRequest.equalsIgnoreCase("cancell") ) {
			la.setStatus(ApplicationStatusEnum.CANCELLED);
			leaveApplicationService.updateLeaveApplication(la);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		else if (methodRequest.equalsIgnoreCase("delete")) {
		// If payload.request == Delete
		la.setStatus(ApplicationStatusEnum.DELETED);
		leaveApplicationService.updateLeaveApplication(la);
		return new ResponseEntity<>(HttpStatus.OK);
		}
		
	
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "/leave/new")
	public ResponseEntity<String> newLeave(Principal principal, @RequestBody NewLeaveApplication newleaveapplication) {
		LAPSUser user = userService.findUserByUsername(principal.getName());

		// Populating Leave application
		LeaveApplication la = new LeaveApplication();
		la.setDescription(newleaveapplication.getDescription());
		la.setFromDate(LocalDate.parse(newleaveapplication.getFromDate()));
		la.setToDate(LocalDate.parse(newleaveapplication.getToDate()));
		la.setType(LeaveApplicationTypeEnum.valueOf(newleaveapplication.getLeaveapplicationtype()));
		la.setStatus(ApplicationStatusEnum.APPLIED);
		la.setUser(user);
		
		Optional<String> leaveValidation = checkLeave(la,user);
		if (!leaveValidation.isEmpty()) {
			return new ResponseEntity<String>(leaveValidation.get(), HttpStatus.BAD_REQUEST);
		}

		leaveApplicationService.createLeaveApplication(la);
		return new ResponseEntity<String>("Leave created successfully!", HttpStatus.OK);
	}

	@PutMapping(value = "/leave/edit")
	public ResponseEntity<String> editLeave(Principal principal, @RequestBody EditLeaveRequest editLeaveRequest) {

		LAPSUser user = userService.findUserByUsername(principal.getName());

		// Populating Leave application
		LeaveApplication la = new LeaveApplication();
		la.setDescription(editLeaveRequest.getDescription());
		la.setFromDate(LocalDate.parse(editLeaveRequest.getFromDate()));
		la.setToDate(LocalDate.parse(editLeaveRequest.getToDate()));
		la.setType(LeaveApplicationTypeEnum.valueOf(editLeaveRequest.getLeaveapplicationtype()));
		la.setStatus(ApplicationStatusEnum.UPDATED);
		la.setId(editLeaveRequest.getId());
		la.setUser(user);
		List<LeaveApplication> listOfLA = leaveApplicationService.findLeaveApplicationsByUserId(user.getUserId());
		
		
		Optional<String> leaveValidation = checkLeave(la,user);
		if (!leaveValidation.isEmpty()) {
			return new ResponseEntity<String>(leaveValidation.get(), HttpStatus.BAD_REQUEST);
		}
		leaveApplicationService.updateLeaveApplication(la);
		return new ResponseEntity<String>("leave updated: " + editLeaveRequest.getId(), HttpStatus.OK);
	}

	@GetMapping(value = "/claims")
	public ResponseEntity<String> getClaimList(Principal principal) {
		LAPSUser user = userService.findUserByUsername(principal.getName());

		JSONArray claimList = new JSONArray();

		claimService.findCompensationClaimsByUserId(user.getUserId()).forEach((c) -> {
			claimList.put(c.toJsonObject());
		});

		return new ResponseEntity<>(claimList.toString(), HttpStatus.OK);
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

		CompensationClaim claim = new CompensationClaim();

		claim.setDescription(editClaimRequest.getDescription());
		claim.setOvertimeTime(editClaimRequest.getOvertimeTime());
		claim.setOverTimeDate(LocalDate.parse(editClaimRequest.getOvertimeDate()));
		claim.setComment(editClaimRequest.getComment());
		claim.setStatus(editClaimRequest.getStatus());
		claim.setId(editClaimRequest.getId());
		claim.setUser(user);

		claimService.updateCompensationClaim(claim);

		return new ResponseEntity<String>("claim updated: " + editClaimRequest.getId(), HttpStatus.OK);
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
	
	private Optional<String> checkLeave(LeaveApplication la, LAPSUser user){
		List<LeaveApplication> listOfLA = leaveApplicationService.findLeaveApplicationsByUserId(user.getUserId());
		
		// Checking for overlapping leave
		for (LeaveApplication currentLa : listOfLA) {
			if (currentLa.getId() != la.getId() && la.isOverlapping(currentLa) && currentLa.getStatus() != ApplicationStatusEnum.DELETED) {
				return Optional.of("Overlapping Leave Request, please try again with another set of dates.");
			}
		}

		// If leave duration is less than 14 days, check for holidays and weekends
		int leaveDays = la.countLeaveDays();
		int numberOfHolidays = 0;
		int weekends = 0;
		if (leaveDays < 14) {
			// Using repository to find the number of holidays
			numberOfHolidays = holidayservice.getHolidayCount(la.getFromDate(), la.getToDate());
			weekends = la.countWeekend();
		}
		leaveDays -= (numberOfHolidays + weekends);

		// Checking if the user has enough leave
		double leaveEntitlement = 0;
		switch (la.getType()) {
		case MedicalLeave:
			leaveEntitlement = user.getMedicalLeaveEntitlement();
			break;
		case AnnualLeave:
			leaveEntitlement = user.getAnnualLeaveEntitlement();
			break;
		case CompensationLeave:
			leaveEntitlement = user.getCompensationLeaveEntitlement();
			break;
		}
		System.out.println(leaveEntitlement);
		System.out.println(leaveDays);
		if (leaveDays > leaveEntitlement) {
			return Optional.of("Not enough Leave Entitlement, your remaining leave for this type is "+ String.valueOf((int) leaveEntitlement));
		}
		
		
		// Checking if start date is before end date (First check done in front end)
		if (la.getFromDate().isAfter(la.getToDate())) {
			return Optional.of("Start Date is later than end date. Please try again.");
		}
				
		return Optional.empty();
	}
}
