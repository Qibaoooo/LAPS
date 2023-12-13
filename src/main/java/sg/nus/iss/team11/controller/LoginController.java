package sg.nus.iss.team11.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.User;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LoginController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "/login", "/home" }, method = RequestMethod.GET)
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

	/*
	 * All methods below this comment is for API end points. API end points are for
	 * ReactJS front end. If you are adding methods for Java Spring MVC (part of our
	 * compulsory feature), please add them above this commend block.
	 * 
	 */

	@PostMapping(value = "/api/login/authenticate")
	public ResponseEntity<String> apiLogin(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
			HttpSession session, HttpServletResponse response) {
		// Checking for empty Username and Password
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("binding result has errors", HttpStatus.BAD_REQUEST);
		}

		// Authenticating Username and password from database
		User searchedUser = userService.authenticateUser(user.getUsername(), user.getPassword());
		if (searchedUser == null) {
			return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
		}
		
		// Storing user object in HttpSession
		session.setAttribute("user", searchedUser);
		
		Cookie cookie = new Cookie("JSESSIONID", session.getId());
		cookie.setHttpOnly(false);
		cookie.setPath("/");
		response.addCookie(cookie);

		JSONObject json = new JSONObject();
		json.put("username", searchedUser.getUsername());
		json.put("id", searchedUser.getUserId());
		json.put("role", searchedUser.getRole().getName());
		
		ResponseEntity<String> responseEntity = new ResponseEntity<>(json.toString(), HttpStatus.OK);
		return responseEntity;
	}

	@PostMapping(value = "/api/logout")
	public ResponseEntity<String> apiLogout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<>("Logged out", HttpStatus.OK);
	}

}
