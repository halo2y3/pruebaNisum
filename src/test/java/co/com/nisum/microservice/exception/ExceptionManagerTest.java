package co.com.nisum.microservice.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import co.com.nisum.microservice.exception.ExceptionManager.DeletingException;
import co.com.nisum.microservice.exception.ExceptionManager.EmptyFieldException;
import co.com.nisum.microservice.exception.ExceptionManager.NotFoundException;
import co.com.nisum.microservice.exception.ExceptionManager.NullEntityExcepcion;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExceptionManagerTest {

	@Test
	void nullEntityExcepcionTest(){
		ExceptionManager e=new ExceptionManager().new NullEntityExcepcion("Entidad Vacia");
		Assertions.assertThrows(NullEntityExcepcion.class, () -> {
			throw e;
		});
	}

	@Test
	void emptyFieldExceptionTest(){
		ExceptionManager e = new ExceptionManager().new EmptyFieldException("Campo Vacio");
		Assertions.assertThrows(EmptyFieldException.class, () -> {
			throw e;
		});
	}

	@Test
	void deletingExceptionTest(){
		ExceptionManager e = new ExceptionManager().new DeletingException("Borrando Entidad");
		Assertions.assertThrows(DeletingException.class, () -> {
			throw e;
		});
	}

	@Test
	void notFoundExceptionTest(){
		ExceptionManager e = new ExceptionManager().new NotFoundException ("Concentrador");
		Assertions.assertThrows(NotFoundException.class, () -> {
			throw e;
		});
	}

}	
