package sg.nus.iss.team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.security.JwtUtils;

@Controller
@RequestMapping(value = "/v1")
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;

	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("user", new LAPSUser());
		return "login";
	}

	@PostMapping("/login/authenticate")
	public String login(@ModelAttribute("user") @Valid LAPSUser user, BindingResult bindingResult, Model model,
			HttpSession session) {
		// Checking for empty Username and Password
		if (bindingResult.hasErrors()) {
			return "login";
		}
		
		//############# code for spring security
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		session.setAttribute("jwt", jwt);
		//############# end 
		
		// Authenticating Username and password from database
		LAPSUser searchedUser = userService.authenticateUser(user.getUsername(), user.getPassword());
		if (searchedUser == null) {
			model.addAttribute("loginMessage", "Incorrect username/password");
			return "login";
		}
		// Storing user object in HttpSession
		session.setAttribute("user", searchedUser);
		
		// Redirecting user to the specific role view
		String roleId = searchedUser.getRole().getRoleId();
		if (roleId.equals("manager")) {
			return "redirect:/v1/manager/leave/view";
		}

		if (roleId.equals("manager")) {
			return "redirect:/v1/manager/leave/view";
		}

		if (roleId.equals("admin")) {
			return "redirect:/v1/admin/employee";
		}

		return "redirect:/v1/staff/leave/list";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/v1/login";
	}

}