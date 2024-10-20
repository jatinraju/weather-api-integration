package com.wai.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.wai.constants.ErrorCodeEnum;
import com.wai.pojo.ErrorResponse;

@ControllerAdvice
public class WeatherExceptionHandler {

	@ExceptionHandler(WeatherException.class)
	public ResponseEntity<ErrorResponse> weatherExceptionHandler(WeatherException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(ex.getErrorCode());
		errorResponse.setErrorMessage(ex.getErrorMessage());
		return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorResponse> noResourceFoundExceptionHandler() {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(ErrorCodeEnum.NULL_OR_EMPTY_FIELD.getCode());
		errorResponse.setErrorMessage(ErrorCodeEnum.NULL_OR_EMPTY_FIELD.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
