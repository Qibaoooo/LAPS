package sg.nus.iss.team11.controller.service;

import java.util.List;

import sg.nus.iss.team11.model.Role;

public interface RoleService {
	  List<Role> findAllRoles();

	  Role findRole(String roleId);

	  Role createRole(Role role);

	  Role updateRole(Role role);

	  void removeRole(Role role);

}
