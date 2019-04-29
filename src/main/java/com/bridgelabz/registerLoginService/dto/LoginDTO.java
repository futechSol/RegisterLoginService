package com.bridgelabz.registerLoginService.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @ToString
public class LoginDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "Enter email address")
	@Email(message="Enter valid email address")
	private String email;
	//@Length(min = 8, max = 32, message = "Password must be min 6 and max 32 chars long")
	@NotEmpty(message = "Enter password ..!")
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", message = "Password must be min 8 and max 20 chars long, and should be alphanumeric with a capital letter")
	private String password;
}
