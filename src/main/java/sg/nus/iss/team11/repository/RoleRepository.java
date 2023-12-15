package sg.nus.iss.team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.team11.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
	@Query("SELECT r FROM Role r WHERE r.name = :name")
	Role findRoleByName(@Param("name") String name);
}
