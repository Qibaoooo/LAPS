package sg.nus.iss.team11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.team11.controller.service.RoleService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.User;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	public UserService userservice;
	@Autowired
	public RoleService roleservice;

	@RequestMapping(value = "/employee")
	public String viewEmployeeList(HttpSession session, Model model) {
		User currentAdmin = (User) session.getAttribute("user");
		List<User> employees = userservice.findAllUsers();
		model.addAttribute("Employees", employees);
		return "employee-list";
	}

	@RequestMapping(value = "/employee/delete/{id}")
	public String deleteEmployee(@PathVariable int id) {
		// add code to delete specific employee
		userservice.removeUser(userservice.findUser(id));
		return "redirect:/admin/employee";
	}

	@GetMapping(value = "/employee/new")
	public String newEmployee(Model model) {
		model.addAttribute("newEmployee",new User());
		List<Integer> managersId=userservice.findAllManagerId();
		managersId.add(userservice.findMaxId()+1);
		model.addAttribute("managersId",managersId);
		return "employee-new";
	}

	@PostMapping(value = "/employee/new")
	public String createEmployee(@ModelAttribute User newEmployee,@RequestParam String roleId) {
		newEmployee.setRole(roleservice.findRole(roleId));
		User created = userservice.createUser(newEmployee);
		if (roleId.equalsIgnoreCase("manager")) {
		created.setManagerId(created.getUserId());
		userservice.updateUser(created);
		}
		return "redirect:/admin/employee";
	}
	@GetMapping(value = "/employee/edit/{id}")
	public String editEmployee(Model model,@PathVariable int id) {
		model.addAttribute("editEmployee",userservice.findUser(id));
		List<Integer> managersId=userservice.findAllManagerId();
		managersId.add(userservice.findMaxId()+1);
		model.addAttribute("managersId",managersId);
		return "employee-edit";
	}

	@PostMapping(value = "/employee/edit/{id}")
	public String saveEmployee(@ModelAttribute User editEmployee,@RequestParam String roleId) {
		editEmployee.setRole(roleservice.findRole(roleId));
		userservice.updateUser(editEmployee);
		return "redirect:/admin/employee";
	}
}
