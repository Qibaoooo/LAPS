package sg.nus.iss.team11;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.nus.iss.team11.model.Admin;
import sg.nus.iss.team11.model.Manager;
import sg.nus.iss.team11.model.Staff;
import sg.nus.iss.team11.repository.UserRepository;

@SpringBootApplication
public class LapsApplication {

	public static void main(String[] args) {
//		test
		SpringApplication.run(LapsApplication.class, args);
	}
	
	@Bean
	CommandLineRunner loadData(UserRepository userRepo) {
		return (args) -> {
			// clean start
			userRepo.deleteAll();
			
			// add users
			Admin admin1 = new Admin("admin1", "123");
			Manager manager1 = new Manager("admin1", "123", null);
			Staff staff1 = new Staff("admin1", "123", null, 0);
			
			userRepo.save(admin1);
			userRepo.save(manager1);
			userRepo.save(staff1);
		};
	}

}
