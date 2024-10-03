package com.code.booksystem.services;

import com.code.booksystem.dto.SignupRequest;
import com.code.booksystem.dto.UserDTO;



public interface AuthService {
	
	UserDTO createUser(SignupRequest signupRequest);
	
	boolean hasCustomerWithEmail(String Email);

}