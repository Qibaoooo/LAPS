package sg.nus.iss.team11.controller.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepo;

	@Autowired
	PasswordEncoder encoder;

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
		return userRepo.findLAPSUserByUsername(username).orElseThrow();
	}

	@Override
	public List<LAPSUser> findSubordinates(int userId) {
		return userRepo.findSubordinates(userId);
	}

	@Override
	public LAPSUser authenticateUser(String username, String password) {
		Optional<LAPSUser> user = userRepo.findLAPSUserByUsername(username);
		if (user.isEmpty()) {
			return null;
		}

		if (this.encoder.matches(password, user.get().getPassword())) {
			return user.get();
		}
		return null;
	}

	@Override
	public List<Integer> findAllManagerId() {
		return userRepo.findAllManagerId();
	}

	@Override
	public List<String> findAllManagerName(){
		return userRepo.findAllManagerName();
	}
	
	@Override
	public int findMaxId() {
		return userRepo.findMaxId();
	}

	@Override
	public void incrementCompensationLeaveBy(double days, int userId) {
		LAPSUser user = this.findUser(userId);
		user.setCompensationLeaveEntitlement(user.getCompensationLeaveEntitlement() + days);
		userRepo.saveAndFlush(user);
	}
	
	@Override
	public List<String> findAllUserName(){
		return userRepo.findAllUserName();
	}
}
