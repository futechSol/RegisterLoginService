package com.bridgelabz.registerLoginService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	public ResponseEntity<Object> create(){
		
		
		return null;
	}
	private void checkBindingResultError(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			logger.error("Error while binding user details");
			//response.set
			/*
			 * String statusMessge =
			 * environment.getProperty("status.bindingResult.invalidData"); int statusCode =
			 * Integer.parseInt(environment.getProperty("status.bindingResult.errorCode"));
			 */
//			Response response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.success.code")),
//					environment.getProperty("status.forgotPassword.success"));
//			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
	}
}
