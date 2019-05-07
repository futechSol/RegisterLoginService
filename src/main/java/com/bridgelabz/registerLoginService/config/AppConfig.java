package com.bridgelabz.registerLoginService.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class AppConfig {
	@Value("${spring.rabbitmq.template.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.user.queue}")
	private String userQueue;
	@Value("${spring.rabbitmq.user.routingKey}")
	private String userRoutingKey;

	/**
	 * swagger bean
	 * @return
	 */
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.bridgelabz.registerLoginService")).build();
	}

	/**
	 * ModelMapper to map the DTO to actual model
	 * @return instance of ModelMapper
	 */
	@Bean
	public ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

	/**
	 * BCrypt instance to encode the user password
	 * @return BCrypt instance 
	 */
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	//---------------------------- amqp config ------------------------------------------
	@Bean
	Exchange exchage() {
		return new DirectExchange(exchange);
	}

	@Bean(name = "userQueue")
	Queue userQueue() {
		return new Queue(userQueue, false);
	}

	@Bean
	Binding userQueueBinding(Queue userQueue, DirectExchange exchange) {
		return BindingBuilder.bind(userQueue).to(exchange).with(userRoutingKey);
	}
	
	//----------------------------------RestTemplate Bean-------------------------------------------------------
	/**
	 * restTemplate bean to call NoteMicro-service
	 * @return
	 */
	@Bean
	@LoadBalanced		// Load balance between service instances running at different ports.
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
