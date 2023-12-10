package sg.nus.iss.team11.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.User;

@Controller
@RequestMapping(value = "/manager")
public class ManagerApplicationController {

	@Autowired
	public LeaveApplicationService leaveApplicationService;

	// ------------------------------------------------------//
	// Add login validate codes after
	// ------------------------------------------------------//

	// ------------------------------------------------------//
	// Show list of leave application
	// 1.Get Manager info form session(implement later)
	// (done)2.Find related staff, staff.managerId = this.id
	// SELECT s FROM User s WHERE s.managerId = id
	// retrieve data from DB
	// (done)3.Find related applications, staff.leaveApplications
	// 4.Show respective html page
	// ------------------------------------------------------//
	@RequestMapping(value = "/view")
	public String viewApplications(HttpSession session, Model model) {

		// Need to add session-related codes, to retrieve subordinates
		List<User> subordinates = (List<User>) session.getAttribute("subordinates");

		Map<User, List<LeaveApplication>> subordinate2LAs = new HashMap<>();
		for (User u : subordinates) {
			List<LeaveApplication> userLAList = leaveApplicationService.findLeaveApplicationsByUserId(u.getUserId());
			subordinate2LAs.put(u, userLAList);
		}

		model.addAttribute("viewApplications", subordinate2LAs);

		return "manager-view-applications";
	}

	// ------------------------------------------------------//
	// 1.Find the respective application, bind to model
	// 2.Create a html page, showing id, name, description,
	// from date, end date, type, status, maybe entitlementleft?
	// ------------------------------------------------------//
	@RequestMapping(value = "/process/{id}")
	public String viewApplicationById(@PathVariable int id, Model model) {
		LeaveApplication application = leaveApplicationService.findLeaveApplicationById(id);
		model.addAttribute("application", application);
		return "manager-application-details";
	}

}
