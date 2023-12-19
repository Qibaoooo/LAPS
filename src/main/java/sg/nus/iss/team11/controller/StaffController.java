package sg.nus.iss.team11.controller;

import java.time.LocalDate;
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
import sg.nus.iss.team11.controller.service.CompensationClaimService;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.RoleService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.ApplicationStatusEnum;
import sg.nus.iss.team11.model.CompensationClaim;
import sg.nus.iss.team11.model.CompensationClaimTimeEnum;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.validator.LeaveDateValidator;

@Controller
@RequestMapping(value = "/v1/staff")
public class StaffController {
	@Autowired
	LeaveApplicationService leaveApplicationService;

	@Autowired
	CompensationClaimService compensationClaimService;

	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private LeaveDateValidator leavedatevalidator;

	@InitBinder("leaveApplication")
	private void initValidators(WebDataBinder binder) {
		binder.addValidators(leavedatevalidator);
	}
	
	@RequestMapping(value = {"leave/list", "/"})
	public String staffLeaveApplicationList(Model model, HttpSession session) {

	    LAPSUser user = (LAPSUser) session.getAttribute("user");

		List<LeaveApplication> laList = leaveApplicationService.findLeaveApplicationsByUserId(user.getUserId());
		model.addAttribute("laList", laList);
		
		// Adding current date to the model
		model.addAttribute("currentDate", LocalDate.now());
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
			HttpSession session, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("leaveTypes", java.util.Arrays.asList(LeaveApplicationTypeEnum.values()));
			return "staff-new-leave-application";
		}

	    LAPSUser user = (LAPSUser) session.getAttribute("user");
		leaveApplication.setUser(user);
		leaveApplication.setStatus(ApplicationStatusEnum.APPLIED);
		leaveApplicationService.createLeaveApplication(leaveApplication);

		return "redirect:/v1/staff/leave/list";
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
			@PathVariable Integer id, HttpSession session, Model model) throws LeaveApplicationNotFound {
		
		if (result.hasErrors()) {
			model.addAttribute("leaveTypes", java.util.Arrays.asList(LeaveApplicationTypeEnum.values()));
			return "staff-edit-leave-application";
		}
		
		leaveApplication.setStatus(ApplicationStatusEnum.UPDATED);

		leaveApplicationService.updateLeaveApplication(leaveApplication);
		
		return "redirect:/v1/staff/leave/list";

	}
	
	@RequestMapping(value = "leave/cancel/{id}")
	public String cancelLeave(@PathVariable Integer id ) throws LeaveApplicationNotFound {
		LeaveApplication leaveApplication = leaveApplicationService.findLeaveApplicationById(id);
		
		leaveApplication.setStatus(ApplicationStatusEnum.CANCELLED);
		leaveApplicationService.updateLeaveApplication(leaveApplication);
		
		return "redirect:/v1/staff/leave/list";
	}	
	
	// merged from previous claim controller:
	

	@RequestMapping(value = "claim/list")
	public String staffCompensationClaimList(Model model, HttpSession session) {

		LAPSUser user = (LAPSUser) session.getAttribute("user");

		List<CompensationClaim> ccList = compensationClaimService.findCompensationClaimsByUserId(user.getUserId());
		model.addAttribute("ccList", ccList);

		return "staff-compensation-claim-list";
	}

	@GetMapping(value = "claim/new")
	public String newClaim(Model model) {
		model.addAttribute("claim", new CompensationClaim());
		model.addAttribute("timeChoices", java.util.Arrays.asList(CompensationClaimTimeEnum.values()));

		return "staff-new-compensation-claim";
	}

	@PostMapping(value = "claim/new")
	public String newClaim(@ModelAttribute CompensationClaim compensationClaim, BindingResult result,
			HttpSession session, Model model) {
		
		if (result.hasErrors()) {
			model.addAttribute("timeChoices", java.util.Arrays.asList(CompensationClaimTimeEnum.values()));
			return "staff-new-compensation-claim";
		}
		
	    LAPSUser user = (LAPSUser) session.getAttribute("user");
	    compensationClaim.setUser(user);
	    compensationClaim.setStatus(ApplicationStatusEnum.APPLIED);
	    compensationClaimService.createCompensationClaim(compensationClaim);
	    
		return "redirect:/v1/staff/claim/list";
	}


}
