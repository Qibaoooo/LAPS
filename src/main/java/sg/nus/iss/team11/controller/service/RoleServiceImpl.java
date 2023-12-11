package sg.nus.iss.team11.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.team11.model.Role;
import sg.nus.iss.team11.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepo;
	
	@Override
	public List<Role> findAllRoles() {
		return roleRepo.findAll();
	}

	@Override
	public Role findRole(String roleId) {
		return roleRepo.findById(roleId).orElse(null);
	}

	@Override
	public Role createRole(Role role) {
		return roleRepo.saveAndFlush(role);
	}

	@Override
	public Role updateRole(Role role) {
		return roleRepo.saveAndFlush(role);
	}

	@Override
	public void removeRole(Role role) {
		roleRepo.delete(role);
	}

}
