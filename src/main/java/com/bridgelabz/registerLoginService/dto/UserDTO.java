package com.bridgelabz.registerLoginService.dto;

import javax.validation.constraints.NotEmpty;

public class UserDTO {
	private String firstName;  
	@NotEmpty
	private String lastName;
	@NotEmpty
	private String phoneNumber;
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
}
