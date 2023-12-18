package com.athenia.athenia.controller;

import com.athenia.athenia.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/18
 */

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(RuntimeException.class)
	public MessageResponse<Void> handleRuntimeException(RuntimeException ex) {
		log.error("Handle error in GlobalExceptionHandler.class: ", ex);
		return new MessageResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong in server");
	}

	@ExceptionHandler(AuthenticationException.class)
	public MessageResponse<Void> handleAuthenticationException(AuthenticationException ex) {
		log.error("Handle authentication error in GlobalExceptionHandler.class: ", ex);
		return new MessageResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Authentication error" + ex.getMessage());
	}
}
