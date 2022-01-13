package com.dnia.eaas.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dnia.eaas.model.UserOwner;
import com.dnia.eaas.model.enums.Role;
import com.dnia.eaas.service.UserOwnerService;


@SpringBootTest
public class UserOwnerTest {
	
	@Autowired private UserOwnerService userOwnerService;
	@Autowired private UserOwnerRepository userOwnerRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
	@Test
	public void saveTest() {
		String hashPassword = passwordEncoder.encode("12345678");
		UserOwner user = new UserOwner(null, "hugo", "hugo@gmail.com", hashPassword, true, Role.ADMINISTRATOR);
		UserOwner createdUser = userOwnerRepository.save(user);
		
		assertThat(createdUser.getId()).isEqualTo(3L);
		
	}
	
	@Test
	public void findByIdTest() {
		UserOwner result = userOwnerService.findById(4L);
		
		assertThat(result.getPassword()).isEqualTo("$2a$10$BB6A57VJslsTEiQnGxKv0O9DSpi8/exvRTRJIH3tfCIf4HHe6KS0.");
	}
	
	@Test
	public void findAllTest() {
		List<UserOwner> users = userOwnerService.findAll();
		
		assertThat(users.size()).isEqualTo(4);
	}
	
	@Test
	public void updateTest() {
		String hashPassword = passwordEncoder.encode("123456");
		UserOwner user = new UserOwner(20005L, "Jefferson", "jeff@gmail.com", hashPassword, true, Role.ADMINISTRATOR);
		UserOwner updatedUser = userOwnerService.update(user);
		
		assertThat(updatedUser.getName()).isEqualTo("Jefferson");
	}
	
	@Test
	public void updateRoleTest() {
		UserOwner result = userOwnerService.findById(4L);
		int affectedRows = userOwnerService.updateRole(result);
		
		assertThat(affectedRows).isEqualTo(1);
	}
	
	@Test
	public void loginTest() {
		UserOwner user = new UserOwner(null, "Jefferson", "jeff@gmail.com", "123456", true, Role.ADMINISTRATOR);
	}

}
