package sg.nus.iss.team11;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.nus.iss.team11.repository.UserRepository;

@SpringBootTest
class LapsApplicationTests {

	@Autowired
	UserRepository repo;

	@Test
	void contextLoads() {
		
	}

}
