package co.com.nisum.microservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserResponseDTOTest {

	@Test
	void instanceUserRequestDTOTest() {
		
		UserResponseDTO userResponseDTO=new UserResponseDTO();
		
		assertEquals("{\r\n"
				+ "  \"created\" : null,\r\n"
				+ "  \"modified\" : null,\r\n"
				+ "  \"token\" : null,\r\n"
				+ "  \"id_user\" : null,\r\n"
				+ "  \"last_login\" : null,\r\n"
				+ "  \"is_active\" : false\r\n"
				+ "}", userResponseDTO.toString());
	}

}
