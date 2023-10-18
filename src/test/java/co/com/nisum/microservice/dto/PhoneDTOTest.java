package co.com.nisum.microservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class PhoneDTOTest {

	@Test
	void instancePhoneDTOTest() {
		
		UUID idPhone=UUID.randomUUID();
		PhoneDTO phoneDTO=new PhoneDTO();
		phoneDTO.setCityCode("1020");
		phoneDTO.setCountryCode("57");
		phoneDTO.setIdPhone(idPhone);
		phoneDTO.setNumber("3182174957");
		
		assertEquals("1020", phoneDTO.getCityCode());
		assertEquals("57", phoneDTO.getCountryCode());
		assertEquals(idPhone, phoneDTO.getIdPhone());
		assertEquals("3182174957", phoneDTO.getNumber());
		
		assertEquals("{\r\n"
				+ "  \"idPhone\" : \""+idPhone+"\",\r\n"
				+ "  \"number\" : \"3182174957\",\r\n"
				+ "  \"citycode\" : \"1020\",\r\n"
				+ "  \"countrycode\" : \"57\"\r\n"
				+ "}", phoneDTO.toString());
	}

}
