package com.bridgelabz.registerLoginService.util;

import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public class Response implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int statusCode;
	private String statusMessage;
    
	/**
	 * default constructor
	 */
	public Response() {}
	/**
	 * parameterized constructor
	 * @param statusCode
	 * @param statusMessage
	 */
	public Response(int statusCode, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	@Override
	public String toString() {
		return "Response [statusCode=" + statusCode + ", statusMessage=" + statusMessage + "]";
	}
}
