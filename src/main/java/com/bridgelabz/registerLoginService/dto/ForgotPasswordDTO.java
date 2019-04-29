package com.bridgelabz.registerLoginService.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @ToString
public class ForgotPasswordDTO implements Serializable{
private static final long serialVersionUID = 1L;
	@NotEmpty(message = "Enter email address")
	@Email
	private String email;
}