package sg.nus.iss.team11.controller.API;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.RoleService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.validator.LeaveDateValidator;

@Controller
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class APILeaveApplicationController {
	

	/*
	 * All methods below this comment is for API end points. API end points are for
	 * ReactJS front end. If you are adding methods for Java Spring MVC (part of our
	 * compulsory feature), please add them to the controller without 'API' in the name.
	 * 
	 */
	
	@Autowired
	LeaveApplicationService leaveApplicationService;

	@Autowired
	RoleService roleService;

	@Autowired
	UserService userService;

	@Autowired
	private LeaveDateValidator leavedatevalidator;

	@InitBinder
	private void initValidators(WebDataBinder binder) {
		binder.addValidators(leavedatevalidator);
	}

	/*
	 * API to get the necessary info to populate new leave page.
	 */
	@GetMapping(value = "leave/form_info")
	public ResponseEntity<String> newLeave(HttpSession session, HttpServletResponse response) {

		JSONArray leaveTypes = new JSONArray(Arrays.asList(LeaveApplicationTypeEnum.values()));

		return new ResponseEntity<>(leaveTypes.toString(), HttpStatus.OK);
	}

	@GetMapping(value = "leave/list")
	public ResponseEntity<String> leaveList(HttpSession session, HttpServletResponse response) {

		LAPSUser user = (LAPSUser) session.getAttribute("user");

		JSONArray leaveList = new JSONArray();

		leaveApplicationService.findLeaveApplicationsByUserId(user.getUserId()).forEach((l) -> {
			JSONObject leave = new JSONObject();
			leave.put("id", l.getId());
			leave.put("comment", l.getComment());
			leave.put("description", l.getDescription());
			leave.put("fromDate", l.getFromDate());
			leave.put("toDate", l.getToDate());
			leave.put("status", l.getStatus().toString());
			leave.put("type", l.getType());
			
			leaveList.put(leave);
		});
		
		return new ResponseEntity<>(leaveList.toString(), HttpStatus.OK);
	}

}
