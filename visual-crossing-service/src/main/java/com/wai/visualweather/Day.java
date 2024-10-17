package com.wai.visualweather;

import lombok.Data;

@Data
public class Day {

	private String datetime;
	private double tempmax;
	private double tempmin;
	private double temp;
	private double feelslike;
	private double humidity;
	private double precip;
	private double windspeed;
	private double winddir;
	private double pressure;
	private double cloudcover;
	private String conditions;
	private String description;
	private String sunrise;
	private String sunset;

}
