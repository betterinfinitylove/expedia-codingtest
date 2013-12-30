package com.expedia.weather.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.BindingResult;

import com.expedia.weather.model.Weather;

public class WeatherValidator {
	
	public void validate(Weather input, BindingResult result) {
		Pattern pattern = Pattern.compile("\\d{5}");
	    Matcher matcher = pattern.matcher(input.getZip());
	    
	    if(!matcher.matches()) {
	    	result.rejectValue("zip", "zipCode.invalidFormat");
	    }
	}
	
}
