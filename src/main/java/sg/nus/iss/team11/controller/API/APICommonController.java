package sg.nus.iss.team11.controller.API;

import java.security.Principal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LAPSUser;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/common")
public class APICommonController {
	@Autowired
	private UserService userService;

	@GetMapping(value = "user-details")
	public ResponseEntity<?> userDetails(Principal principal) {
		LAPSUser user = userService.findUserByUsername(principal.getName());
		
		JSONObject userJson = new JSONObject();
		userJson.put("annualLeaveEntitlement", user.getAnnualLeaveEntitlement());
		userJson.put("compensationLeaveEntitlement",user.getCompensationLeaveEntitlement());
		userJson.put("medicalLeaveEntitlement",user.getMedicalLeaveEntitlement());
		userJson.put("managerId",user.getManagerId());
		userJson.put("role",user.getRole().getName());
		userJson.put("username",user.getUsername().toUpperCase());
		
		return new ResponseEntity<>(userJson.toString(),HttpStatus.OK);
	}
}
