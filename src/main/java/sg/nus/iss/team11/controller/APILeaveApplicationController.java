package sg.nus.iss.team11.controller;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
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
import sg.nus.iss.team11.model.User;
import sg.nus.iss.team11.validator.LeaveDateValidator;

@Controller
@RequestMapping(value = "/api")
public class APILeaveApplicationController {
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

		User user = (User) session.getAttribute("user");

		JSONArray leaveList = new JSONArray();

		leaveApplicationService.findLeaveApplicationsByUserId(user.getUserId()).forEach((l) -> {
			JSONObject leave = new JSONObject();
			leave.put("", l.getId());
			leave.put("", l.getComment());
			leave.put("", l.getDescription());
			leave.put("", l.getFromDate());
			leave.put("", l.getToDate());
			leave.put("", l.getStatus().toString());
			leave.put("", l.getType());
			
			leaveList.put(leave);
		});
		
		return new ResponseEntity<>(leaveList.toString(), HttpStatus.OK);
	}

}
