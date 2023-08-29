package co.com.nisum.microservice.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.nisum.microservice.domain.Session;
import co.com.nisum.microservice.domain.User;
import co.com.nisum.microservice.dto.LoginRequestDTO;
import co.com.nisum.microservice.dto.LoginResponseDTO;
import co.com.nisum.microservice.exception.ExceptionManager;
import co.com.nisum.microservice.exception.ResponseExceptionHandler.ExceptionResponse;
import co.com.nisum.microservice.service.JwtUtilService;
import co.com.nisum.microservice.service.SessionService;
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
	@Autowired
	private SessionService sessionService;
	@Autowired
	private JwtUtilService jwtUtilService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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

		Optional<User> userOptional = userService.getUserByEmail(loginRequestDTO.getEmail());

		if(userOptional.isPresent()) {
			User user=userOptional.get();
			log.info(bCryptPasswordEncoder.encode(loginRequestDTO.getPass()));
			if(bCryptPasswordEncoder.matches(loginRequestDTO.getPass(), user.getPassword())) {
				List<Session> listTokens=user.getTokens();
				LoginResponseDTO loginResponseDTO=new LoginResponseDTO();
				for (Session session : listTokens) {
					if(session.isActive()) {
						loginResponseDTO.setToken(session.getToken());
						log.info(loginResponseDTO.toString());
						return ResponseEntity.ok().body(loginResponseDTO);
					}else {
						loginResponseDTO =createSessionToken(user, loginRequestDTO);
						return ResponseEntity.ok().body(loginResponseDTO);
					}
				}
				loginResponseDTO =createSessionToken(user, loginRequestDTO);
				return ResponseEntity.ok().body(loginResponseDTO);
			}
		}

		throw new ExceptionManager("Credenciales no validas");
	}

	private LoginResponseDTO createSessionToken(User user, LoginRequestDTO loginRequestDTO) {

		GrantedAuthority rol= new SimpleGrantedAuthority("USER");
		List<GrantedAuthority> listGrantedAuthority=new ArrayList<>();
		listGrantedAuthority.add(rol);
		UserDetails userDetails=new org.springframework.security.core.userdetails.User(loginRequestDTO.getEmail(), loginRequestDTO.getPass(), listGrantedAuthority);

		Session newSession=new Session();
		newSession.setActive(false);
		newSession.setLastLogin(LocalDateTime.now());
		newSession.setToken(jwtUtilService.generateToken(userDetails));
		newSession.setUser(user);

		sessionService.save(newSession);

		LoginResponseDTO loginResponseDTO= new LoginResponseDTO();
		loginResponseDTO.setToken(newSession.getToken());
		log.info(loginResponseDTO.toString());
		return loginResponseDTO;
	}

}
