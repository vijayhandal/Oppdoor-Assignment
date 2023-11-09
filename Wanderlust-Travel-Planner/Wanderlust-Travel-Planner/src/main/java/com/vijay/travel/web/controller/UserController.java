package com.vijay.travel.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vijay.travel.exception.HttpMethodNotImplementedException;
import com.vijay.travel.model.User;
import com.vijay.travel.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {

		this.userService = userService;
	}

	@PutMapping
	ResponseEntity<User> editUser() {

		throw new HttpMethodNotImplementedException("Edit user http method has not been implemented yet");
	}

	@DeleteMapping
	ResponseEntity<User> deleteUser(Authentication authentication) {

		String username = authentication.getName();
		User deletedUser = userService.deleteUser(username);

		return ResponseEntity.ok(deletedUser);
	}
}
