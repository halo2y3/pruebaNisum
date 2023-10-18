package co.com.nisum.microservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.nisum.microservice.domain.Phone;
import co.com.nisum.microservice.domain.Session;
import co.com.nisum.microservice.domain.User;
import co.com.nisum.microservice.dto.LoginRequestDTO;
import co.com.nisum.microservice.dto.LoginResponseDTO;
import co.com.nisum.microservice.exception.ExceptionManager;
import co.com.nisum.microservice.repository.UserRepository;
import co.com.nisum.microservice.utility.Utilities;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Scope("singleton")
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private JwtUtilService jwtUtilService;
	@Autowired
	private Validator validator;
	@Autowired
	Utilities utilities;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void validate(User user) {
		utilities.validate(user, validator);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public User save(User entity) {
		log.debug("saving User instance");

		Utilities.validationObjet(entity, "User");

		validate(entity);

		entity.setEmail(entity.getEmail().toLowerCase());

		Optional<User> userOptional=userRepository.getUserByEmail(entity.getEmail());

		if(userOptional.isPresent()) {
			throw new ExceptionManager("Correo ya registrado");    
		}

		List<Phone> listPhone=entity.getPhones();
		for (Phone phone : listPhone) {
			phone.setUser(entity);
		}

		if(!utilities.isValidEmail(entity.getEmail())) {
			throw new ExceptionManager("El email no es valido");
		}

		if(!utilities.isValidPassword(entity.getPassword())) {
			throw new ExceptionManager("El password no es valido");
		}

		entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));

		return userRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(User entity) {
		log.debug("deleting User instance");

		Utilities.validationObjet(entity, "User");
		userRepository.deleteById(entity.getIdUser());

		log.debug("delete User successful");
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public User update(User entity) {
		log.debug("updating User instance");

		Utilities.validationObjet(entity, "User ");
		validate(entity);

		if(!utilities.isValidEmail(entity.getEmail())) {
			throw new ExceptionManager("El email no es valido");
		}

		if(!utilities.isValidPassword(entity.getPassword())) {
			throw new ExceptionManager("El password no es valido");
		}

		return userRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(UUID identificador) {
		log.debug("getting User instance");
		return userRepository.findById(identificador);
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		log.debug("getUserByEmail email: {}", email);
		return userRepository.getUserByEmail(email);
	}

	@Override
	public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
		Optional<User> userOptional = userRepository.getUserByEmail(loginRequestDTO.getEmail());

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
						return loginResponseDTO;
					}else {
						loginResponseDTO =createSessionToken(user, loginRequestDTO);
						return loginResponseDTO;
					}
				}
				loginResponseDTO =createSessionToken(user, loginRequestDTO);
				return loginResponseDTO;
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
