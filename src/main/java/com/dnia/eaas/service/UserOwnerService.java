package com.dnia.eaas.service;

import java.util.List;

import com.dnia.eaas.model.UserOwner;

public interface UserOwnerService {
	
	UserOwner findById(Long id);
	UserOwner save(UserOwner userOwner);
	UserOwner update(UserOwner userOwner);
	List<UserOwner> findAll();
	void delete(Long id);
	int updateRole(UserOwner userOwner);
}
