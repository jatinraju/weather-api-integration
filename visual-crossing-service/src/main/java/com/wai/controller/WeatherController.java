package com.wai.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wai.constants.EndPoints;
import com.wai.pojo.WeatherResponse;
import com.wai.pojo.WeatherResponseWithoutCurrentConditions;
import com.wai.service.interfaces.WeatherService;

@RestController
@RequestMapping(EndPoints.API_V1)
public class WeatherController {

	private WeatherService weatherService;

	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@GetMapping(EndPoints.TEST)
	public String getHello() {
		return "This is a Test";
	}

	// Returns Current Conditions + 15 days advance
	@GetMapping(EndPoints.LOCATION)
	public ResponseEntity<WeatherResponse> getByLocation(@PathVariable("location") String location) {
		WeatherResponse weatherResponse = weatherService.getByLocation(location);
		return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
	}

	// Returns dynamic period(today, yesterday, last30days, tomorrow etc..) weather
	@GetMapping(EndPoints.LOCATION_DYNAMIC_PERIOD)
	public ResponseEntity<WeatherResponse> getByLocationAndDynamicPeriod(@PathVariable("location") String location,
			@PathVariable("period") String period) {
		WeatherResponse weatherResponse = weatherService.getByLocationAndDynamicPeriod(location, period);
		return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
	}

	// Returns specific date weather
	@GetMapping(EndPoints.LOCATION_DATE)
	public ResponseEntity<WeatherResponseWithoutCurrentConditions> getByLocationAndDate(
			@PathVariable("location") String location, @PathVariable("date") String date) {
		System.out.println("WeatherController.getByLocationAndDate() | location: " + location + " | date: " + date);
		WeatherResponseWithoutCurrentConditions weatherResponse = weatherService.getByLocationAndDate(location, date);
		return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
	}

	// Returns specific date + specific hour weather
	@GetMapping(EndPoints.LOCATION_DATE_TIME)
	public ResponseEntity<WeatherResponse> getByLocationAndDateTime(@PathVariable("location") String location,
			@PathVariable("date") String date, @PathVariable("time") String time) {
		System.out.println("WeatherController.getByLocationAndDateTime() | location: " + location + " | date: " + date
				+ " | time: " + time);
		WeatherResponse weatherResponse = weatherService.getByLocationAndDateTime(location, date, time);
		return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
	}

	// Returns weather within specified date range
	@GetMapping(EndPoints.LOCATION_DATE_RANGE)
	public ResponseEntity<WeatherResponse> getByLocationAndDateRange(@PathVariable("location") String location,
			@PathVariable("from") String from, @PathVariable("to") String to) {
		System.out.println("WeatherController.getByLocationAndDateRange() | location: " + location + "| from: " + from
				+ " | to: " + to);
		WeatherResponse weatherResponse = weatherService.getByLocationAndDateRange(location, from, to);
		return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
	}
}
