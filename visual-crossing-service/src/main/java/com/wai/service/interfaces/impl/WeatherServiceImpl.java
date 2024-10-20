package com.wai.service.interfaces.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.wai.constants.AppConstants;
import com.wai.constants.ErrorCodeEnum;
import com.wai.exception.WeatherException;
import com.wai.pojo.WeatherResponse;
import com.wai.pojo.WeatherResponseWithoutCurrentConditions;
import com.wai.service.http.HttpRequest;
import com.wai.service.http.HttpServiceEngine;
import com.wai.service.interfaces.WeatherService;
import com.wai.visualweather.Weather;

@Service
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	private HttpServiceEngine httpServiceEngine;

	@Value("${visual.weather.base_url}")
	private String visualWeatherBaseUrl;

	@Value("${visual.weather.api_key}")
	private String visualWeatherApiKey;

	@Value("${visual.weather.data_url}")
	private String visualWeatherDataUrl;

	@Autowired
	private Gson gson;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public WeatherResponse getByLocation(String location) {
		System.out.println("WeatherServiceImpl.getByLocation() | location : " + location);
		validateCity(location);
		String url = visualWeatherBaseUrl + location + "?key=" + visualWeatherApiKey + visualWeatherDataUrl;

		HttpRequest request = prepareHttpRequest(url);
		ResponseEntity<String> response = httpServiceEngine.makeHttpRequest(request);
		System.out.println(response.getBody());

		Weather weather = gson.fromJson(response.getBody(), Weather.class);
		System.out.println(weather);

		return modelMapper.map(weather, WeatherResponse.class);
	}

	@Override
	public WeatherResponseWithoutCurrentConditions getByLocationAndDate(String location, String date) {
		System.out.println("WeatherServiceImpl.getByLocationAndDate() | location : " + location + " | date: " + date);
		validateCity(location);
		validateDate(date);
		String url = visualWeatherBaseUrl + location + "/" + date + "?key=" + visualWeatherApiKey
				+ visualWeatherDataUrl;
		HttpRequest request = prepareHttpRequest(url);
		ResponseEntity<String> response = httpServiceEngine.makeHttpRequest(request);
		System.out.println(response.getBody());

		Weather weather = gson.fromJson(response.getBody(), Weather.class);
		System.out.println(weather);

		return modelMapper.map(weather, WeatherResponseWithoutCurrentConditions.class);
	}

	@Override
	public WeatherResponse getByLocationAndDynamicPeriod(String location, String period) {
		System.out.println(
				"WeatherServiceImpl.getByLocationAndDynamicPeriod() | location: " + location + " | period: " + period);
		validateCity(location);
		String url = visualWeatherBaseUrl + location + "/" + period + "?key=" + visualWeatherApiKey
				+ visualWeatherDataUrl;
		HttpRequest request = prepareHttpRequest(url);
		ResponseEntity<String> response = httpServiceEngine.makeHttpRequest(request);
		System.out.println(response.getBody());

		Weather weather = gson.fromJson(response.getBody(), Weather.class);
		System.out.println(weather);
		return modelMapper.map(weather, WeatherResponse.class);
	}

	@Override
	public WeatherResponse getByLocationAndDateTime(String location, String date, String time) {
		System.out.println("WeatherServiceImpl.getByLocationAndDateTime() | location : " + location + " | date: " + date
				+ " | time: " + time);
		validateCity(location);
		validateDate(date);
		validateTime(time);
		String url = visualWeatherBaseUrl + location + "/" + date + "T" + time + "?key=" + visualWeatherApiKey
				+ visualWeatherDataUrl;
		HttpRequest request = prepareHttpRequest(url);
		ResponseEntity<String> response = httpServiceEngine.makeHttpRequest(request);
		System.out.println(response.getBody());

		Weather weather = gson.fromJson(response.getBody(), Weather.class);
		System.out.println(weather);
		return modelMapper.map(weather, WeatherResponse.class);
	}

	@Override
	public WeatherResponse getByLocationAndDateRange(String location, String from, String to) {
		System.out.println("WeatherServiceImpl.getByLocationAndDateTime() | location : " + location + " | from: " + from
				+ " | to: " + to);
		validateCity(location);
		validateDate(from);
		validateDate(to);
		validateDateDifference(from, to);
		String url = visualWeatherBaseUrl + location + "/" + from + "/" + to + "?key=" + visualWeatherApiKey
				+ visualWeatherDataUrl;
		HttpRequest request = prepareHttpRequest(url);
		ResponseEntity<String> response = httpServiceEngine.makeHttpRequest(request);
		System.out.println(response.getBody());

		Weather weather = gson.fromJson(response.getBody(), Weather.class);
		System.out.println(weather);
		return modelMapper.map(weather, WeatherResponse.class);
	}

	private HttpRequest prepareHttpRequest(String url) {
		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.GET);
		request.setRequest(null);
		request.setUrl(url);
		return request;
	}

	private boolean validateCity(String location) {
		if (location == null || location.equals("")) {
			throw new WeatherException(ErrorCodeEnum.NULL_OR_EMPTY_FIELD.getCode(),
					ErrorCodeEnum.NULL_OR_EMPTY_FIELD.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (!location.matches("^[a-zA-Z]+$")) {
			throw new WeatherException(ErrorCodeEnum.INVALID_INPUT.getCode(), ErrorCodeEnum.INVALID_INPUT.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		return true;
	}

	private boolean validateDate(String date) {
		if (date == null || date.equals("")) {
			throw new WeatherException(ErrorCodeEnum.NULL_OR_EMPTY_FIELD.getCode(),
					ErrorCodeEnum.NULL_OR_EMPTY_FIELD.getMessage(), HttpStatus.BAD_REQUEST);
		}

		if (!date.matches("^(\\d{4})-(\\d{2})-(\\d{2})$")) {
			throw new WeatherException(ErrorCodeEnum.INVALID_DATE_FORMAT.getCode(),
					ErrorCodeEnum.INVALID_DATE_FORMAT.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return true;
	}

	private boolean validateTime(String time) {
		if (time == null || time.equals("")) {
			throw new WeatherException(ErrorCodeEnum.NULL_OR_EMPTY_FIELD.getCode(),
					ErrorCodeEnum.NULL_OR_EMPTY_FIELD.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// Regex to match a valid time in the format HH:mm:ss
		if (!time.matches("^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$")) {
			throw new WeatherException(ErrorCodeEnum.INVALID_TIME_FORMAT.getCode(),
					ErrorCodeEnum.INVALID_TIME_FORMAT.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return true;
	}

	private void validateDateDifference(String from, String to) {
		// Define the date format
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Parse the 'from' and 'to' strings to LocalDate
		LocalDate fromDate = LocalDate.parse(from, dateFormatter);
		LocalDate toDate = LocalDate.parse(to, dateFormatter);

		// Get the current date
		LocalDate currentDate = LocalDate.now();

		// Calculate the difference in days from the 'from' date to the current date
		long daysFromNow = ChronoUnit.DAYS.between(fromDate, currentDate);
		System.out.println("++++++++++++++ Days: " + daysFromNow);
		// Check if the 'from' date is more than 365 days from the current date
		if (daysFromNow < 0 || daysFromNow > AppConstants.DATE_RANGE_DAYS) {
			System.out.println("asdlkaskldasdlknasdlex");
			throw new WeatherException(ErrorCodeEnum.INVALID_INPUT.getCode(),
					"The 'from' date must not be more than one month from the current date.", HttpStatus.BAD_REQUEST);
		}

		// Check if the 'to' date is before the 'from' date
		if (toDate.isAfter(currentDate)) {
			throw new WeatherException(ErrorCodeEnum.INVALID_INPUT.getCode(),
					"The 'to' date must be before the Today's date.", HttpStatus.BAD_REQUEST);
		}
	}
}
