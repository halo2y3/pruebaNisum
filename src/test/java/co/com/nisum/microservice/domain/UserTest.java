package co.com.nisum.microservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserTest {

	@Test
	void getMethodsUser(){
		User user=new User();
		user.setModified(null);
		user.setTokens(null);
		assertEquals("User(idUser=null, name=null, email=null, password=null, phones=null, tokens=null, created=null, modified=null)", user.toString());
	}

}
