package sg.nus.iss.team11.controller.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.nus.iss.team11.model.User;

public interface UserService {
	  List<User> findAllUsers();

	  User findUser(Integer userId);
	  
	  User findUserByUsername(String username);

	  User createUser(User User);

	  User updateUser(User User);

	  void removeUser(User User);


}
