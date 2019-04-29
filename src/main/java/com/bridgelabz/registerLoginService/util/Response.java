package com.bridgelabz.registerLoginService.util;

import java.io.Serializable;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
public class Response implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int statusCode;
	private String statusMessage;
	private String token;
	
	public Response () {}
	
	public Response(int statusCode, String statusMessage, String token) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.token = token;
	}
}
