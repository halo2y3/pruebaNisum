package co.com.nisum.microservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class SessionDTOTest {

	@Test
	void instanceSessionDTOTest() {
		
		UUID idSession = UUID.randomUUID();
		LocalDateTime lastLogin=LocalDateTime.now();
		SessionDTO sessionDTO=new SessionDTO();
		
		UserRequestDTO userRequestDTO=new UserRequestDTO();
		userRequestDTO.setEmail("edwinhalo2y3@hotmail.com");
		userRequestDTO.setPassword("12089830@Aa");
		
		sessionDTO.setActive(false);
		sessionDTO.setIdSession(idSession);
		sessionDTO.setLastLogin(lastLogin);
		sessionDTO.setToken("Token");
		sessionDTO.setUser(userRequestDTO);
		
		assertEquals(false, sessionDTO.isActive());
		assertEquals(idSession, sessionDTO.getIdSession());
		assertEquals(lastLogin, sessionDTO.getLastLogin());
		assertEquals("Token", sessionDTO.getToken());
		assertNotNull(sessionDTO.getUser());
		
		assertEquals("{\r\n"
				+ "  \"idSession\" : \""+idSession+"\",\r\n"
				+ "  \"token\" : \"Token\",\r\n"
				+ "  \"lastLogin\" : \""+lastLogin.toString().subSequence(0, lastLogin.toString().length()-2)+"\",\r\n"
				+ "  \"user\" : {\r\n"
				+ "    \"idUser\" : null,\r\n"
				+ "    \"name\" : null,\r\n"
				+ "    \"email\" : \"edwinhalo2y3@hotmail.com\",\r\n"
				+ "    \"password\" : \"12089830@Aa\",\r\n"
				+ "    \"phones\" : null\r\n"
				+ "  },\r\n"
				+ "  \"active\" : false\r\n"
				+ "}", sessionDTO.toString());
	}

}
