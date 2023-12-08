package sg.nus.iss.team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.team11.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
