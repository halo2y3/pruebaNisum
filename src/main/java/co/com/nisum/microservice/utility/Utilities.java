package co.com.nisum.microservice.utility;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import co.com.nisum.microservice.domain.Phone;
import co.com.nisum.microservice.domain.Session;
import co.com.nisum.microservice.domain.User;
import co.com.nisum.microservice.dto.PhoneDTO;
import co.com.nisum.microservice.dto.UserRequestDTO;
import co.com.nisum.microservice.dto.UserResponseDTO;
import co.com.nisum.microservice.exception.ExceptionManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Utilities {

	@Autowired
    private ConfigApplication config;

	public void validate(Object object, Validator validator) {
		Set<ConstraintViolation<Object>> constraintViolations=validator.validate(object);
		if (!constraintViolations.isEmpty()) {
			StringBuilder strMessage = new StringBuilder();
			for (ConstraintViolation<?> constraintViolation : constraintViolations) {
				strMessage.append(constraintViolation.getPropertyPath().toString());
				strMessage.append(" - "+constraintViolation.getMessage()+". \n");
			}
			throw new ExceptionManager(strMessage.toString());
		}
	}

	public void validationObjet(Object objeto, String mensaje) {
		if (objeto == null) {
			throw new ExceptionManager().new NullEntityExcepcion(mensaje);
		}
	}

	public static String toStringObjec(Object objeto) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.enable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		try {
			return mapper.writeValueAsString(objeto);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			return objeto.getClass().getName() + "@" + Integer.toHexString(objeto.hashCode());
		}
	}

	public static List<Phone> mappPhones(List<PhoneDTO> list, UUID idUser) {
		List<Phone> listPhone=new ArrayList<>();
		if(list==null || list.isEmpty()) {
			return listPhone;
		}
		for (PhoneDTO phoneDTO : list) {

			User userBD=new User();
			userBD.setIdUser(idUser);

			Phone phone=new Phone();
			phone.setIdPhone(phoneDTO.getIdPhone());
			phone.setNumber(phoneDTO.getNumber());
			phone.setCityCode(phoneDTO.getCityCode());
			phone.setCountryCode(phoneDTO.getCountryCode());
			phone.setUser(userBD);

			listPhone.add(phone);
		}

		return listPhone;
	}

	public static String convertToDateViaInstant(LocalDateTime dateToConvert) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date=java.util.Date
				.from(dateToConvert.atZone(ZoneId.systemDefault())
						.toInstant());

		return simpleDateFormat.format(date);
	}

	public static UserResponseDTO createUserResponseDTO(User user, Session session){
		UserResponseDTO userResponseDTO=new UserResponseDTO();
		userResponseDTO.setIdUser(user.getIdUser());
		userResponseDTO.setCreated(Utilities.convertToDateViaInstant(user.getCreated()));
		userResponseDTO.setModified(Utilities.convertToDateViaInstant(user.getModified()));
		userResponseDTO.setActive(session.isActive());
		userResponseDTO.setToken(session.getToken());
		userResponseDTO.setLastLogin(Utilities.convertToDateViaInstant(session.getLastLogin()));
		return userResponseDTO;
	}

	public static Session createSession(User user, String token){

		Session session=new Session();
		session.setActive(false);
		session.setLastLogin(LocalDateTime.now());
		session.setToken(token);
		session.setUser(user);

		return session;
	}

	public static UserDetails createUserDetails(UserRequestDTO userDTO){
		GrantedAuthority rol= new SimpleGrantedAuthority("USER");
		List<GrantedAuthority> listGrantedAuthority=new ArrayList<>();
		listGrantedAuthority.add(rol);
		return new org.springframework.security.core.userdetails.User(userDTO.getEmail(), userDTO.getPassword(), listGrantedAuthority);
	}

	public boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(config.getEmailRegx());   
		Matcher matcher = pattern.matcher(email);  
		return matcher.matches();  
	}

	public boolean isValidPassword(String password) {
		Pattern pattern = Pattern.compile(config.getPasswordRegx());   
		Matcher matcher = pattern.matcher(password);  
		return matcher.matches();  
	}

}
