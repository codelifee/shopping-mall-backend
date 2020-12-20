package com.exercisecoach.exercisecoach.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.exercisecoach.exercisecoach.service.UserService;

@RestController
//@CrossOrigin(origin="")
public class UserController {

	private UserService userService;
	
	@GetMapping("/users/{userId}/userEmail")
	public String getUserEmail(@PathVariable Integer userId) {
		return userService.getUserEmail(userId);
	}
}
