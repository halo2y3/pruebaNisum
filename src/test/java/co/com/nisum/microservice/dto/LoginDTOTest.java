package co.com.nisum.microservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LoginDTOTest {

	@Test
	void instanceLoginRequestDTOTest() {
		
		LoginRequestDTO loginRequestDTO=new LoginRequestDTO();
		loginRequestDTO.setEmail("edwinhalo2y3@hotmail.com");
		loginRequestDTO.setPass("12089830@Aa");
		assertEquals("edwinhalo2y3@hotmail.com", loginRequestDTO.getEmail());
		assertEquals("12089830@Aa", loginRequestDTO.getPass());
		assertEquals("{\r\n"
				+ "  \"email\" : \"edwinhalo2y3@hotmail.com\",\r\n"
				+ "  \"pass\" : \"12089830@Aa\"\r\n"
				+ "}", loginRequestDTO.toString());
	}

}
