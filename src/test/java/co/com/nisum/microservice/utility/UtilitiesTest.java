package co.com.nisum.microservice.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import co.com.nisum.microservice.domain.Phone;
import co.com.nisum.microservice.dto.PhoneDTO;
import co.com.nisum.microservice.exception.ExceptionManager;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UtilitiesTest {

	@Test
	void mappPhonesTest(){
		List<PhoneDTO> list=new ArrayList<>();
		list.add(new PhoneDTO());
		List<Phone> listPhone=Utilities.mappPhones(list, UUID.randomUUID());
		assertEquals(1, listPhone.size());
	}
	
	@Test
	void mappPhonesTest2(){
		List<PhoneDTO> list=new ArrayList<>();
		List<Phone> listPhone=Utilities.mappPhones(list, UUID.randomUUID());
		assertEquals(0, listPhone.size());
	}
	
	@Test
	void mappPhonesTest3(){
		List<PhoneDTO> list=null;
		List<Phone> listPhone=Utilities.mappPhones(list, UUID.randomUUID());
		assertEquals(0, listPhone.size());
	}
	
	@Test
	void validationObjetTest(){
		PhoneDTO phoneDTO=null;
		try {
			Utilities.validationObjet(phoneDTO, "Mensaje Error");	
		} catch (Exception e) {
			assertTrue(e instanceof ExceptionManager.NullEntityExcepcion);
		}
		
	}

}
