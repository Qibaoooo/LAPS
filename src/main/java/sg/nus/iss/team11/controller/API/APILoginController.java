package sg.nus.iss.team11.controller.API;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LAPSUser;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class APILoginController {

	@Autowired
	private UserService userService;

	/*
	 * All methods below this comment is for API end points. API end points are for
	 * ReactJS front end. If you are adding methods for Java Spring MVC (part of our
	 * compulsory feature), please add them to the controller without 'API' in the name.
	 * 
	 */

	@PostMapping(value = "/api/login/authenticate")
	public ResponseEntity<String> apiLogin(@ModelAttribute("user") @Valid LAPSUser user, BindingResult bindingResult,
			HttpSession session, HttpServletResponse response) {
		// Checking for empty Username and Password
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("binding result has errors", HttpStatus.BAD_REQUEST);
		}

		// Authenticating Username and password from database
		LAPSUser searchedUser = userService.authenticateUser(user.getUsername(), user.getPassword());
		if (searchedUser == null) {
			return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
		}
		
		// Storing user object in HttpSession
		session.setAttribute("user", searchedUser);
		
		// https://stackoverflow.com/questions/56965476/cors-cookie-not-set-on-cross-domains-using-fetch-set-credentials-include-an
		Cookie cookie = new Cookie("JSESSIONID", session.getId());
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setAttribute("SameSite", "None");
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
