package sg.nus.iss.team11.controller.API;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.team11.controller.API.payload.NewClaimRequest;
import sg.nus.iss.team11.controller.service.CompensationClaimService;
import sg.nus.iss.team11.controller.service.RoleService;
import sg.nus.iss.team11.controller.service.UserService;
import sg.nus.iss.team11.model.ApplicationStatusEnum;
import sg.nus.iss.team11.model.CompensationClaim;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.repository.CompensationClaimRepository;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/staff")
public class APICompensationClaimController {
	@Autowired
	CompensationClaimService claimService;

	@Autowired
	RoleService roleService;

	@Autowired
	UserService userService;
	
	
	@PostMapping(value = "/claim/new")
	public ResponseEntity<String> createNewClaim(Principal principal, @RequestBody NewClaimRequest claimRequest) {
		
		LAPSUser user = userService.findUserByUsername(principal.getName());

		CompensationClaim claim = new CompensationClaim();
		claim.setDescription(claimRequest.getDescription());
		claim.setOvertimeTime(claimRequest.getOvertimeTime());
		claim.setOverTimeDate(LocalDate.parse(claimRequest.getOvertimeDate()));
		claim.setStatus(ApplicationStatusEnum.APPLIED);
		claim.setUser(user);
		
		CompensationClaim created = claimService.createCompensationClaim(claim);		
		
		return new ResponseEntity<String>("claim created: "+created.getId(), HttpStatus.OK);
	}
	
}
