package sg.nus.iss.team11.controller.service;

import java.util.List;

import sg.nus.iss.team11.model.User;

public interface UserService {
	List<User> findAllUsers();

	User findRole(int userId);

	User createRole(User user);

	User updateRole(User user);

	void removeRole(User user);
	
	List<User> findSubordinates(int managerId);

}
