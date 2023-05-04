package com.maxis.hsbb.usermanagement.exceptions;

import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class HSBBResponseEntitytExceptionHandler extends ResponseEntityExceptionHandler{

	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
		
		
		return new ResponseEntity<Object>(new ExceptionResponse(new Date()
				,ex.getMessage(), request.getDescription(false))
				,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundExceptions(UserNotFoundException ex, WebRequest request){
		
		return new ResponseEntity<Object>(new ExceptionResponse(new Date()
				,ex.getMessage(), request.getDescription(false))
				,HttpStatus.NOT_FOUND);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		return new ResponseEntity<Object>(
				new ExceptionResponse(new Date(), "Valdiation Failed", ex.getBindingResult().toString()),
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ValidationException.class)
	public final ResponseEntity<Object> handleValidationException(ValidationException ex,
			WebRequest request){
		
		return new ResponseEntity<Object>(new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false))
				, HttpStatus.NOT_ACCEPTABLE);
	}
	
}
