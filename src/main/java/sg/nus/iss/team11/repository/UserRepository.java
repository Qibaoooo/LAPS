package sg.nus.iss.team11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.LAPSUser;

public interface UserRepository extends JpaRepository<LAPSUser, Integer> {
	LAPSUser findLAPSUserByUsername(String username);

	@Query("SELECT s FROM LAPSUser s WHERE s.managerId = :managerId")
	List<LAPSUser> findSubordinates(@Param("managerId") Integer managerId);
	
	@Query("SELECT s From LAPSUser s WHERE s.username = :username AND s.password = :password")
	LAPSUser findLAPSUserByNamePwd(@Param("username")String username, @Param("password")String password);
	
}
