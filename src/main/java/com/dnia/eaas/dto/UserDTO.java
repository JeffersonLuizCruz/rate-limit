package com.dnia.eaas.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dnia.eaas.model.UserOwner;
import com.dnia.eaas.model.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {

	@NotBlank(message = "Name required")
	private String name;
	
	@Email(message = "Invalid email address")
	private String email;
	
	@Size(min = 7, max = 99, message = "Password must be between 7 and 99")
	private String password;
	
	@NotNull(message = "Role required")
	private Role role;
	
	public UserOwner transformToUser() {
		UserOwner user = new UserOwner(null, this.name, this.email, this.password, null, this.role);
	    return user;
	}
}
