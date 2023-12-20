package sg.nus.iss.team11.controller.API;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.iss.team11.controller.API.payload.EditRole;
import sg.nus.iss.team11.controller.API.payload.NewEmployee;
import sg.nus.iss.team11.controller.API.payload.NewRole;
import sg.nus.iss.team11.controller.API.payload.EditClaimRequest;
import sg.nus.iss.team11.controller.API.payload.EditEmployee;
import sg.nus.iss.team11.controller.exception.RoleNotFound;
import sg.nus.iss.team11.controller.service.CompensationClaimService;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.RoleService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.ApplicationStatusEnum;
import sg.nus.iss.team11.model.CompensationClaim;
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
	@Autowired
	LeaveApplicationService leaveapplicationservice;
	@Autowired
	CompensationClaimService compensationclaimservice;

	@GetMapping(value = "/employee")
	public ResponseEntity<String> viewEmployeeList(Authentication authentication, Principal principal) {
		List<LAPSUser> employees = userservice.findAllUsers();
		JSONArray employeesList = new JSONArray();
		for (LAPSUser e : employees) {
			JSONObject ep = new JSONObject();
			ep.put("id", e.getUserId());
			ep.put("name", e.getUsername());
			ep.put("managerId", e.getManagerId());
			ep.put("role", e.getRole().getName());
			ep.put("type", e.getType());
			ep.put("annualLeaveEntitlement", e.getAnnualLeaveEntitlement());
			ep.put("medicalLeaveEntitlement", e.getMedicalLeaveEntitlement());
			ep.put("compensationLeaveEntitlement", e.getCompensationLeaveEntitlement());
			employeesList.put(ep);
		}

		return new ResponseEntity<>(employeesList.toString(), HttpStatus.OK);
	}

	@DeleteMapping(value = "/employee")
	public ResponseEntity<String> deleteEmployee(Principal principal, @RequestParam String username) {

		LAPSUser user = userservice.findUserByUsername(username);

		List<LeaveApplication> applis = user.getLeaveApplications();
		for (LeaveApplication la : applis) {
			leaveapplicationservice.removeLeaveApplication(la);
		}
		List<CompensationClaim> cclaims = user.getCompensationClaim();
		for(CompensationClaim cc:cclaims) {
			compensationclaimservice.removeCompensationClaim(cc);
		}
		userservice.removeUser(user);
		return new ResponseEntity<String>("Employee deleted: " + username, HttpStatus.OK);
	}


	@PostMapping(value = "/employee/new")
	public ResponseEntity<String> createNewEmployee(@RequestBody NewEmployee newCreateEmployee) {
		LAPSUser nuser = new LAPSUser();
		nuser.setUsername(newCreateEmployee.getUsername());
		nuser.setPassword(encoder.encode(newCreateEmployee.getPassword()));
		nuser.setManagerId(userservice.findUserByUsername(newCreateEmployee.getManagerName()).getUserId());
		nuser.setRole(roleservice.findRoleByRoleName(newCreateEmployee.getRoleName()));
		nuser.setType(newCreateEmployee.getType());
		nuser.setAnnualLeaveEntitlement(newCreateEmployee.getAnnualLeaveEntitlement());
		nuser.setMedicalLeaveEntitlement(newCreateEmployee.getMedicalLeaveEntitlement());
		nuser.setCompensationLeaveEntitlement(newCreateEmployee.getCompensationLeaveEntitlement());
		LAPSUser created = userservice.createUser(nuser);
		if (newCreateEmployee.getRoleName().equalsIgnoreCase("manager")) {
			created.setManagerId(created.getUserId());
			userservice.updateUser(created);
		}
		if (newCreateEmployee.getRoleName().equalsIgnoreCase("administrator")) {
			created.setType(null);
			created.setManagerId(0);
			created.setAnnualLeaveEntitlement(0);
			created.setMedicalLeaveEntitlement(0);
			created.setCompensationLeaveEntitlement(0);
			userservice.updateUser(created);
		}

		return new ResponseEntity<String>("user created:" + created.getUserId(), HttpStatus.OK);
	}

	@GetMapping(value = "/employee/new")
	public ResponseEntity<String> viewAllList(Authentication authentication, Principal principal2) {
		JSONArray bigList = new JSONArray();
		List<Role> roles = roleservice.findAllRoles();
		List<String> managers = userservice.findAllManagerName();
		JSONArray rolesList = new JSONArray();
		JSONArray managersList = new JSONArray();
		for (Role r : roles) {
			JSONObject rn = new JSONObject();
			rn.put("roleName", r.getName());
			rolesList.put(rn);
		}
		for (String m : managers) {
			JSONObject mn = new JSONObject();
			mn.put("managerName", m);
			managersList.put(mn);
		}
		bigList.put(managersList);
		bigList.put(rolesList);

		return new ResponseEntity<>(bigList.toString(), HttpStatus.OK);
	}

	@PostMapping(value = "/employee/edit")
	public ResponseEntity<String> editEmployeeInfo(Principal principal, @RequestBody EditEmployee editEmployee) {
		LAPSUser user = userservice.findUserByUsername(principal.getName());

		LAPSUser eUser = userservice.findUser(editEmployee.getId());

		eUser.setUsername(editEmployee.getUsername());
		eUser.setPassword(encoder.encode(editEmployee.getPassword()));
		eUser.setManagerId(userservice.findUserByUsername(editEmployee.getManagerName()).getUserId());
		eUser.setRole(roleservice.findRoleByRoleName(editEmployee.getRoleName()));
		eUser.setType(editEmployee.getType());
		eUser.setAnnualLeaveEntitlement(editEmployee.getAnnualLeaveEntitlement());
		eUser.setMedicalLeaveEntitlement(editEmployee.getMedicalLeaveEntitlement());
		eUser.setCompensationLeaveEntitlement(editEmployee.getCompensationLeaveEntitlement());

		userservice.updateUser(eUser);

		return new ResponseEntity<String>("user edited:" + editEmployee.getId(), HttpStatus.OK);
	}

	@GetMapping(value = "/role")
	public ResponseEntity<String> viewRoleList(Authentication authentication, Principal principal) {
		List<Role> roles = roleservice.findAllRoles();
		JSONArray rolesList = new JSONArray();
		for (Role r : roles) {
			JSONObject rp = new JSONObject();
			rp.put("name", r.getName());
			rp.put("description", r.getDescription());
			rp.put("id", r.getRoleId());
			rolesList.put(rp);
		}

		return new ResponseEntity<>(rolesList.toString(), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/role")
	public ResponseEntity<String>deleteRole(@RequestParam String id){
		 try {
		      roleservice.deleteRoleById(id);
		   } catch (Exception e) {
		      return new ResponseEntity<String>("can't delete role! It still has users assigned.", HttpStatus.BAD_REQUEST);
		   }
		return new ResponseEntity<String>("role deleted: " + id, HttpStatus.OK);
	}

	@PostMapping(value = "/role/new")
	public ResponseEntity<String> createNewRole(Principal principal, @RequestBody NewRole newRole) {

		// init a new Role
		Role role = new Role();
		
		role.setName(newRole.getName());
		role.setDescription(newRole.getDescription());
		role.setRoleId(newRole.getName().toLowerCase());
		
		Role created = roleservice.createRole(role);

		return new ResponseEntity<String>("role created: " + created.getRoleId(), HttpStatus.OK);
	}

	@PutMapping(value = "/role/edit")
	public ResponseEntity<String> editRole(Principal principal, @RequestBody EditRole editrole) {

		Role roles=new Role();
		roles.setRoleId(roleservice.findRoleByRoleName(editrole.getName()).getRoleId());
		roles.setName(editrole.getName());
		roles.setDescription(editrole.getDescription());

		Role edited = roleservice.updateRole(roles);

		return new ResponseEntity<String>("roles updated: " + edited.getRoleId(), HttpStatus.OK);
	}
}
