package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.application.dto.ResponseDto;
import com.atlasbase.atlasbase_core.application.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalWebExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ResponseDto> handleBadCredentialsException(BadCredentialsException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(ResponseDto.builder().error(Map.of("message", e.getMessage())).build());
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ResponseDto> handleValidationException(ValidationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ResponseDto.builder().error(Map.of("message", e.getMessage())).build());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDto> handleGenericException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ResponseDto.builder().error(Map.of("message", e.getMessage())).build());
	}

}
