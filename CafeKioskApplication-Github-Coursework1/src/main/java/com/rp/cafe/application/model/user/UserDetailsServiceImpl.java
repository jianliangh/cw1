package com.rp.cafe.application.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersCrudRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		Users users = userRepository.getUserByUsername(username); // check the username is existed or not
		
		if (users == null) {
			throw new UsernameNotFoundException("Could not find user");
		}
		
		return new MyUserDetails(users);
	}

}
