package co.com.nisum.microservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserRequestDTOTest {

	@Test
	void instanceUserRequestDTOTest() {
		
		UserRequestDTO userRequestDTO=new UserRequestDTO();
		
		assertEquals("{\r\n"
				+ "  \"idUser\" : null,\r\n"
				+ "  \"name\" : null,\r\n"
				+ "  \"email\" : null,\r\n"
				+ "  \"password\" : null,\r\n"
				+ "  \"phones\" : null\r\n"
				+ "}", userRequestDTO.toString());
	}

}
