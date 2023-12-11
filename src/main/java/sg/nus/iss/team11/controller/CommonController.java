package sg.nus.iss.team11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
	@RequestMapping(value = "/about")
	public String aboutPage() {
		return "about";
	}
}
