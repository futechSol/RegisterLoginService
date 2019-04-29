package com.bridgelabz.registerLoginService.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.registerLoginService.dto.LoginDTO;
import com.bridgelabz.registerLoginService.dto.UserDTO;
import com.bridgelabz.registerLoginService.exception.UserException;
import com.bridgelabz.registerLoginService.model.User;
import com.bridgelabz.registerLoginService.repository.UserRepository;
import com.bridgelabz.registerLoginService.util.Response;
import com.bridgelabz.registerLoginService.util.ResponseService;
import com.bridgelabz.registerLoginService.util.TokenGenerator;
import com.bridgelabz.registerLoginService.util.Utility;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private Environment environment;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private MailService mailService;
	@Autowired
	private RabbitMQService rabbitMQService;
	@Autowired
	private TokenGenerator tokenGenerator;
	@Autowired
	private AmazonService amazonService;

	public boolean isDuplicateUserByEmail(String email) {
		if (userRepository.findByEmail(email).isPresent())
			return true;
		else
			return false;
	}

	@Override
	public Response create(UserDTO userDTO) {
		if (isDuplicateUserByEmail(userDTO.getEmail()))
			return ResponseService.getResponse(Integer.parseInt(environment.getProperty("status.user.errorCode")),
					environment.getProperty("status.user.duplicateEmail"), null);
		// map the UserDTO to User
		User user = modelMapper.map(userDTO, User.class);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRegistrationDate(LocalDateTime.now());
		user.setModifiedDate(LocalDateTime.now());
		user.setVerified(false);
		try {
			user = userRepository.save(user);
		} catch (Exception e) {
			throw new UserException(Integer.parseInt(environment.getProperty("status.user.errorCode")),
					environment.getProperty("status.user.saveError"));
		}
		String userActivationLink = Utility.getLocalHostIPaddress() + ":" + environment.getProperty("server.port")
		+ "/users/activation/";
		userActivationLink = userActivationLink + tokenGenerator.generateUserToken(user.getId());
		SimpleMailMessage mail = mailService.getMailMessageObject(user.getEmail(), "User registration verification",
				userActivationLink);
		rabbitMQService.publishUserMail(mail); // publish message to the queue in rabbitmq server
		Response response = ResponseService.getResponse(
				Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("status.user.register.success"), null);
		return response;
	}

	@Override
	public Response verifyUser(String token) {
		Response response = null;
		long id = tokenGenerator.retrieveIdFromToken(token);
		User user = userRepository.findById(id).get();
		if (user == null)// check id is valid or not
			throw new UserException(Integer.parseInt(environment.getProperty("status.user.errorCode")),environment.getProperty("status.user.invalidUser"));
		if(!user.isVerified()) { //check for already verified or not
			user.setVerified(true);// set verification to true
			user.setModifiedDate(LocalDateTime.now());
			user = userRepository.save(user);// update to db
			if (user == null)
				throw new UserException(Integer.parseInt(environment.getProperty("status.user.errorCode")),
						environment.getProperty("status.user.saveError"));
			response = ResponseService.getResponse(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("status.user.verification.success"), null);
		}
		else
			response = ResponseService.getResponse(Integer.parseInt(environment.getProperty("status.success.code")),"User is already verified", null);
		return response;
	}

	@Override
	public Response login(LoginDTO loginDTO) {
		Response response = null;
		Optional<User> user = userRepository.findByEmail(loginDTO.getEmail());
		if (user.isPresent() && bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
			if (user.get().isVerified()) {
				String token = tokenGenerator.generateUserToken(user.get().getId());
				response = ResponseService.getResponse(Integer.parseInt(environment.getProperty("status.success.code")),
						environment.getProperty("status.user.login.success"),token);
			}
			else // user is not verified yet
				response = ResponseService.getResponse(Integer.parseInt(environment.getProperty("status.user.login.errorCode")), environment.getProperty("status.user.login.unVerifiedUser"), null);
		}
		else
			response = ResponseService.getResponse(Integer.parseInt(environment.getProperty("status.user.errorCode")), environment.getProperty("status.user.login.invalidUser"), null);
		return response;
	}

	@Override
	public Response passwordRecovery(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(!user.isPresent())
			return ResponseService.getResponse(Integer.parseInt(environment.getProperty("status.user.errorCode")), environment.getProperty("status.user.forgotPassword.invalidEmail"), null);
		String passwordResetLink = Utility.getLocalHostIPaddress() + ":" +environment.getProperty("server.port")+"/users/resetpassword/";
		passwordResetLink = passwordResetLink + tokenGenerator.generateUserToken(user.get().getId());
		SimpleMailMessage mail = mailService.getMailMessageObject(user.get().getEmail(), "Password Reset Link", passwordResetLink);
		rabbitMQService.publishUserMail(mail); //publish message to the queue in rabbitmq server
		return ResponseService.getResponse(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("status.user.forgotPassword.success"), null);
	}

	@Override
	public Response resetPassword(String newPassword, String token) {
		Response response = null;
		long id = tokenGenerator.retrieveIdFromToken(token);
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent())
			return ResponseService.getResponse(Integer.parseInt(environment.getProperty("status.user.errorCode")), environment.getProperty("status.user.invalidUser"), null);
		user.get().setPassword(bCryptPasswordEncoder.encode(newPassword));
		user.get().setModifiedDate(LocalDateTime.now());
		if(userRepository.save(user.get()) == null)
			response = ResponseService.getResponse(Integer.parseInt(environment.getProperty("status.user.errorCode")),
					environment.getProperty("status.user.saveError"), null);
		else
			response =ResponseService.getResponse(Integer.parseInt(environment.getProperty("status.success.code")),environment.getProperty("status.resetPassword.success"), null);
		return response;
	}

	@Override
	public Response uploadProfilePic(MultipartFile multipartFile, String token) {
		Long userId = tokenGenerator.retrieveIdFromToken(token);
		return amazonService.uploadFile(multipartFile, userId);
	}

	@Override
	public Response getProfilePic(String token) {
		Long userId = tokenGenerator.retrieveIdFromToken(token);
		String str = amazonService.getProfilePicFromS3Bucket(userId);
		return ResponseService.getResponse(Integer.parseInt(environment.getProperty("status.success.code")), str, null);
	}
}
