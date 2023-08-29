package co.com.nisum.microservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.nisum.microservice.domain.Phone;
import co.com.nisum.microservice.domain.User;
import co.com.nisum.microservice.dto.UserRequestDTO;
import co.com.nisum.microservice.dto.UserResponseDTO;
import co.com.nisum.microservice.exception.ResponseExceptionHandler;
import co.com.nisum.microservice.mapper.PhoneMapper;
import co.com.nisum.microservice.service.PhoneServiceImpl;


@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@Sql({"classpath:schema.sql"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserRestControllerTest {

	@Autowired
	private UserRestController restController;

	@Autowired
	private PhoneServiceImpl phoneServiceImpl; 

	@Autowired
	private PhoneMapper phoneMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ResponseExceptionHandler responseExceptionHandler;

	private UserRequestDTO objetoDTO;

	@Order(0)
	@Test
	void saveUserTest() throws JsonProcessingException {
		objetoDTO= new UserRequestDTO();
		objetoDTO.setName("name");
		objetoDTO.setPassword("Pass123@@");
		objetoDTO.setEmail("test1@test.com");

		ResponseEntity<Object> respuesta=restController.save(objetoDTO);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(respuesta.getBody()); 
		UserResponseDTO userResponseDTO= objectMapper.readValue(json, UserResponseDTO.class);

		Phone phone= new Phone();
		phone.setNumber("5547371");
		phone.setCityCode("10");
		phone.setCountryCode("57");
		User userBD=new User();
		userBD.setIdUser(userResponseDTO.getIdUser());
		phone.setUser(userBD);
		phoneServiceImpl.save(phone);

		assertEquals(200, respuesta.getStatusCodeValue());
	}

	@Order(1)
	@Test
	void updateTest() throws JsonProcessingException {
		objetoDTO= new UserRequestDTO();
		objetoDTO.setName("name");
		objetoDTO.setPassword("Pass123@@");
		objetoDTO.setEmail("test2@test.com");

		ResponseEntity<Object> respuesta=restController.save(objetoDTO);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(respuesta.getBody()); 
		UserResponseDTO userResponseDTO= objectMapper.readValue(json, UserResponseDTO.class);

		objetoDTO.setIdUser(userResponseDTO.getIdUser());

		ResponseEntity<Object> respuesta2=restController.update(objetoDTO);
		assertEquals(200, respuesta2.getStatusCodeValue());
	}

	@Order(2)
	@Test
	void findByIdTest() throws JsonMappingException, JsonProcessingException {
		objetoDTO= new UserRequestDTO();
		objetoDTO.setName("name");
		objetoDTO.setPassword("Pass123@@");
		objetoDTO.setEmail("test3@test.com");

		ResponseEntity<Object> respuesta=restController.save(objetoDTO);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(respuesta.getBody()); 
		UserResponseDTO userResponseDTO= objectMapper.readValue(json, UserResponseDTO.class);

		ResponseEntity<Object> respuesta2=restController.findById(userResponseDTO.getIdUser().toString());
		assertEquals(200, respuesta2.getStatusCodeValue());
	}

	@Order(3)
	@Test
	void findByIdErrorTest() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(restController)
				.setControllerAdvice(responseExceptionHandler)
				.build();

		mockMvc.perform(MockMvcRequestBuilders.get("/api/service/user/findById/5a4c2115-934a-489d-b9dc-0a0995d3f3f0"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Order(4)
	@Test
	void deleteTest() throws JsonProcessingException  {
		objetoDTO= new UserRequestDTO();
		objetoDTO.setName("name");
		objetoDTO.setPassword("Pass123@@");
		objetoDTO.setEmail("test4@test.com");

		ResponseEntity<Object> respuesta=restController.save(objetoDTO);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(respuesta.getBody()); 
		UserResponseDTO userResponseDTO= objectMapper.readValue(json, UserResponseDTO.class);

		ResponseEntity<Object> respuesta2=restController.delete(userResponseDTO.getIdUser().toString());
		assertEquals(200, respuesta2.getStatusCodeValue());
	}

	@Order(5)
	@Test
	void deleteErrorTest() throws Exception  {
		mockMvc = MockMvcBuilders.standaloneSetup(restController)
				.setControllerAdvice(responseExceptionHandler)
				.build();

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/service/user/delete/5a4c2115-934a-489d-b9dc-0a0995d3f3f0"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
