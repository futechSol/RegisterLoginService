package com.bridgelabz.registerLoginService.dto;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Setter @Getter @ToString
public class ResetPasswordDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Enter password ...!")
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", message = "Password must be min 8 and max 20 chars long")
	private String password;
}