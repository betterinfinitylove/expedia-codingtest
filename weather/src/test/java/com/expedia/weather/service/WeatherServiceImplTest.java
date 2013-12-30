package com.expedia.weather.service;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.expedia.weather.delegate.WundergroundDelegate;
import com.expedia.weather.exception.WeatherException;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceImplTest {

	@Mock
	private WundergroundDelegate wundergroundDelegate;
	
	@InjectMocks
	private WeatherServiceImpl service;
	
	public void setup() {
		service = new WeatherServiceImpl();
	}
	
	@Test
	public void testfindWeatherByZipCode() {
		String zipCode = "123";
		try {
			service.findWeatherBy(zipCode);
			
			verify(wundergroundDelegate).getWeatherBy(zipCode);
			
			verifyNoMoreInteractions(wundergroundDelegate);
		} catch (WeatherException e) {
			fail();
		}
	}
}
