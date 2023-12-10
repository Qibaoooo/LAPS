package sg.nus.iss.team11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.model.LeaveApplication;

@Controller
@RequestMapping(value = "/staff")
public class LeaveApplicationController {
	@Autowired
	LeaveApplicationService leaveApplicationService;

	@RequestMapping(value = "leave/list")
	public String staffLeaveApplicationList(Model model) {
		// TODO: get user from UserSession later
//	    UserSession usession = (UserSession) session.getAttribute("usession");
//	    
//	    System.out.println(usession.getEmployee());

		List<LeaveApplication> laList = leaveApplicationService.findAllLeaveApplications();
		model.addAttribute("laList", laList);

		return "staff-leave-application-list.html";
	}
}
