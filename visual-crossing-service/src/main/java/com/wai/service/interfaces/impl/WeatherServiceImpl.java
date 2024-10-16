package com.wai.service.interfaces.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.wai.pojo.WeatherResponse;
import com.wai.service.http.HttpRequest;
import com.wai.service.http.HttpServiceEngine;
import com.wai.service.interfaces.WeatherService;

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

	@Override
	public WeatherResponse getByLocation(String location) {
		System.out.println("WeatherServiceImpl.getByLocation() | location : " + location);

		// Preparing HttpRequest
		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.GET);
		request.setRequest(null);
		String url = visualWeatherBaseUrl + location + "?key=" + visualWeatherApiKey + visualWeatherDataUrl;
		request.setUrl(url);
		ResponseEntity<String> response = httpServiceEngine.makeHttpRequest(request);
		System.out.println(response.getBody());

		WeatherResponse weatherResponse = gson.fromJson(response.getBody(), WeatherResponse.class);
		System.out.println(weatherResponse);
		return weatherResponse;

	}

}
