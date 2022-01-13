package com.dnia.eaas.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.dnia.eaas.model.UserOwner;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserUpdateDTO {
	
	@NotBlank(message = "Name required")
	private String name;
	
	@Email(message = "Invalid email address")
	private String email;
	
	@Size(min = 7, max = 99, message = "Password must be between 7 and 99")
	private String password;

	public UserOwner transformToUser() {
		UserOwner user = new UserOwner(null, this.name, this.email, this.password, null, null);
	    return user;
	}
}
