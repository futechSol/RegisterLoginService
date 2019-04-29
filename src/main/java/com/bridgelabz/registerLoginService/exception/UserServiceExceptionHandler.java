package com.bridgelabz.registerLoginService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bridgelabz.registerLoginService.util.Response;
import com.bridgelabz.registerLoginService.util.ResponseService;

@RestControllerAdvice
public class UserServiceExceptionHandler {
	/*
	 * @ExceptionHandler(Exception.class) public ResponseEntity<Response>
	 * GlobalExceptionHandler(Exception e) { Response statusInfo =
	 * ResponseInfo.getResponse( -200, "Internal Error"); return new
	 * ResponseEntity<>(statusInfo,HttpStatus.OK); }
	 */
	@ExceptionHandler(TokenException.class)
	public ResponseEntity<Response> tokenExceptionHandler(TokenException e) {
		Response response = ResponseService.getResponse(e.getErrorCode(), e.getMessage(), null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> userExceptionHandler(UserException e) {
		Response response = ResponseService.getResponse(e.getErrorCode(), e.getMessage(), null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@ExceptionHandler(BindingException.class)
	public ResponseEntity<Response> BindingExceptionHandler(BindingException e) {
		Response response = ResponseService.getResponse(e.getErrorCode(), e.getMessage(), null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
