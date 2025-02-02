package com.cdurro.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cdurro.dto.UserDTO;
import com.cdurro.model.User;
import com.cdurro.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin (origins = "http://localhost:5173")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		return userService.getUsers();
	}
	
	@GetMapping("/users/id/{id}")
	public User getUserById(@PathVariable Long id) {
		
		return userService.getUserById(id);
	}
	
	@GetMapping("/users/{identifier}")
	public ResponseEntity<User> getUser(@PathVariable String identifier) { 
		User user = userService.getUserByUsername(identifier).getBody();
		
		if (user == null) {
			user = userService.getUserByEmail(identifier).getBody();
		}
		
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		
		return userService.createUser(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) {
		
		return userService.verifyUser(user);
	}
	
	@PutMapping("/users/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
		
		return userService.updateUser(id, user);
	}
	
}
