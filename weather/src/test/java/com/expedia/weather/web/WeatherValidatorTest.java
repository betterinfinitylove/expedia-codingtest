package com.expedia.weather.web;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import com.expedia.weather.model.Weather;

@RunWith(MockitoJUnitRunner.class)
public class WeatherValidatorTest {

	@Mock
	private BindingResult result;

	private Weather weather;
	
	private WeatherValidator validator;
	
	@Before
	public void setup() {
		weather = new Weather();
		validator = new WeatherValidator();
	}
	
	@Test
	public void testValidateZipCode_Pass() {
		weather.setZip("12345");
		
		validator.validate(weather, result);
		
		verifyZeroInteractions(result);
	}
	
	@Test
	public void testValidateZipCode_Fail() {
		weather.setZip("abcde");
		
		validator.validate(weather, result);
		
		verify(result, times(1)).rejectValue("zip", "zipCode.invalidFormat"); 
	}
}
