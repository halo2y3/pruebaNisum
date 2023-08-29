package co.com.nisum.microservice.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.nisum.microservice.exception.ResponseExceptionHandler;
import co.com.nisum.microservice.exception.ResponseExceptionHandler.ExceptionResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {

		logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
		httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
		httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

		ServletOutputStream out = httpServletResponse.getOutputStream();
		ExceptionResponse exceptionResponse = new ResponseExceptionHandler().new ExceptionResponse(new Date(), "HTTP ERROR 403 Forbidden", "Forbidden");
		new ObjectMapper().writeValue(out, exceptionResponse);
		out.flush();
	}
}
