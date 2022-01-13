package com.dnia.eaas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dnia.eaas.model.UserOwner;
import com.dnia.eaas.model.enums.Role;

@Repository("userOwnerRepository")
public interface UserOwnerRepository extends JpaRepository<UserOwner, Long>{
	Optional<UserOwner> findByEmail(String email);
	Optional<Role> findByRole(Role role);
	
	@Query("SELECT u FROM owner u WHERE email = ?1 AND password = ?2")
	Optional<UserOwner> login(String email, String password);
	
	@Transactional(readOnly = false)
	@Modifying
	@Query("UPDATE owner SET role = ?2 WHERE id = ?1")
	public int updateRole(Long id, Role role);
}
