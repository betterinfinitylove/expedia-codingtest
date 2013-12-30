package com.expedia.weather.service;

import com.expedia.weather.exception.WeatherException;
import com.expedia.weather.model.Weather;

public interface WeatherService {

	Weather findWeatherBy(String zipCode) throws WeatherException;
}
