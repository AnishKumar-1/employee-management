package com.management.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalException {

	   private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	   String formattedDate = LocalDateTime.now().format(dateFormatter);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> exceptionHandler(Exception ex, HttpServletRequest request) {
		// Format timestamp as "dd/MM/yyyy"
      
		 
		ApiError apiResponse;

		HttpStatus httpStatus;
		
		if (ex instanceof BadCredentialsException) {
			apiResponse = new ApiError(formattedDate, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					ex.getLocalizedMessage(), request.getRequestURI());
			httpStatus = HttpStatus.BAD_REQUEST;
		}

		// Default Internal Server Error response
		apiResponse = new ApiError(formattedDate, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), request.getRequestURI());
		httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		return new ResponseEntity<>(apiResponse, httpStatus);
	}
	
	   @ExceptionHandler(AuthorizationDeniedException.class)
	    public ResponseEntity<ApiError> handleAuthorizationDeniedException(AuthorizationDeniedException ex, HttpServletRequest request) {
	        // 403 Forbidden response

	        ApiError apiResponse = new ApiError(
	        		formattedDate,
	            HttpStatus.FORBIDDEN.value(),
	            HttpStatus.FORBIDDEN,
	            ex.getMessage(),
	            request.getRequestURI()
	        );

	        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
	    }
}
