package sg.nus.iss.team11;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.nus.iss.team11.model.*;
import sg.nus.iss.team11.repository.UserRepository;

@SpringBootTest
class LapsApplicationTests {
	
	@Autowired
	UserRepository repo;
	
	@Test
	void contextLoads() {
		Admin admin1 = new Admin("admin1", "123");
		Manager manager1 = new Manager("admin1", "123", 0);
		Staff staff1 = new Staff("admin1", "123", 0);
		
		
		repo.save(admin1);
		repo.save(manager1);
		repo.save(staff1);
	}

}
