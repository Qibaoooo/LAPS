package sg.nus.iss.team11.controller.API;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.model.LeaveApplication;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/manager")
public class APIManagerController {
	@Autowired
	UserService userService;
	
	@Autowired
	LeaveApplicationService leaveApplicationService;
	
	@GetMapping(value = "/leave/view")
	public ResponseEntity<String> viewApplicationsForApproval(Authentication authentication, Principal principal) {

		// Need to add session-related codes, to retrieve subordinates
		LAPSUser currentManager = userService.findUserByUsername(principal.getName());
		
		JSONArray leaveList = new JSONArray();
		
		List<LAPSUser> subordinates = userService.findSubordinates(currentManager.getUserId());
		
		for(LAPSUser u:subordinates) {
			JSONArray userLeave = new JSONArray();
			List<LeaveApplication> userLAList = leaveApplicationService.findLeaveApplicationsToProcess(u.getUserId());
			for (LeaveApplication l:userLAList) {
				JSONObject leave = new JSONObject();
				leave.put("id", l.getId());
				leave.put("comment", l.getComment());
				leave.put("description", l.getDescription());
				leave.put("fromDate", l.getFromDate());
				leave.put("toDate", l.getToDate());
				leave.put("status", l.getStatus().toString());
				leave.put("type", l.getType());
				
				userLeave.put(leave);
			}
			leaveList.put(userLeave);
		}

		return new ResponseEntity<>(leaveList.toString(), HttpStatus.OK);
	}

}
