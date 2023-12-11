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
	public List<User> findAllUsers(){
		return userRepo.findAll();
	}

	@Override
	public User findRole(int userId) {
		return userRepo.findById(userId).orElse(null);
	}

	@Override
	public User createRole(User user) {
		return userRepo.saveAndFlush(user);
	}

	@Override
	public User updateRole(User user) {
		return userRepo.saveAndFlush(user);
	}

	@Override
	public void removeRole(User user) {
		userRepo.delete(user);
	}
	
	@Override
	public List<User> findSubordinates(int managerId){
		return userRepo.findSubordinates(managerId);
	}

}
