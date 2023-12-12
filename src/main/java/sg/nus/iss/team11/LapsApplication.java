package sg.nus.iss.team11;

import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.LeaveApplicationStatusEnum;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;
import sg.nus.iss.team11.model.Role;
import sg.nus.iss.team11.model.User;
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
	CommandLineRunner loadData(UserRepository userRepo, LeaveApplicationRepository leaveRepo, RoleRepository roleRepo) {
		return (args) -> {
			// clean start
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
			userRepo.save(tin.setManager(esther));
			userRepo.save(cherwah.setManager(esther));
			userRepo.save(yuenkwan.setManager(esther));
			userRepo.save(esther.setManager(esther));
			
			LeaveApplication la1 = new LeaveApplication(tin, "annual leave for tin", LeaveApplicationStatusEnum.APPLIED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3));
			LeaveApplication la2 = new LeaveApplication(esther, "annual leave for esther", LeaveApplicationStatusEnum.APPLIED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3));
			LeaveApplication la3 = new LeaveApplication(cherwah, "annual leave for cherwah", LeaveApplicationStatusEnum.APPLIED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3));
			LeaveApplication la4 = new LeaveApplication(cherwah, "Let me leave", LeaveApplicationStatusEnum.UPDATED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3));
			LeaveApplication la5 = new LeaveApplication(esther, "Leave anytime I want", LeaveApplicationStatusEnum.APPROVED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3));
			LeaveApplication la6 = new LeaveApplication(cherwah, "Why I can't leave", LeaveApplicationStatusEnum.REJECTED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3));

			leaveRepo.save(la1);
			leaveRepo.save(la2);
			leaveRepo.save(la3);
			leaveRepo.save(la4);
			leaveRepo.save(la5);
			leaveRepo.save(la6);
		};
	}

}
