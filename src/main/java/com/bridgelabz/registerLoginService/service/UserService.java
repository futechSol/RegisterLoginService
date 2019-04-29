package com.bridgelabz.registerLoginService.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.registerLoginService.dto.LoginDTO;
import com.bridgelabz.registerLoginService.dto.UserDTO;
import com.bridgelabz.registerLoginService.util.Response;


public interface UserService {
	   Response create(UserDTO userDTO);
	   Response  verifyUser(String token);
	   Response login(LoginDTO loginDTO);
	   Response passwordRecovery(String email);
	   Response resetPassword(String newPassword, String token);
	   Response uploadProfilePic(MultipartFile  multipartFile, String token);
	   Response getProfilePic(String token);
	   //boolean isDuplicateUserByEmail(String email);
}
