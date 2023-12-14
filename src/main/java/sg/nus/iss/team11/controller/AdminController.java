package sg.nus.iss.team11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.LAPSUser;

@Controller
@RequestMapping(value="/v1/admin")
public class AdminController {
@Autowired
public UserService userservice;
@RequestMapping(value="/employee")
public String viewEmployeeList(HttpSession session, Model model) {
	LAPSUser currentAdmin=(LAPSUser)session.getAttribute("user");
	List<LAPSUser> employees=userservice.findAllUsers();
	model.addAttribute("Employees", employees);
	return "employee-list";
}
}
