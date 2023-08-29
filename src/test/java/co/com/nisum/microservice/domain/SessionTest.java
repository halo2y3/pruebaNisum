package co.com.nisum.microservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class SessionTest {

	@Test
	void getMethodsSession(){
		Session session=new Session();
		session.setIdSession(UUID.fromString("81736ac1-1154-4bd9-bfae-37b3195a4dc3"));
		assertEquals("Session(idSession=81736ac1-1154-4bd9-bfae-37b3195a4dc3, token=null, isActive=false, lastLogin=null, user=null)", session.toString());
	}

}
