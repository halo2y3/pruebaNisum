package co.com.nisum.microservice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import co.com.nisum.microservice.MicroservicesApplication;

@SpringBootTest(classes = MicroservicesApplication.class)
class MicroservicesApplicationTest {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
