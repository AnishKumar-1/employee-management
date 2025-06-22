package com.management.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalException {

	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	String formattedDate = LocalDateTime.now().format(dateFormatter);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> exceptionHandler(Exception ex, HttpServletRequest request) {
		// Default values
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		// Check exception type and update the status
		if (ex instanceof BadCredentialsException) {
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (ex instanceof NoSuchElementException) {
			httpStatus = HttpStatus.NOT_FOUND;
		} else if (ex instanceof ResourceNotFoundException) {
			httpStatus = HttpStatus.NOT_FOUND;
		} else if (ex instanceof IllegalArgumentException) {
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (ex instanceof AuthorizationDeniedException) {
			httpStatus = HttpStatus.FORBIDDEN;
		} else if (ex instanceof ExpiredJwtException) {
			httpStatus = HttpStatus.UNAUTHORIZED;
		} else if (ex instanceof MalformedJwtException) {
			httpStatus = HttpStatus.UNAUTHORIZED;
		} else if (ex instanceof NoResourceFoundException) {
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (ex instanceof DuplicateResourceException) {
			httpStatus = HttpStatus.BAD_REQUEST;
		}

		// Create error response
		ApiError apiResponse = new ApiError(formattedDate, httpStatus.value(), httpStatus, ex.getLocalizedMessage(),
				request.getRequestURI());

		return new ResponseEntity<>(apiResponse, httpStatus);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> methodLevelValidation(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
