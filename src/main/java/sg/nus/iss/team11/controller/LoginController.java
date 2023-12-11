package sg.nus.iss.team11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import sg.nus.iss.team11.model.User;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login/authenticate")
	public String login(@ModelAtrribute("User") @Valid User user) {
		// Validating username and password
		
		// Authenticating username and password from database
//		User u = userService.authenticate(user.getName(), user.getPassword());
//	    if (u == null) {
//	      model.addAttribute("loginMessage", "Incorrect username/password");
//	      return "login";
	    }
	}
}
