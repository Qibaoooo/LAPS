package sg.nus.iss.team11.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sg.nus.iss.team11.model.LAPSUser;

@Controller
public class CommonController {
	@RequestMapping(value = "/about")
	public String aboutPage() {
		return "about";
	}
	

	/*
	 * All methods below this comment is for API end points. API end points are for
	 * ReactJS front end. If you are adding methods for Java Spring MVC (part of our
	 * compulsory feature), please add them above this commend block.
	 * 
	 */
	
	
	@GetMapping(value = "/api/userinfo")
	public ResponseEntity<String> getUserInfo(HttpSession session, HttpServletResponse response) {
		LAPSUser user = (LAPSUser) session.getAttribute("user");
		
		JSONObject json = new JSONObject();
		json.put("username", user.getUsername());
		json.put("id", user.getUserId());
		json.put("role", user.getRole());
		
		ResponseEntity<String> responseEntity = new ResponseEntity<>(json.toString(), HttpStatus.OK);
		return responseEntity;
	}
}
