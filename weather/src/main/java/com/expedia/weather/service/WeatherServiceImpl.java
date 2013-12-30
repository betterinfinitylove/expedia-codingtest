package com.expedia.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expedia.weather.delegate.WundergroundDelegate;
import com.expedia.weather.exception.WeatherException;
import com.expedia.weather.model.Weather;

@Service
public class WeatherServiceImpl implements WeatherService {
	
	@Autowired
	private WundergroundDelegate wundergroundDelegate;

	public Weather findWeatherBy(String zipCode) throws WeatherException {
		return wundergroundDelegate.getWeatherBy(zipCode);
	}

}
