package sg.nus.iss.team11;

import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.ApplicationStatusEnum;
import sg.nus.iss.team11.model.CompensationClaim;
import sg.nus.iss.team11.model.CompensationClaimTimeEnum;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;
import sg.nus.iss.team11.model.Role;
import sg.nus.iss.team11.model.User;
import sg.nus.iss.team11.repository.CompensationClaimRepository;
import sg.nus.iss.team11.repository.LeaveApplicationRepository;
import sg.nus.iss.team11.repository.RoleRepository;
import sg.nus.iss.team11.repository.UserRepository;

@SpringBootApplication
public class LapsApplication {

	public static void main(String[] args) {
//		test
		SpringApplication.run(LapsApplication.class, args);
	}

	@Bean
	CommandLineRunner loadData(UserRepository userRepo, LeaveApplicationRepository leaveRepo, RoleRepository roleRepo,
			CompensationClaimRepository claimRepo) {
		return (args) -> {
			// clean start
			claimRepo.deleteAll();
			leaveRepo.deleteAll();
			userRepo.deleteAll();
			roleRepo.deleteAll();

			Role adminRole = roleRepo.save(new Role("admin", "Administrator", "System administrator"));
			Role staffRole = roleRepo.save(new Role("staff", "Staff", "Staff members"));
			Role managerRole = roleRepo.save(new Role("manager", "Manager", "Manager"));

			User adminUser = userRepo.save(new User("adminUser", "password", adminRole));
			User esther = userRepo.save(new User("esther", "password", managerRole));
			User tin = userRepo.save(new User("tin", "password", staffRole));
			User cherwah = userRepo.save(new User("cherwah", "password", staffRole));
			User yuenkwan = userRepo.save(new User("yuenkwan", "password", staffRole));

			leaveRepo.save(new LeaveApplication(tin, "annual leave for tin", ApplicationStatusEnum.APPLIED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3)));
			leaveRepo.save(new LeaveApplication(esther, "annual leave for esther", ApplicationStatusEnum.APPLIED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3)));
			leaveRepo.save(new LeaveApplication(cherwah, "annual leave for cherwah", ApplicationStatusEnum.APPLIED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3)));

			leaveRepo.save(new LeaveApplication(tin, "annual leave from 2 month back", ApplicationStatusEnum.APPROVED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().minusDays(60),
					LocalDate.now().minusDays(59)));
			leaveRepo.save(new LeaveApplication(tin, "annual leave from 2 month back", ApplicationStatusEnum.APPROVED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().minusDays(58),
					LocalDate.now().minusDays(57)));
			leaveRepo.save(new LeaveApplication(tin, "annual leave from 2 month back", ApplicationStatusEnum.APPROVED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().minusDays(56),
					LocalDate.now().minusDays(55)));

			leaveRepo.save(new LeaveApplication(tin, "annual leave from 1 month back", ApplicationStatusEnum.APPROVED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().minusDays(30),
					LocalDate.now().minusDays(29)));
			leaveRepo.save(new LeaveApplication(tin, "annual leave from 1 month back", ApplicationStatusEnum.APPROVED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().minusDays(28),
					LocalDate.now().minusDays(27)));
			leaveRepo.save(new LeaveApplication(tin, "annual leave from 1 month back", ApplicationStatusEnum.APPROVED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().minusDays(26),
					LocalDate.now().minusDays(25)));

			claimRepo.save(new CompensationClaim(tin, "cc for tin AM", ApplicationStatusEnum.APPLIED,
					CompensationClaimTimeEnum.AM, LocalDate.now().plusDays(10)));
			claimRepo.save(new CompensationClaim(tin, "cc for tin PM", ApplicationStatusEnum.APPLIED,
					CompensationClaimTimeEnum.PM, LocalDate.now().plusDays(10)));
			claimRepo.save(new CompensationClaim(tin, "cc for tin WHOLEDAY", ApplicationStatusEnum.APPLIED,
					CompensationClaimTimeEnum.WHOLEDAY, LocalDate.now().plusDays(10)));

		};
	}

}
