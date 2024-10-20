package com.wai.constants;

public enum ErrorCodeEnum {

	// Define error codes and corresponding messages
	INVALID_INPUT("1001", "Invalid input provided"), NULL_OR_EMPTY_FIELD("1002", "Required field is null or empty"),
	// New error code for invalid date format
	INVALID_DATE_FORMAT("1007", "Invalid date format. Date format should be yyyy-MM-dd"),
	INVALID_TIME_FORMAT("1008", "Invalid time format. Time format should be HH:mm:ss");

	private final String code;
	private final String message;

	// Constructor
	ErrorCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	// Getter methods
	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "ErrorCode " + code + ": " + message;
	}

}
