package co.com.nisum.microservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class PhoneTest {

	@Test
	void getMethodsPhone(){
		Phone phone=new Phone();
		phone.setIdPhone(UUID.fromString("81736ac1-1154-4bd9-bfae-37b3195a4dc3"));
		phone.setNumber("123456789012345");
		phone.setCityCode("031");
		phone.setCountryCode("30");
		phone.setUser(new User());
		assertEquals("Phone(idPhone=81736ac1-1154-4bd9-bfae-37b3195a4dc3, number=123456789012345, cityCode=031, countryCode=30, user=User(idUser=null, name=null, email=null, password=null, phones=null, tokens=null, created=null, modified=null))", phone.toString());
	}

}
