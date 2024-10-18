package com.wai.constants;

public class EndPoints {

	// TODO: Change Path Variable to RequestParam
	public static final String API_V1 = "/api/v1";
	public static final String TEST = "/test";

	public static final String LOCATION = "/{location}";
	public static final String LOCATION_DYNAMIC_PERIOD = "/period/{location}/{period}";
	public static final String LOCATION_DATE = "/date/{location}/{date}";
	public static final String LOCATION_DATE_RANGE = "/daterange/{location}/{from}/{to}";
	public static final String LOCATION_DATE_TIME = "/datetime/{location}/{date}/{time}";
}
