package com.dnia.eaas.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dnia.eaas.exception.NotFoundException;
import com.dnia.eaas.model.UserOwner;
import com.dnia.eaas.repository.UserOwnerRepository;

@Component("accessManager")
public class AccessManager {
	
	@Autowired
	private UserOwnerRepository userRepository;
	
	private static final String ADMINISTRATOR = "ADMINISTRATOR";
	
	public boolean isOwner(Long id) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<UserOwner> result = userRepository.findByEmail(email);
		
		if(!result.isPresent()) throw new NotFoundException("There are not user with email = " + email);
		
		UserOwner user = result.get();
		
		if(user.getRole().name().equalsIgnoreCase(ADMINISTRATOR)) {
			if(user.getId() == id || user.getId() != id) {
			return true;
			}
		}
		
		boolean trial = user.getId() == id;
		
		return trial;
	}
	
	public boolean isConsumerOwner(Long id) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<UserOwner> result = userRepository.findByEmail(email);
		
		if(!result.isPresent()) throw new NotFoundException("There are not user with email = " + email);
		
		UserOwner user = result.get();
		
		//Request request = requestService.getById(id);
		
		//return user.getId() == request.getOwner().getId();
		return false;
		
	}

}
