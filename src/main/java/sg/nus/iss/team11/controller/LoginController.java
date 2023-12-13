package sg.nus.iss.team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LAPSUser;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LoginController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "/login", "/home" }, method = RequestMethod.GET)
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
			return "redirect:/manager/view";
		}

		if (roleId.equals("admin")) {
			return "redirect:/admin/employee";
		}

		return "redirect:/staff/leave/list";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}


}
