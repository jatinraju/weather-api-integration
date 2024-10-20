package com.wai.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WeatherException extends RuntimeException {

	private static final long serialVersionUID = -7852034413641231508L;
	private String errorCode;
	private String errorMessage;
	private HttpStatus httpStatus;
}
