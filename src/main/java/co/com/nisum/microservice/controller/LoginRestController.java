package co.com.nisum.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.nisum.microservice.dto.LoginRequestDTO;
import co.com.nisum.microservice.dto.LoginResponseDTO;
import co.com.nisum.microservice.exception.ResponseExceptionHandler.ExceptionResponse;
import co.com.nisum.microservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/public/login")
@CrossOrigin(origins = "*")
public class LoginRestController {

	@Autowired
	private UserService userService;
	
	@Operation(summary = "Login usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", 
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = LoginResponseDTO.class))}),
			@ApiResponse(responseCode = "400", description = "Bad Request", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))}),
			@ApiResponse(responseCode = "403", description = "Forbidden", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))}),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))})})
	@PostMapping(value = "/authentication")
	public ResponseEntity<Object> authentication(@RequestBody LoginRequestDTO loginRequestDTO) {
		log.debug("Request to authentication: {}", loginRequestDTO);
		return ResponseEntity.ok().body(userService.loginUser(loginRequestDTO));
	}

}
