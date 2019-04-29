package com.bridgelabz.registerLoginService.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class User implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	@Setter(AccessLevel.PRIVATE)
	private long id;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@NotEmpty
	private String phoneNumber;
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
	private String profilePic;
	private boolean isVerified;
	private LocalDateTime registrationDate;
	private LocalDateTime modifiedDate;
	
}
