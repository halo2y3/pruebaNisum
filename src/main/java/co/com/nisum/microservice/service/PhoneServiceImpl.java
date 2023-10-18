package co.com.nisum.microservice.service;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.nisum.microservice.domain.Phone;
import co.com.nisum.microservice.repository.PhoneRepository;
import co.com.nisum.microservice.utility.Utilities;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Scope("singleton")
@Service
public class PhoneServiceImpl implements PhoneService{

	@Autowired
	private PhoneRepository phoneRepository;
	@Autowired
	private Validator validator;
	@Autowired
	private Utilities utilities;

	@Override
	public void validate(Phone phone) {
		utilities.validate(phone, validator);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Phone save(Phone entity) {
		log.debug("saving Phone instance");

		Utilities.validationObjet(entity, "Phone");
		validate(entity);

		return phoneRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Phone entity) {
		log.debug("deleting Phone instance");

		Utilities.validationObjet(entity, "Phone");
		phoneRepository.deleteById(entity.getIdPhone());

		log.debug("delete User successful");
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Phone update(Phone entity) {
		log.debug("updating Phone instance");

		Utilities.validationObjet(entity, "User ");
		validate(entity);
		return phoneRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Phone> findById(UUID identificador) {
		log.debug("getting Phone instance");
		return phoneRepository.findById(identificador);
	}

}
