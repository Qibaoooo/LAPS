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

			User user1 = userRepo.save(new User("adminUser", "password", adminRole));
			User user2 = userRepo.save(new User("esther", "password", managerRole));
			User user3 = userRepo.save(new User("tin", "password", staffRole));
			User user4 = userRepo.save(new User("cherwah", "password", staffRole));
			User user5 = userRepo.save(new User("yuenkwan", "password", staffRole));

			LeaveApplication la1 = new LeaveApplication(user3, "annual leave for tin", LeaveApplicationStatusEnum.APPLIED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3));

			leaveRepo.save(la1);
		};
	}

}
