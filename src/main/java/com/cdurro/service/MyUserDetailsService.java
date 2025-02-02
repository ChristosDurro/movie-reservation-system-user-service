package com.cdurro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cdurro.model.User;
import com.cdurro.model.UserPrincipal;
import com.cdurro.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepo repo;
	
	
	// i put identifier instead of username as param name cause the user will either pass a username or email
	@Override
	public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
		
		User user = repo.findByUsername(identifier);
		
		if (user == null)
			user = repo.findByEmail(identifier);

		if (user == null)
			throw new UsernameNotFoundException("User not found!");
		
		return new UserPrincipal(user);
	}

}
