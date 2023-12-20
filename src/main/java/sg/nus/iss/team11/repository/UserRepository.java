package sg.nus.iss.team11.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.nus.iss.team11.model.LAPSUser;

@Repository
public interface UserRepository extends JpaRepository<LAPSUser, Integer> {
	Optional<LAPSUser> findLAPSUserByUsername(String username);

	@Query("SELECT s FROM LAPSUser s WHERE s.managerId = :managerId")
	List<LAPSUser> findSubordinates(@Param("managerId") Integer managerId);
	
	@Query("SELECT s From LAPSUser s WHERE s.username = :username AND s.password = :password")
	LAPSUser findLAPSUserByNamePwd(@Param("username")String username, @Param("password")String password);
	
	@Query("SELECT DISTINCT s.managerId From LAPSUser s")
	List<Integer> findAllManagerId();
	
	@Query("SELECT MAX(u.userId) FROM LAPSUser u")
	int findMaxId();
	
	@Query("SELECT DISTINCT s.username From LAPSUser s Where s.role.roleId='manager'")
	List<String> findAllManagerName();
}
	