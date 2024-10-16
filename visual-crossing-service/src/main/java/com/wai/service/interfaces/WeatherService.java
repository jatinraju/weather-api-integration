package com.wai.service.interfaces;

import com.wai.pojo.WeatherResponse;

public interface WeatherService {

	WeatherResponse getByLocation(String location);
}
