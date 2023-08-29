package co.com.nisum.microservice.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.MethodParameter;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import co.com.nisum.microservice.exception.ResponseExceptionHandler.ExceptionResponse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ResponseExceptionHandlerTest {	

	@Autowired
	ResponseExceptionHandler responseExceptionHandler;

	@Mock
	WebRequest webRequest;
	@Mock
	MethodParameter parameter;
	@Mock
	BindingResult bindingResult;
	@Mock
	MethodArgumentNotValidException methodArgumentNotValidException;
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	
	HttpHeaders headers = new HttpHeaders();
	
	@Test
	void handlerAccessDeniedException(){
		ResponseEntity<ExceptionResponse> respuesta=responseExceptionHandler.handlerAccessDeniedException(new Exception("FORBIDDEN"), request, response);
		assertEquals(HttpStatus.FORBIDDEN, respuesta.getStatusCode());
	}
	
	@Test
	void handleMethodArgumentNotValid(){
		ResponseEntity<Object> respuesta=responseExceptionHandler.handleMethodArgumentNotValid(methodArgumentNotValidException, headers, HttpStatus.BAD_REQUEST, webRequest);
		assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
	}

	@Test
	void invalidDataAccessApiUsageExceptionTest(){
		ResponseEntity<ExceptionResponse> respuesta=responseExceptionHandler.invalidDataAccessApiUsageException(new InvalidDataAccessApiUsageException("Concentrador"), webRequest);
		assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
	}

	@Test
	void manejarTodasExcepcionesTest(){
		ResponseEntity<ExceptionResponse> respuesta=responseExceptionHandler.manejarTodasExcepciones(new Exception("Concentrador"), webRequest);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
	}

	@Test
	void manejarModeloExcepcionesTest(){
		ResponseEntity<ExceptionResponse> respuesta=responseExceptionHandler.manejarModeloExcepciones(new ExceptionManager("User"), webRequest);
		assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
	}

	@Test
	void exceptionDataTest(){
		ResponseEntity<ExceptionResponse> respuesta=responseExceptionHandler.exceptionData(new ExceptionManager().new NullEntityExcepcion("User"), webRequest);
		assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
	}

	@Test
	void exceptionNotFoundDataTest(){
		ResponseEntity<ExceptionResponse> respuesta=responseExceptionHandler.exceptionNotFoundData(new ExceptionManager().new NotFoundException("User"), webRequest);
		assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
	}

	@Test
	void setExceptionResponseTest(){
		ExceptionResponse exception=responseExceptionHandler.new ExceptionResponse();
		exception.setDetalle("detalle");
		exception.setMensaje("mensaje");
		exception.setTimestamp(new Date());
		exception.toString();
		assertEquals("detalle", exception.getDetalle());
	}

}
