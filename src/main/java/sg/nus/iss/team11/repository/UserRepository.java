package sg.nus.iss.team11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findUserByUsername(String username);

	@Query("SELECT s FROM User s WHERE s.managerId = :managerId")
	List<User> findSubordinates(@Param("managerId") Integer managerId);
}
