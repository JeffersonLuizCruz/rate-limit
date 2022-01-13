package com.dnia.eaas.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnia.eaas.dto.UserDTO;
import com.dnia.eaas.dto.UserLoginDTO;
import com.dnia.eaas.dto.UserLoginResponseDTO;
import com.dnia.eaas.dto.UserUpdateDTO;
import com.dnia.eaas.model.UserOwner;
import com.dnia.eaas.security.JwtManager;
import com.dnia.eaas.service.UserOwnerService;

@RestController
@RequestMapping(value = "/users")
public class UserOwnerController {

	@Autowired private AuthenticationManager authManager;
	@Autowired private JwtManager jwtManager;
	@Autowired private UserOwnerService userService;

	@PostMapping("/login")
	public ResponseEntity<UserLoginResponseDTO> login(@RequestBody @Valid UserLoginDTO user) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(),
				user.getPassword());
		Authentication auth = authManager.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(auth);

		User userSpring = (User) auth.getPrincipal();

		String email = userSpring.getUsername();
		List<String> roles = userSpring.getAuthorities().stream().map(authority -> authority.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(jwtManager.createToken(email, roles));
	}

	@PreAuthorize(value = "@accessManager.isOwner(#id)")
	@PutMapping("/{id}")
	public ResponseEntity<UserOwner> update(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateDTO userDTO) {
		UserOwner user = userDTO.transformToUser();
		user.setId(id);
		
		UserOwner updatedUser = userService.update(user);
		
		return ResponseEntity.ok(updatedUser);

	}
	
	@Secured({"ROLE_ADMINISTRATOR"})
	@PostMapping
	public ResponseEntity<UserOwner> save(@RequestBody @Valid UserDTO userDTO) {
		UserOwner userToSave = userDTO.transformToUser();
		UserOwner createdUser = userService.save(userToSave);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserOwner> findById(@RequestHeader(value = "X-api-key") @PathVariable("id") Long id) {	
		UserOwner entity = userService.findById(id);
		
		return ResponseEntity.ok(entity);
	}
	
}
