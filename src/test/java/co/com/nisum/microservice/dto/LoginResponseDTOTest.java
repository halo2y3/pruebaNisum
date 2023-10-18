package co.com.nisum.microservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LoginResponseDTOTest {

	@Test
	void instanceLoginResponseDTOTest() {
		
		LoginResponseDTO loginResponseDTO=new LoginResponseDTO();
		
		loginResponseDTO.setToken("Token");
		
		assertEquals("Token", loginResponseDTO.getToken());
		assertEquals("{\r\n"
				+ "  \"token\" : \"Token\"\r\n"
				+ "}", loginResponseDTO.toString());
	}

}
