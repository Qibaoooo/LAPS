package sg.nus.iss.team11.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.nus.iss.team11.controller.service.LeaveApplicationService;
import sg.nus.iss.team11.model.LeaveApplication;

@Controller
public class MovementRegisterController {
	@Autowired
	public LeaveApplicationService leaveApplicationService;
	
	@GetMapping("/movement_register")
	public String getList(Model model) {
		
		return "login";
	}
	
	@PostMapping("/movement_register")
	public String getList(@RequestParam(defaultValue = "2023") int year, @RequestParam(defaultValue = "12") int month, Model model ) {
		List<LeaveApplication> UserLeaveForMonth = leaveApplicationService.findLeaveApplicationByYearMonth(year, month);
		for(LeaveApplication la: UserLeaveForMonth){
			System.out.println(la.getUser().getUsername());
		}
		
		return "login";
	}
}
