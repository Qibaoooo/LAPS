package sg.nus.iss.team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.team11.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findUserByUsername(String username);
}
