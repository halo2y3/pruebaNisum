package co.com.nisum.microservice.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ConfigApplication {

	@Value("${expresion-regular.email}")
	private String emailRegx;

	@Value("${expresion-regular.password}")
	private String passwordRegx;

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.token-validity}")
	private long tokenValidity;

}
