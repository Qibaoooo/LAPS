package sg.nus.iss.team11.controller.service;

import java.util.List;

import sg.nus.iss.team11.model.LAPSUser;

public interface UserService {
	List<LAPSUser> findAllUsers();

	LAPSUser findUser(Integer userId);

	LAPSUser findUserByUsername(String username);

	LAPSUser createUser(LAPSUser User);

	LAPSUser updateUser(LAPSUser User);

	void removeUser(LAPSUser User);

	List<LAPSUser> findSubordinates(int userId);

	LAPSUser authenticateUser(String username, String password);

	List<Integer> findAllManagerId();

	List<LAPSUser> findAllManager();
}
