package com.wai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wai.constants.EndPoints;
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

	@GetMapping(EndPoints.NOW)
	public String getNow() {
		System.out.println("WeatherController.getNow()");
		return "NOW";
	}

}
