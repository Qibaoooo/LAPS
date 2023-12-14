package sg.nus.iss.team11.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.team11.model.User;
import sg.nus.iss.team11.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepo;
	
	@Override
	public List<User> findAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User findUser(Integer userId) {
		return userRepo.findById(userId).orElse(null);
	}

	@Override
	public User createUser(User user) {
		return userRepo.saveAndFlush(user);
	}

	@Override
	public User updateUser(User user) {
		return userRepo.saveAndFlush(user);
	}

	@Override
	public void removeUser(User user) {
		userRepo.delete(user);
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepo.findUserByUsername(username);
	}

	@Override
	public List<User> findSubordinates(int userId) {
		return userRepo.findSubordinates(userId);
	}
	
	@Override
	public User authenticateUser(String username, String password) {
		return userRepo.findUserByNamePwd(username, password);
	}
	
	@Override
	public List<Integer> findAllManagerId(){
		return userRepo.findAllManagerId();
	}
	
	@Override
	public int findMaxId() {
		return userRepo.findMaxId();
	}
}
