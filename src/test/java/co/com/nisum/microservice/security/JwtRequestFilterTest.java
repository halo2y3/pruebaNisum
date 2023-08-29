package co.com.nisum.microservice.security;

import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JwtRequestFilterTest {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	FilterChain chain;

	@Test
	void doFilterInternalTest() throws ServletException, IOException{
		when(request.getHeader("Authorization")).thenReturn("");
		jwtRequestFilter.doFilterInternal(request, response, chain);
	}

}
