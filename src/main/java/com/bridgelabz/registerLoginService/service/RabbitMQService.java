package com.bridgelabz.registerLoginService.service;

import org.springframework.mail.SimpleMailMessage;

public interface RabbitMQService {
	void publishUserMail(SimpleMailMessage mail);
	void recieveUserMail(SimpleMailMessage mail);
}
