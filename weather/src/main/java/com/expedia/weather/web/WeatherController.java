package com.expedia.weather.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.expedia.weather.exception.WeatherException;
import com.expedia.weather.model.Weather;
import com.expedia.weather.service.WeatherService;

@Controller
@RequestMapping("/")
public class WeatherController {

	private static final String SHOW_RESULT_ATTR = "showResult";
	
	@Autowired
	private WeatherService weatherService;
	
	@Autowired
	private WeatherValidator weatherValidator;
	
	private boolean showResult;

	@RequestMapping(method = RequestMethod.GET)
	public String get(ModelMap model) {
		Weather input = new Weather();

		model.addAttribute(SHOW_RESULT_ATTR, showResult);
		model.addAttribute("weather", input);

		return "weathers";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String find(@ModelAttribute("weather") Weather weather, BindingResult result, ModelMap model) {
		showResult = false;
		
		weatherValidator.validate(weather, result);
		
		try {
			
			if(!result.hasErrors()) {
				Weather found = weatherService.findWeatherBy(weather.getZip());
				
				model.addAttribute("result", found);
				
				showResult = true;
			} 
		} catch (WeatherException e) {
			
			if(StringUtils.equalsIgnoreCase("querynotfound", e.getErrorType())) {
				result.rejectValue("zip", "zipCode.notFound");
			} else {
				throw new IllegalArgumentException("Unknown error type");
			}
			
		} 
		
		model.addAttribute(SHOW_RESULT_ATTR, showResult);
		
		return "weathers";
	}
}
