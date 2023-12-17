package sg.nus.iss.team11.controller.API;

import java.security.Principal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.iss.team11.controller.API.payload.NewEmployee;
import sg.nus.iss.team11.controller.exception.RoleNotFound;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.RoleService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.Role;
import sg.nus.iss.team11.validator.RoleValidator;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/admin")

public class APIAdminController {
	@Autowired
	UserService userservice;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	RoleService roleservice;

	@GetMapping(value = "/employee")
	public ResponseEntity<String> viewEmployeeList(Authentication authentication, Principal principal) {
		List<LAPSUser> employees = userservice.findAllUsers();
		JSONArray employeesList = new JSONArray();
		for (LAPSUser e : employees) {
			JSONObject ep = new JSONObject();
			ep.put("Id", e.getUserId());
			ep.put("name", e.getUsername());
			ep.put("managerId", e.getManagerId());
			ep.put("role", e.getRole().getName());
			ep.put("annualLeaveEntitlement", e.getAnnualLeaveEntitlement());
			ep.put("medicalLeaveEntitlement", e.getMedicalLeaveEntitlement());
			ep.put("compensationLeaveEntitlement", e.getCompensationLeaveEntitlement());
			employeesList.put(ep);
		}

		return new ResponseEntity<>(employeesList.toString(), HttpStatus.OK);
	}

	@PostMapping(value = "/employee/new")
	public ResponseEntity<String> createNewEmployee(Principal principal, @RequestBody NewEmployee newCreateEmployee) {
		LAPSUser nuser = new LAPSUser();
		nuser.setUsername(newCreateEmployee.getUsername());
		nuser.setPassword(encoder.encode(newCreateEmployee.getPassword()));
		nuser.setManagerId(userservice.findUserByUsername(newCreateEmployee.getManagerName()).getUserId());
		nuser.setRole(roleservice.findRoleByRoleName(newCreateEmployee.getRoleName()));
		nuser.setAnnualLeaveEntitlement(newCreateEmployee.getAnnualLeaveEntitlement());
		nuser.setMedicalLeaveEntitlement(newCreateEmployee.getMedicalLeaveEntitlement());
		nuser.setCompensationLeaveEntitlement(newCreateEmployee.getCompensationLeaveEntitlement());
		LAPSUser created = userservice.createUser(nuser);

		return new ResponseEntity<String>("user created:" + created.getUserId(), HttpStatus.OK);
	}
}



