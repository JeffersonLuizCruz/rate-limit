package com.dnia.eaas.security;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dnia.eaas.constant.ExceptionConstants;
import com.dnia.eaas.exception.NotFoundException;
import com.dnia.eaas.model.UserOwner;
import com.dnia.eaas.repository.UserOwnerRepository;

@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired private UserOwnerRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserOwner> entity = userRepository.findByEmail(username);
		
		if(!entity.isPresent()) throw new NotFoundException(ExceptionConstants.NOT_FOUND_EXCEPTION + " " + username);
		
		UserOwner user = entity.get();
		
		List<GrantedAuthority> grantedAuthority = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

		return User
				.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.authorities(grantedAuthority)
				.build();
	}

}
