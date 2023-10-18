package co.com.nisum.microservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import co.com.nisum.microservice.dto.LoginRequestDTO;


@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class LoginRestControllerTest {

	@Autowired
	private LoginRestController restController;
	
	@Test
	void findByIdTest() throws JsonMappingException, JsonProcessingException {
		LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
		loginRequestDTO.setEmail("edwinhalo2y3@hotmail.com");
		loginRequestDTO.setPass("66809988Aa@@");
		ResponseEntity<Object> respuesta=restController.authentication(loginRequestDTO);
		assertEquals(200, respuesta.getStatusCodeValue());
	}


}
