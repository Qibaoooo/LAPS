package sg.nus.iss.team11.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepo;
	
	@Override
	public List<LAPSUser> findAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public LAPSUser findUser(Integer userId) {
		return userRepo.findById(userId).orElse(null);
	}

	@Override
	public LAPSUser createUser(LAPSUser user) {
		return userRepo.saveAndFlush(user);
	}

	@Override
	public LAPSUser updateUser(LAPSUser user) {
		return userRepo.saveAndFlush(user);
	}

	@Override
	public void removeUser(LAPSUser user) {
		userRepo.delete(user);
	}

	@Override
	public LAPSUser findUserByUsername(String username) {
		return userRepo.findLAPSUserByUsername(username);
	}

	@Override
	public List<LAPSUser> findSubordinates(int userId) {
		return userRepo.findSubordinates(userId);
	}
	
	@Override
	public LAPSUser authenticateUser(String username, String password) {
		return userRepo.findLAPSUserByNamePwd(username, password);
	}

}
