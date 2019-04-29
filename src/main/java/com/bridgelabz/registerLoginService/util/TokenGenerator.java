package com.bridgelabz.registerLoginService.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.bridgelabz.registerLoginService.exception.TokenException;

@PropertySource("classpath:error.properties")
@Component
public class TokenGenerator {

	@Autowired
	private Environment environment;
	private static String TOKEN_SECRET;
	Logger logger = LoggerFactory.getLogger(TokenGenerator.class);

	/**
	 * default constructor
	 */
	public TokenGenerator() {

	}

	/**
	 * generates a JWT token for user with id
	 * 
	 * @param id id of the user
	 * @return JWT token
	 */
	public String generateUserToken(long id) {
		TOKEN_SECRET = environment.getProperty("jwt.secret.key");
		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
		String token = JWT.create().withClaim("ID", id).sign(algorithm);
		return token;
	}

	public long retrieveIdFromToken(String token) {
		TOKEN_SECRET = environment.getProperty("jwt.secret.key");
		try {
			Verification verification;
			verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
			JWTVerifier jwtverifier = verification.build();
			DecodedJWT decodedjwt = jwtverifier.verify(token);
			Claim claim = decodedjwt.getClaim("ID");
			long userid = claim.asLong();
			return userid;
		} catch (Exception e) {
			throw new TokenException(Integer.parseInt(environment.getProperty("status.token.errorCode")),
					environment.getProperty("status.token.errorMessage"));
		}
	}
}
