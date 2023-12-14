package sg.nus.iss.team11.controller;

import java.util.List;
import java.util.stream.Collectors;

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
	
//	@GetMapping("/movement_register")
//	public String getList(Model model) {
//		
//		return "movement_register";
//	}
	
	@GetMapping("/movement_register")
	public String getList(@RequestParam(defaultValue = "2023") int year, @RequestParam(defaultValue = "12") int month, Model model ) {
		List<LeaveApplication> UserLeaveForMonth = leaveApplicationService.findLeaveApplicationByYearMonth(year, month);
//		for(LeaveApplication la: UserLeaveForMonth){
//			System.out.println(la.getUser().getUsername());
//		}
//		
//		return "login";
		List<String> usernames = UserLeaveForMonth.stream()
                .map(la -> la.getUser().getUsername())
                .collect(Collectors.toList());

		model.addAttribute("usernames", usernames);
		
		return "movement_register"; // Change to the name of your template
	}
}
