package co.com.nisum.microservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.nisum.microservice.domain.Phone;
import co.com.nisum.microservice.domain.User;
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

		utilities.validationObjet(entity, "User");

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

		utilities.validationObjet(entity, "User");
		userRepository.deleteById(entity.getIdUser());

		log.debug("delete User successful");
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public User update(User entity) {
		log.debug("updating User instance");

		utilities.validationObjet(entity, "User ");
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

}
