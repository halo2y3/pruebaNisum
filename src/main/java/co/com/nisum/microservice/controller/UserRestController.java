package co.com.nisum.microservice.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.nisum.microservice.domain.Session;
import co.com.nisum.microservice.domain.User;
import co.com.nisum.microservice.dto.UserRequestDTO;
import co.com.nisum.microservice.exception.ExceptionManager;
import co.com.nisum.microservice.exception.ResponseExceptionHandler.ExceptionResponse;
import co.com.nisum.microservice.mapper.UserMapper;
import co.com.nisum.microservice.service.JwtUtilService;
import co.com.nisum.microservice.service.SessionService;
import co.com.nisum.microservice.service.UserService;
import co.com.nisum.microservice.utility.Utilities;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/service/user")
@CrossOrigin(origins = "*")
public class UserRestController {

	@Autowired
	private UserService userService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JwtUtilService jwtUtilService;

	@Operation(summary = "Consulta usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", 
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = UserRequestDTO.class))}),
			@ApiResponse(responseCode = "400", description = "Bad Request", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))}),
			@ApiResponse(responseCode = "403", description = "Forbidden", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))}), 
			@ApiResponse(responseCode = "404", description = "Not Found", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))}),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))})})
	@GetMapping(value = "/findById/{idUser}")
	public ResponseEntity<Object> findById(@PathVariable("idUser") String idUser) {
		log.debug("Request to findById() User, idUser: {}", idUser);

		User user= null;
		Optional<User> userBd = userService.findById(UUID.fromString(idUser));
		if(userBd.isPresent()) {
			user= userBd.get();
		}else{
			throw new ExceptionManager().new NotFoundException("User");
		}
		return ResponseEntity.ok().body(userMapper.userToUserDTO(user));
	}

	@Operation(summary = "Guardar usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", 
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = UserRequestDTO.class))}),
			@ApiResponse(responseCode = "400", description = "Bad Request", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))}),
			@ApiResponse(responseCode = "403", description = "Forbidden", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))}),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))})})
	@PostMapping(value = "/save")
	public ResponseEntity<Object> save(@RequestBody @Valid UserRequestDTO userDTO) {
		log.debug("Request to save User: {}", userDTO);

		User user = userMapper.userDTOToUser(userDTO);	
		user = userService.save(user);

		String token=jwtUtilService.generateToken(Utilities.createUserDetails(userDTO));
		Session session=sessionService.save(Utilities.createSession(user, token));

		return ResponseEntity.ok().body(Utilities.createUserResponseDTO(user, session));
	}

	@Operation(summary = "Actualizar usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", 
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = UserRequestDTO.class))}),
			@ApiResponse(responseCode = "400", description = "Bad Request", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))}),
			@ApiResponse(responseCode = "403", description = "Forbidden", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))}), 
			@ApiResponse(responseCode = "404", description = "Not Found", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))}),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))})})
	@PutMapping(value = "/update")
	public ResponseEntity<Object> update(@RequestBody @Valid UserRequestDTO userDTO) {
		log.debug("Request to update User: {}", userDTO);

		User user = userMapper.userDTOToUser(userDTO);

		Optional<User> userBd = userService.findById(user.getIdUser());
		if(userBd.isPresent()) {
			user.setCreated(userBd.get().getCreated());
		}else{
			throw new ExceptionManager().new NotFoundException("User");
		}

		user.setEmail(user.getEmail().toLowerCase());
		user = userService.update(user);

		return ResponseEntity.ok().body(userMapper.userToUserDTO(user));
	}

	@Operation(summary = "Eliminar usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))}),
			@ApiResponse(responseCode = "403", description = "Forbidden", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))}), 
			@ApiResponse(responseCode = "404", description = "Not Found", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))}),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = ExceptionResponse.class))})})
	@DeleteMapping(value = "/delete/{idUser}")
	public ResponseEntity<Object> delete(@PathVariable("idUser") String idUser) {
		log.debug("Request to delete User, idUser: {}", idUser);

		Optional<User> userDB = userService.findById(UUID.fromString(idUser));
		if(userDB.isPresent()) {
			userService.delete(userDB.get());
		}else{
			throw new ExceptionManager().new NotFoundException("User");
		}
		return ResponseEntity.ok().build();
	}

}
