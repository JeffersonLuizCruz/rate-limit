package com.dnia.eaas.controller.exception;


import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dnia.eaas.exception.NotFoundException;
import com.dnia.eaas.exception.UnauthorizedException;
import com.dnia.eaas.exception.UserNameNotFoundExecption;



@ControllerAdvice
public class GlobalExecptionHandler extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiError> handleUnauthorizedException(UnauthorizedException ex) {
		ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), Instant.now());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(UserNameNotFoundExecption.class)
	public ResponseEntity<ApiError> handleNotFoundException(UserNameNotFoundExecption ex) {
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex) {
		ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), Instant.now());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
		ApiError error = new ApiError(HttpStatus.FORBIDDEN.value(), ex.getMessage(), Instant.now());
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
//		List<String> errors = new ArrayList<String>();
//		
//		ex.getBindingResult().getAllErrors().forEach(error -> {
//			errors.add(error.getDefaultMessage());
//		});
		
		List<String> errors = ex.getBindingResult()
								.getAllErrors()
								.stream()
								.map(p -> p.getDefaultMessage())
								.collect(Collectors.toList());
		
		String defaultMessage = "Invalid field(s)";
		
		ApiErrorList error = new ApiErrorList(HttpStatus.BAD_REQUEST.value(), defaultMessage, Instant.now(), errors);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}

