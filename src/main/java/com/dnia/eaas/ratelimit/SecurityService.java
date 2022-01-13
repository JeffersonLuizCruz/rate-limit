package com.dnia.eaas.ratelimit;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dnia.eaas.exception.NotFoundException;
import com.dnia.eaas.model.UserOwner;
import com.dnia.eaas.repository.UserOwnerRepository;

@Service
public class SecurityService {

	@Autowired private UserOwnerRepository userRepository;
	public String username() {
		Authentication name = SecurityContextHolder.getContext().getAuthentication();

		if(name.getName() == null) {
			return "anonymousUser";
		}
		Optional<UserOwner> entity = userRepository.findByEmail(name.getName());
		entity.orElseThrow(() -> new NotFoundException("Email não registrado"));
		
		if(entity.get().getRole().name() == "anonymousUser") {
			return null;
		}
		return entity.get().getRole().name();
	}

}