package sg.nus.iss.team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.User;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping("/login/authenticate")
	public String login(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model,
		      HttpSession session) {
		// Checking for empty Username and Password
		if (bindingResult.hasErrors()) {
			return "login";
		}
		
		// Authenticating Username and password from database
		User searchedUser = userService.authenticateUser(user.getUsername(), user.getPassword());
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
