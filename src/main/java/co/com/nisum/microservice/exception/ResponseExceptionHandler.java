package co.com.nisum.microservice.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.com.nisum.microservice.exception.ExceptionManager.NotFoundException;
import co.com.nisum.microservice.exception.ExceptionManager.NullEntityExcepcion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public @ResponseBody ResponseEntity<ExceptionResponse> handlerAccessDeniedException(final Exception ex,
			final HttpServletRequest request, final HttpServletResponse response) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "HTTP ERROR 403 Forbidden", "Forbidden");
		return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
	}

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("ERROR InvalidDataAccessApiUsageException:", ex);
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validacion fallida", request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<ExceptionResponse> invalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex, WebRequest request){
		log.error("ERROR InvalidDataAccessApiUsageException:", ex);
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}	

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<ExceptionResponse> manejarTodasExcepciones(Exception ex, WebRequest request){
		log.error("ERROR Exception:", ex);
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}	

	@ExceptionHandler(ExceptionManager.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<ExceptionResponse> manejarModeloExcepciones(ExceptionManager ex, WebRequest request) {
		log.debug("ERROR ExceptionManager:", ex);
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NullEntityExcepcion.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<ExceptionResponse> exceptionData(NullEntityExcepcion ex, WebRequest request) {
		log.debug("ERROR NullEntityExcepcion:", ex);
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}	

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ResponseEntity<ExceptionResponse> exceptionNotFoundData(NotFoundException ex, WebRequest request) {
		log.debug("ERROR NotFoundException:", ex);
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@Getter
	@Setter
	@ToString
	public class ExceptionResponse {

		private Date timestamp;
		private String mensaje;
		private String detalle;

		public ExceptionResponse() {
		}

		public ExceptionResponse(Date timestamp, String mensaje, String detalle) {
			this.timestamp = timestamp;
			this.mensaje = mensaje;
			this.detalle = detalle;
		}

	}

}
