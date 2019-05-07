package com.bridgelabz.registerLoginService.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.registerLoginService.dto.ForgotPasswordDTO;
import com.bridgelabz.registerLoginService.dto.LoginDTO;
import com.bridgelabz.registerLoginService.dto.ResetPasswordDTO;
import com.bridgelabz.registerLoginService.dto.UserDTO;
import com.bridgelabz.registerLoginService.service.UserService;
import com.bridgelabz.registerLoginService.util.Response;
import com.bridgelabz.registerLoginService.util.ResponseService;

@RestController
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private Environment environment;
	@Autowired
	private UserService userService;
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Registers a new User
	 * 
	 * @param userDTO
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
		logger.info("UserDTO : " + userDTO);
		logger.trace("User Registration");
		Response response = null;
		response = checkBindingResultError(bindingResult);
		if (response != null)
			return new ResponseEntity<>(response, HttpStatus.OK);
		else {
			response = userService.create(userDTO);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	/**
	 * verifies the user through email notification
	 * 
	 * @param token user token
	 * @return response
	 */
	@GetMapping("/activation/{token}")
	public ResponseEntity<Response> verify(@PathVariable String token) {
		logger.info("token : " + token);
		logger.trace("User Verification");
		Response response = userService.verifyUser(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * logins the user to the application
	 * 
	 * @param loginDTO      login credentials instance
	 * @param bindingResult errors while binding the parameters
	 * @return response
	 */
	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
		logger.info("UserDTO : " + loginDTO);
		logger.trace("User Login");
		Response response = null;
		response = checkBindingResultError(bindingResult);
		if (response != null)
			return new ResponseEntity<>(response, HttpStatus.OK);
		else {
			response = userService.login(loginDTO);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> passwordRecovery(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO,
			BindingResult bindingResult) {
		logger.info("user email : " + forgotPasswordDTO.getEmail());
		logger.trace("User Forget Password");
		Response response = null;
		response = checkBindingResultError(bindingResult);
		if (response != null)
			return new ResponseEntity<>(response, HttpStatus.OK);
		else {
			response = userService.passwordRecovery(forgotPasswordDTO.getEmail());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PutMapping("/resetpassword/{token}")
	public ResponseEntity<Response> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO,
			BindingResult bindingResult, @PathVariable String token) {
		logger.info("user password : " + resetPasswordDTO.getPassword());
		logger.trace("reset user password");
		Response response = null;
		response = checkBindingResultError(bindingResult);
		if (response != null)
			return new ResponseEntity<>(response, HttpStatus.OK);
		else {
			response = userService.resetPassword(resetPasswordDTO.getPassword(), token);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PostMapping("/profilepic")
	public ResponseEntity<Response> uploadProfilePicture(@RequestPart("multipartFile") MultipartFile multipartFile,
			@RequestHeader String token) {
		Response response = userService.uploadProfilePic(multipartFile, token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/profilepic")
	public ResponseEntity<Response> getProfilePicture(@RequestHeader String token) {
		return new ResponseEntity<>(userService.getProfilePic(token), HttpStatus.OK);
	}

	/**
	 * Binding Error Handler
	 * 
	 * @param bindingResult
	 */
	private Response checkBindingResultError(BindingResult bindingResult) {
		Response response = null;
		if (bindingResult.hasErrors()) {
			logger.error("Error while binding user details");
			// String statusMessge =
			// environment.getProperty("status.bindingResult.errorMessage");
			int statusCode = Integer.parseInt(environment.getProperty("status.bindingResult.errorCode"));
			List<Object> errors = new ArrayList<>();
			bindingResult.getFieldErrors().stream().forEach(e -> errors.add(e.getDefaultMessage()));
			response = ResponseService.getResponse(statusCode, errors.toString(), null);
		}
		return response;
	}

	// ----------------------- Accessing Note APIs  //---------------------------------------------

	@GetMapping("/notes")
	public ResponseEntity<Object> notes(@RequestHeader String token) {
		
		// List<Object> notes = restTemplate.getForObject(url, List.class);
		//Object obj = restTemplate.getForObject(url, List.class);
		
		String url = "http://Note-Service/notes/";
		RequestEntity<Void> request = null;
		try {
			request = RequestEntity
					.get(new URI(url))
					.accept(MediaType.APPLICATION_JSON)
					.header("token", token)
					.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		ResponseEntity<Object> response = restTemplate.exchange(request, Object.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

















