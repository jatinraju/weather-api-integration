package com.wai.visualweather;

import java.util.List;

import lombok.Data;

@Data
public class Weather {

	private double latitude;
	private double longitude;
	private String resolvedAddress;
	private String timezone;
	private List<Day> days;
	private CurrentConditions currentConditions;

}
