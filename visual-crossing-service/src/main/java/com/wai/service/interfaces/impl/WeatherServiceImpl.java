package com.wai.service.interfaces.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
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
		String url = visualWeatherBaseUrl + location + "/" + date + "?key=" + visualWeatherApiKey
				+ visualWeatherDataUrl;
		HttpRequest request = prepareHttpRequest(url);
		ResponseEntity<String> response = httpServiceEngine.makeHttpRequest(request);
		System.out.println(response.getBody());

		Weather weather = gson.fromJson(response.getBody(), Weather.class);
		System.out.println(weather);

		return modelMapper.map(weather, WeatherResponseWithoutCurrentConditions.class);
	}

	private HttpRequest prepareHttpRequest(String url) {
		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.GET);
		request.setRequest(null);
		request.setUrl(url);
		return request;
	}

	@Override
	public WeatherResponse getByLocationAndDynamicPeriod(String location, String period) {
		System.out.println(
				"WeatherServiceImpl.getByLocationAndDynamicPeriod() | location: " + location + " | period: " + period);
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
		String url = visualWeatherBaseUrl + location + "/" + from + "/" + to + "?key=" + visualWeatherApiKey
				+ visualWeatherDataUrl;
		HttpRequest request = prepareHttpRequest(url);
		ResponseEntity<String> response = httpServiceEngine.makeHttpRequest(request);
		System.out.println(response.getBody());

		Weather weather = gson.fromJson(response.getBody(), Weather.class);
		System.out.println(weather);
		return modelMapper.map(weather, WeatherResponse.class);
	}

}
