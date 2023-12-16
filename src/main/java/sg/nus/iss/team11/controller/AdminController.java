package sg.nus.iss.team11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.iss.team11.controller.exception.RoleNotFound;
import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.controller.service.RoleService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.Role;
import sg.nus.iss.team11.validator.RoleValidator;

@Controller
@RequestMapping(value = "/v1/admin")
public class AdminController {
	@Autowired
	public UserService userservice;
	@Autowired
	public RoleService roleservice;
	@Autowired
	public LeaveApplicationService leaveservice;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	private RoleValidator rValidator;
	
	@InitBinder("role")
	private void initRoleBinder(WebDataBinder binder) {
		binder.addValidators(rValidator);
	}

	@RequestMapping(value = "/employee")
	public String viewEmployeeList(HttpSession session, Model model) {
		LAPSUser currentAdmin = (LAPSUser) session.getAttribute("user");
		List<LAPSUser> employees = userservice.findAllUsers();
		model.addAttribute("Employees", employees);
		return "employee-list";
	}

	@RequestMapping(value = "/employee/delete/{id}")
	public String deleteEmployee(@PathVariable int id) {
		List<LeaveApplication> userapplications = leaveservice.findLeaveApplicationsByUserId(id);
		for (LeaveApplication l : userapplications) {
			leaveservice.removeLeaveApplication(l);
		}

		userservice.removeUser(userservice.findUser(id));

		return "redirect:/v1/admin/employee";
	}

	@GetMapping(value = "/employee/new")
	public String newEmployee(Model model) {
		model.addAttribute("newEmployee", new LAPSUser());
		List<Integer> managersId = userservice.findAllManagerId();
		model.addAttribute("managersId", managersId);
		return "employee-new";
	}

	@PostMapping(value = "/employee/new")
	public String createEmployee(@ModelAttribute LAPSUser newEmployee, @RequestParam String roleId) {
		newEmployee.setRole(roleservice.findRole(roleId));
		newEmployee.setPassword(encoder.encode(newEmployee.getPassword()));
		LAPSUser created = userservice.createUser(newEmployee);
		if (roleId.equalsIgnoreCase("manager")) {
			created.setManagerId(created.getUserId());
			userservice.updateUser(created);
		}
		if (roleId.equalsIgnoreCase("admin")) {
			created.setManagerId(0);
			userservice.updateUser(created);
		}
		return "redirect:/v1/admin/employee";
	}

	@GetMapping(value = "/employee/edit/{id}")
	public String editEmployee(Model model, @PathVariable int id) {
		model.addAttribute("editEmployee", userservice.findUser(id));
		List<Integer> managersId = userservice.findAllManagerId();
		model.addAttribute("managersId", managersId);
		return "employee-edit";
	}

	@PostMapping(value = "/employee/edit/{id}")
	public String saveEmployee(@ModelAttribute LAPSUser editEmployee, @RequestParam String roleId) {
		editEmployee.setRole(roleservice.findRole(roleId));
		editEmployee.setPassword(encoder.encode(editEmployee.getPassword()));
		LAPSUser created = userservice.updateUser(editEmployee);
		if (roleId.equalsIgnoreCase("manager")) {
			created.setManagerId(created.getUserId());
			userservice.updateUser(created);
		}
		if (roleId.equalsIgnoreCase("admin")) {
			created.setManagerId(0);
			userservice.updateUser(created);
		}
		return "redirect:/v1/admin/employee";
	}
	@GetMapping("/role/list")
	  public String roleListPage(Model model) {
	    List<Role> roleList = roleservice.findAllRoles();
	    model.addAttribute("roleList", roleList);
	    
	    return "role-list";
	  }
	
	@GetMapping("/role/new")
	public String newRolePage(Model model) {

		Role newrole = new Role();
		model.addAttribute("role", newrole);
		return "role-new";}
	
	@PostMapping("/role/new")
	public String createNewRole(@ModelAttribute @Valid Role role, BindingResult result) {
		if (result.hasErrors()) {
			return "role-new";
		}
	
	// set roleId here programmatically
	role.setRoleId(role.getName().toLowerCase());	
	roleservice.createRole(role);
	String message = "New role " + role.getRoleId() + " was successfully created.";
	System.out.println(message);
	
	return "redirect:/v1/admin/role/list";}
	
	@GetMapping("/role/edit/{id}")
	public String editRolePage(@PathVariable String id, Model model) {
		Role role = roleservice.findRole(id);
		model.addAttribute("role", role);
		
		return "role-edit";
	}
	
	@PostMapping("/role/edit/{id}")
	public String editRole(@ModelAttribute @Valid Role role, BindingResult result, 
			@PathVariable String id) throws RoleNotFound {
		if (result.hasErrors()) {
			return "role-edit";
		}

		String message = "Role was successfully updated.";
		System.out.println(message);
		roleservice.updateRole(role);
		
		return "redirect:/v1/admin/role/list";
	}
	
	@GetMapping("/role/delete/{id}")
	public String deleteRole(@PathVariable String id)
			throws RoleNotFound {
		Role role = roleservice.findRole(id);
//		check if role is in use
		roleservice.removeRole(role);
		
		String message = "The role " + role.getRoleId() + " was successfully deleted.";
		System.out.println(message);
		
		return "redirect:/v1/admin/role/list";
	}
}
