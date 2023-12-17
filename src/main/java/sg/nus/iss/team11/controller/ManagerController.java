package sg.nus.iss.team11.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.team11.controller.service.HolidayService;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.ApplicationStatusEnum;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.LAPSUser;

@Controller
@RequestMapping(value = "/v1/manager")
public class ManagerController {

	@Autowired
	public LeaveApplicationService leaveApplicationService;

	@Autowired
	public HolidayService holidayservice;

	@Autowired
	public UserService userService;

	// ------------------------------------------------------//
	// Show list of leave application
	// 1.Get Manager info form session(implement later)
	// (done)2.Find related staff, staff.managerId = this.id
	// SELECT s FROM User s WHERE s.managerId = id
	// The status should be APPLIED or UPDATED
	// retrieve data from DB
	// (done)3.Find related applications, staff.leaveApplications
	// 4.Show respective html page
	// ------------------------------------------------------//
	@RequestMapping(value = "/leave/view")
	public String viewApplicationsForApproval(HttpSession session, Model model) {

		// Need to add session-related codes, to retrieve subordinates
		LAPSUser currentManager = (LAPSUser) session.getAttribute("user");
		List<LAPSUser> subordinates = userService.findSubordinates(currentManager.getUserId());

		Map<LAPSUser, List<LeaveApplication>> subordinate2LAs = new HashMap<>();
		for (LAPSUser u : subordinates) {
			List<LeaveApplication> userLAList = leaveApplicationService.findLeaveApplicationsToProcess(u.getUserId());
			if (userLAList != null) {
				subordinate2LAs.put(u, userLAList);
			}
		}

		model.addAttribute("viewApplications", subordinate2LAs);

		return "manager-view-applications";
	}

	// ------------------------------------------------------//
	// 1.Find the respective application, bind to model
	// 2.Create a html page, showing id, name, description,
	// from date, end date, type, status, maybe entitlementleft?
	// ------------------------------------------------------//
	@GetMapping(value = "/leave/process/{id}")
	public String viewApplicationById(@PathVariable int id, Model model) {
		LeaveApplication application = leaveApplicationService.findLeaveApplicationById(id);
		model.addAttribute("la", application);

		LAPSUser user = application.getUser();
		int entitlement = holidayservice.getEntitlement(application);
		boolean enough = true;
		switch (application.getType().toString()) {
		case "MedicalLeave":
			if (user.getMedicalLeaveEntitlement() < entitlement) {
				enough = false;
			}
			model.addAttribute("entitlementLeft", user.getMedicalLeaveEntitlement());
			break;
		case "AnnualLeave":
			if (user.getAnnualLeaveEntitlement() < entitlement) {
				enough = false;
			}
			model.addAttribute("entitlementLeft", user.getAnnualLeaveEntitlement());
			break;
		case "CompensationLeave":
			if (user.getCompensationLeaveEntitlement() < entitlement) {
				enough = false;
			}
			model.addAttribute("entitlementLeft", user.getCompensationLeaveEntitlement());
			break;
		}
		model.addAttribute("result", enough);

		return "manager-application-details";
	}

	@PostMapping(value = "/leave/process/{id}")
	public String approveOrRejectApplication(@RequestParam String decision, @PathVariable int id) {
		LeaveApplication application = leaveApplicationService.findLeaveApplicationById(id);
		if (decision.equalsIgnoreCase(ApplicationStatusEnum.APPROVED.toString())) {
			application.setStatus(ApplicationStatusEnum.APPROVED);
			LAPSUser user = application.getUser();
			int entitlement = holidayservice.getEntitlement(application);
			switch (application.getType().toString()) {
			case "MedicalLeave":
				user.setMedicalLeaveEntitlement(user.getMedicalLeaveEntitlement() - entitlement);
				userService.updateUser(user);
				break;
			case "AnnualLeave":
				user.setAnnualLeaveEntitlement(user.getAnnualLeaveEntitlement() - entitlement);
				userService.updateUser(user);
				break;
			case "CompensationLeave":
				user.setCompensationLeaveEntitlement(user.getCompensationLeaveEntitlement() - entitlement);
				userService.updateUser(user);
				break;
			}

		} else {
			application.setStatus(ApplicationStatusEnum.REJECTED);
		}
		leaveApplicationService.updateLeaveApplication(application);
		return "redirect:/v1/manager/view";
	}

	// ------------------------------------------------------//
	// Show list of leave application
	// 1.Get Manager info form session(implement later)
	// (done)2.Find related staff, staff.managerId = this.id
	// SELECT s FROM User s WHERE s.managerId = id
	// retrieve data from DB
	// (done)3.Find related applications, staff.leaveApplications
	// 4.Show respective html page
	// ------------------------------------------------------//
	@RequestMapping(value = "/leave/history")
	public String viewApplicationsHistory(HttpSession session, Model model) {
		// Need to add session-related codes, to retrieve subordinates
		LAPSUser currentManager = (LAPSUser) session.getAttribute("user");
		List<LAPSUser> subordinates = userService.findSubordinates(currentManager.getUserId());

		Map<LAPSUser, List<LeaveApplication>> subordinate2LAs = new HashMap<>();
		for (LAPSUser u : subordinates) {
			List<LeaveApplication> userLAList = leaveApplicationService.findLeaveApplicationsByUserId(u.getUserId());
			if (userLAList != null) {
				subordinate2LAs.put(u, userLAList);
			}
		}

		model.addAttribute("viewApplications", subordinate2LAs);
		return "manager-application-history";
	}
	
//	@RequestMapping(value = "/claim/view")
//	public String viewClaimsForApproval(HttpSession session, Model model) {
//
//		// Need to add session-related codes, to retrieve subordinates
//		LAPSUser currentManager = (LAPSUser) session.getAttribute("user");
//		List<LAPSUser> subordinates = userService.findSubordinates(currentManager.getUserId());
//
//		Map<LAPSUser, List<LeaveApplication>> subordinate2LAs = new HashMap<>();
//		for (LAPSUser u : subordinates) {
//			List<LeaveApplication> userLAList = leaveApplicationService.findLeaveApplicationsToProcess(u.getUserId());
//			if (userLAList != null) {
//				subordinate2LAs.put(u, userLAList);
//			}
//		}
//
//		model.addAttribute("viewApplications", subordinate2LAs);
//
//		return "manager-view-applications";
//	}

}
