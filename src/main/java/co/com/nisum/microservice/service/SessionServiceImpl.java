package co.com.nisum.microservice.service;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.nisum.microservice.domain.Session;
import co.com.nisum.microservice.repository.SessionRepository;
import co.com.nisum.microservice.utility.Utilities;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Scope("singleton")
@Service
public class SessionServiceImpl implements SessionService{

	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private Validator validator;
	@Autowired
	private Utilities utilities;

	@Override
	public void validate(Session session) {
		utilities.validate(session, validator);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Session save(Session entity) {
		log.debug("saving Session instance");

		Utilities.validationObjet(entity, "Session");
		validate(entity);

		return sessionRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Session entity) {
		log.debug("deleting Session instance");

		Utilities.validationObjet(entity, "User");
		sessionRepository.deleteById(entity.getIdSession());

		log.debug("delete Session successful");
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Session update(Session entity) {
		log.debug("updating Session instance");

		Utilities.validationObjet(entity, "Session ");
		validate(entity);
		return sessionRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Session> findById(UUID idSession) {
		log.debug("getting Session instance");
		return sessionRepository.findById(idSession);
	}

}
