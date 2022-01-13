package com.dnia.eaas.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dnia.eaas.constant.ExceptionConstants;
import com.dnia.eaas.exception.NotFoundException;
import com.dnia.eaas.model.UserOwner;
import com.dnia.eaas.repository.UserOwnerRepository;
import com.dnia.eaas.service.UserOwnerService;

@Service
public class UserOwnerServiceImpl implements UserOwnerService{

	@Autowired private UserOwnerRepository userOwnerRepository;
	@Autowired private PasswordEncoder passwordEncoder;

	@Override
	public UserOwner findById(Long id) {
		UserOwner entity = verifyExistUserOwner(id);
		return entity;
	}

	@Override
	public UserOwner save(UserOwner user) {
		String hash = passwordEncoder.encode(user.getPassword());
		user.setActive(true);
		user.setPassword(hash);

		UserOwner createUserOwner = userOwnerRepository.save(user);

		return createUserOwner;
	}
	
	@Override
	public UserOwner update(UserOwner user) {
		String hash = passwordEncoder.encode(user.getPassword());
			user.setActive(true);
			user.setPassword(hash);
			UserOwner userRole = findById(user.getId());
			
			if(user.getRole() == null) {
				user.setRole(userRole.getRole());
			}
			UserOwner updateUser = userOwnerRepository.save(user);

			return updateUser;
	}

	@Override
	public List<UserOwner> findAll() {
		List<UserOwner> entitys = userOwnerRepository.findAll();
		return entitys;
	}
	
	@Override
	public void delete(Long id) {
		UserOwner entity = verifyExistUserOwner(id);
		userOwnerRepository.delete(entity);
	}
	
	@Override
	public int updateRole(UserOwner userOwner) {
		int roleEntity = userOwnerRepository.updateRole(userOwner.getId(), userOwner.getRole());
		return roleEntity;
	}

	private UserOwner verifyExistUserOwner(Long id) {
		Optional<UserOwner> entity = userOwnerRepository.findById(id);

		if (!entity.isPresent())
			throw new NotFoundException(ExceptionConstants.NOT_FOUND_EXCEPTION + " " + id);

		return entity.get();
	}
	
}
