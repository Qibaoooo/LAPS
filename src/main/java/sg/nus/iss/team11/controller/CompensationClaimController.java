package sg.nus.iss.team11.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.team11.controller.service.CompensationClaimService;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.RoleService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.ApplicationStatusEnum;
import sg.nus.iss.team11.model.CompensationClaim;
import sg.nus.iss.team11.model.CompensationClaimTimeEnum;
import sg.nus.iss.team11.model.User;

@Controller
@RequestMapping(value = "/staff")
public class CompensationClaimController {

	@Autowired
	CompensationClaimService compensationClaimService;

	@Autowired
	RoleService roleService;

	@Autowired
	UserService userService;

	@RequestMapping(value = "claim/list")
	public String staffCompensationClaimList(Model model, HttpSession session) {

		User user = (User) session.getAttribute("user");

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
		
	    User user = (User) session.getAttribute("user");
	    compensationClaim.setUser(user);
	    compensationClaim.setStatus(ApplicationStatusEnum.APPLIED);
	    compensationClaimService.createCompensationClaim(compensationClaim);
	    
		return "redirect:/staff/claim/list";
	}

}
