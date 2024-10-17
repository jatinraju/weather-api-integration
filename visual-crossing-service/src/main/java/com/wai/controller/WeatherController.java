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

// currentLodation -- return current + 15days weather
// currentLocation today -- return current day + current one hour weather
// currentLocation + Date -- specific date weather 
// currentLocation + Date + exact time -- return one hour weather

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

	@GetMapping(EndPoints.LOCATION)
	public ResponseEntity<WeatherResponse> getByLocation(@PathVariable("location") String location) {
		WeatherResponse weatherResponse = weatherService.getByLocation(location);
		return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
	}

	@GetMapping(EndPoints.LOCATION_DYNAMIC_PERIOD)
	public ResponseEntity<WeatherResponse> getByLocationAndDynamicPeriod(@PathVariable("location") String location,
			@PathVariable("period") String period) {
		WeatherResponse weatherResponse = weatherService.getByLocationAndDynamicPeriod(location, period);
		return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
	}

	@GetMapping(EndPoints.LOCATION_DATE)
	public ResponseEntity<WeatherResponseWithoutCurrentConditions> getByLocationAndDate(
			@PathVariable("location") String location, @PathVariable("date") String date) {
		System.out.println("WeatherController.getByLocationAndDate() | location: " + location + " | date: " + date);
		WeatherResponseWithoutCurrentConditions weatherResponse = weatherService.getByLocationAndDate(location, date);
		return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
	}

}
