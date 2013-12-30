package com.expedia.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.expedia.weather.delegate.WundergroundDelegate;
import com.expedia.weather.web.WeatherValidator;

@Configuration
public class AppConfig {
	private @Value("#{appProperties.apiKey}") String apiKey;

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/pages/");
		resolver.setSuffix(".jsp");

		return resolver;
	}
	
	@Bean(name="messageSource")
	public ResourceBundleMessageSource weatherProperties() {
		ResourceBundleMessageSource properties = new ResourceBundleMessageSource();
		properties.setBasename("com/expedia/weather/properties/Weather");
		
		return properties;
	}

	@Bean
	public WundergroundDelegate wundergroundDelegate() {
		return new WundergroundDelegate(apiKey);
	}
	
	@Bean
	public WeatherValidator weatherValidator() {
		return new WeatherValidator();
	}
}
