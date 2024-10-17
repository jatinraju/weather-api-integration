package com.wai.visualweather;

import lombok.Data;

@Data
public class CurrentConditions {

	private String datetime;
	private double temp;
	private double feelslike;
	private double humidity;
	private double precip;
	private double windspeed;
	private double winddir;
	private double pressure;
	private double cloudcover;
	private String conditions;

}
