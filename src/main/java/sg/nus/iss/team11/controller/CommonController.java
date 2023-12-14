package sg.nus.iss.team11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/v1")
public class CommonController {
	@RequestMapping(value = "/about")
	public String aboutPage() {
		return "about";
	}
	
	@RequestMapping(value = "/home")
	public String homePage(HttpSession session) {
		return "about";
	}
}
