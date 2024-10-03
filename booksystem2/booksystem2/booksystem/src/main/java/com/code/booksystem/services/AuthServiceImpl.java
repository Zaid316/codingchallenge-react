package com.code.booksystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.code.booksystem.dto.SignupRequest;
import com.code.booksystem.Entity.User;
import com.code.booksystem.Repository.UserRepository;
import com.code.booksystem.dto.UserDTO;

import com.code.booksystem.enums.UserRole;


@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
    private  UserRepository userRepository;

 

	@Override
	public UserDTO createUser(SignupRequest signupRequest) {
	    User user = new User();
	    user.setName(signupRequest.getName());
	    user.setEmail(signupRequest.getEmail());
	    user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));

	    // Set default role if none is provided
	    if (signupRequest.getUserRole() == null) {
	        user.setUserRole(UserRole.USER); // Default role to USER
	    } else {
	        user.setUserRole(signupRequest.getUserRole());
	    }

	    User createdUser = userRepository.save(user);

	    // Convert User to UserDTO to hide unnecessary fields
	    UserDTO userDTO = new UserDTO();
	    userDTO.setId(createdUser.getId());
	    userDTO.setName(createdUser.getName());
	    userDTO.setEmail(createdUser.getEmail());
	    userDTO.setUserRole(createdUser.getUserRole().name());

	    return userDTO;  // Rewritten line without hidden characters
	}

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
