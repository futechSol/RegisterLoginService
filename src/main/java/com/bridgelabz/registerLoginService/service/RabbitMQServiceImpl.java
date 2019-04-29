package com.bridgelabz.registerLoginService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQServiceImpl implements RabbitMQService {
	@Autowired
	private AmqpTemplate amqpTemplate;
	@Value("${spring.rabbitmq.template.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.user.routingKey}")
	private String userRoutingKey;
	@Autowired
	private MailService mailService;
	private static final Logger logger = LoggerFactory.getLogger(RabbitMQServiceImpl.class);
	
	@Override
	public void publishUserMail(SimpleMailMessage mail) {
		logger.info("published message = " + mail);
		logger.info("exchange = "+exchange);
		logger.info("routingKey = "+userRoutingKey);
		amqpTemplate.convertAndSend(exchange, userRoutingKey, mail);
	}

	@Override
	@RabbitListener(queues="${spring.rabbitmq.user.queue}")
	public void recieveUserMail(SimpleMailMessage mail) {
		logger.info("consumed message = "+ mail.toString());
		mailService.sendEmail(mail);
	}
}
