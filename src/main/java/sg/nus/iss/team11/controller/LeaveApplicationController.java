package sg.nus.iss.team11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.iss.team11.controller.exception.LeaveApplicationNotFound;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.RoleService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.LeaveApplicationStatusEnum;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;
import sg.nus.iss.team11.model.User;
import sg.nus.iss.team11.validator.LeaveDateValidator;

@Controller
@RequestMapping(value = "/staff")
public class LeaveApplicationController {
	@Autowired
	LeaveApplicationService leaveApplicationService;

	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private LeaveDateValidator leavedatevalidator;

	@InitBinder
	private void initCourseBinder(WebDataBinder binder) {
		binder.addValidators(leavedatevalidator);
	}
	
	@RequestMapping(value = "leave/list")
	public String staffLeaveApplicationList(Model model, HttpSession session) {

	    User user = (User) session.getAttribute("user");

		List<LeaveApplication> laList = leaveApplicationService.findLeaveApplicationsByUserId(user.getUserId());
		model.addAttribute("laList", laList);

		return "staff-leave-application-list";
	}

	@GetMapping(value = "leave/new")
	public String newLeave(Model model) {
		model.addAttribute("leaveApplication", new LeaveApplication());
		model.addAttribute("leaveTypes", java.util.Arrays.asList(LeaveApplicationTypeEnum.values()));

		return "staff-new-leave-application";
	}

	@PostMapping(value = "leave/new")
	public String newLeave(@Valid @ModelAttribute LeaveApplication leaveApplication, BindingResult result,
			HttpSession session) {

		if (result.hasErrors()) {
			return "staff-new-leave-application";
		}

	    User user = (User) session.getAttribute("user");
		leaveApplication.setUser(user);
		leaveApplication.setStatus(LeaveApplicationStatusEnum.APPLIED);
		leaveApplicationService.createLeaveApplication(leaveApplication);

		return "redirect:/staff/leave/list";
	}

	@GetMapping(value = "leave/edit/{id}")
	public String editLeave(@PathVariable Integer id, Model model) {
		LeaveApplication la = leaveApplicationService.findLeaveApplicationById(id);

		model.addAttribute("leaveApplication", la);
		model.addAttribute("leaveTypes", java.util.Arrays.asList(LeaveApplicationTypeEnum.values()));

		return "staff-edit-leave-application";
	}

	@PostMapping(value = "leave/edit/{id}")
	public String editLeave(@Valid @ModelAttribute LeaveApplication leaveApplication, BindingResult result,
			@PathVariable Integer id, HttpSession session) throws LeaveApplicationNotFound {
		
		if (result.hasErrors()) {
			return "staff-edit-leave-application";
		}
		
		leaveApplication.setStatus(LeaveApplicationStatusEnum.UPDATED);

		leaveApplicationService.updateLeaveApplication(leaveApplication);
		
		return "redirect:/staff/leave/list";

	}
	
	@RequestMapping(value = "leave/cancel/{id}")
	public String cancelLeave(@PathVariable Integer id ) throws LeaveApplicationNotFound {
		LeaveApplication leaveApplication = leaveApplicationService.findLeaveApplicationById(id);
		
		leaveApplication.setStatus(LeaveApplicationStatusEnum.CANCELLED);
		leaveApplicationService.updateLeaveApplication(leaveApplication);
		
		return "redirect:/staff/leave/list";
	}

}
