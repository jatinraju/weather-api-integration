package com.wai.service.interfaces;

import com.wai.pojo.WeatherResponse;
import com.wai.pojo.WeatherResponseWithoutCurrentConditions;

public interface WeatherService {

	WeatherResponse getByLocation(String location);

	WeatherResponseWithoutCurrentConditions getByLocationAndDate(String location, String date);

	WeatherResponse getByLocationAndDynamicPeriod(String location, String period);

	WeatherResponse getByLocationAndDateTime(String location, String date, String time);

	WeatherResponse getByLocationAndDateRange(String location, String from, String to);
}
