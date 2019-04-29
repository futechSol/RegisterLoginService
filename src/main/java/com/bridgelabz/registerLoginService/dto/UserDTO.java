package com.bridgelabz.registerLoginService.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @ToString
public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "Enter firstName")
	@Length(min = 3, max = 32, message = "firstName must be of min 3 to 32 characters long")
	private String firstName;
	@NotEmpty(message = "Enter lastName")
	@Length(min = 3, max = 32, message = "lastName must be of min 3 to 32 characters long")
	private String lastName;
	@NotEmpty(message = "Enter mobile number")
	@Pattern(regexp="[7-9]{1}[0-9]{9}",message = "Enter 10-digit mobile number")//^[7-9][0-9]{9}$
	private String phoneNumber;
	@NotEmpty(message = "Enter email address")
	@Email
	//@Pattern(regexp = "^[\\\\w-\\\\+]+(\\\\.[\\\\w]+)*@[\\\\w-]+(\\\\.[\\\\w]+)*(\\\\.[a-z]{2,})$", message="Enter valid email address..!")
	private String email;
	@NotEmpty(message = "Enter password ...!")
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", message = "Password must be min 8 and max 20 chars long")
	private String password;
}
