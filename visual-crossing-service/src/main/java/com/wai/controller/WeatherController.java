package com.wai.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wai.constants.EndPoints;
import com.wai.pojo.WeatherResponse;
import com.wai.service.interfaces.WeatherService;

// currentLocation + Now -- return one hour weather
// currentLocation + Date -- return 24 hrs weather 
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

}
