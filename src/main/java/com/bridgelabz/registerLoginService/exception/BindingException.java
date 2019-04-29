package com.bridgelabz.registerLoginService.exception;

public class BindingException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int errorCode;

	public BindingException(int errorCode, String msg) {
		super(msg);
		this.errorCode=errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
