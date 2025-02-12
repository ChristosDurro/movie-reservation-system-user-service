package com.cdurro.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cdurro.dto.UserDTO;
import com.cdurro.model.User;
import com.cdurro.repository.UserRepo;

@Service
public class UserService {
	
	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(10);
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwtService;
	
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = repo.findAll();
		
		return ResponseEntity.ok(users);
	}

	public ResponseEntity<User> createUser(User user) {
	
		user.setPassword(bcrypt.encode(user.getPassword()));
		
		User userCreated = repo.save(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
	}

	public ResponseEntity<Map<String, Object>> verifyUser(User user) {
		
		Authentication authentication = authManager
						.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		if (authentication.isAuthenticated()) {
		
			User userFetched = repo.findByUsername(user.getUsername());
			
			if (userFetched == null)
				userFetched = repo.findByEmail(user.getUsername()); // here i put getUsername because the request will always be authenticated using the username wether i use the username or email to login
			
			String jwtToken = jwtService.generateToken(userFetched.getUsername());
			
			UserDTO userDTO = new UserDTO(
					userFetched.getId(),
					userFetched.getFirstName(),
					userFetched.getLastName(),
					userFetched.getUsername(),
					userFetched.getEmail()
			);

			Map<String, Object> response = new HashMap<>();
			
			response.put("token", jwtToken);
			response.put("user", userDTO);
			
			
			return ResponseEntity.ok(response);	
		}

		return ResponseEntity.ok(null);
	}

	public ResponseEntity<User> getUserByUsername(String username) {
		User user = repo.findByUsername(username);
		
		return ResponseEntity.ok(user);
	}
	
	public ResponseEntity<User> getUserByEmail(String email) {
		User user = repo.findByEmail(email);
		
		return ResponseEntity.ok(user);
	}

	public ResponseEntity<User> updateUser(Long id, UserDTO user) {
		
		User fetchedUser = repo.findById(id).orElse(null);
		
		System.out.println(user);
		System.out.println(fetchedUser);

		if (fetchedUser != null) {
			
			fetchedUser.setEmail(user.getEmail());
			fetchedUser.setFirstName(user.getFirstName());
			fetchedUser.setLastName(user.getLastName());
			fetchedUser.setUsername(user.getUsername());

			return ResponseEntity.ok(repo.save(fetchedUser));
		}

		return ResponseEntity.ok(null);
	}

	public User getUserById(Long id) {
		
		User user = repo.findById(id).orElse(null);
		
		return user;
	}

	public ResponseEntity<String> validateToken(String token) {
		System.out.println("token received: " + token);
		boolean isValid = jwtService.validateToken(token);
		
		if (isValid) {
	        return ResponseEntity.ok().body("Token is valid.");
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid or expired");
	}
}
