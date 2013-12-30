package com.expedia.weather.delegate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.expedia.weather.exception.WeatherException;
import com.expedia.weather.model.Weather;

@RunWith(MockitoJUnitRunner.class)
public class WundergroundDelegateTest {

	private WundergroundDelegate delegate;

	@Before
	public void setup() throws FileNotFoundException, IOException, URISyntaxException {
		File file = new File(this.getClass().getClassLoader().getResource("com/expedia/weather/config/app.properties").toURI()); 
		
		Properties properties = new Properties();
		properties.load(new FileReader(file)); 
		
		String apiKey = properties.get("apiKey").toString();
		
		delegate = new WundergroundDelegate(apiKey);
	}

	@Test
	public void testGetWeatherByZipCode() {
		String zipCode = "33606";

		Weather weather;
		try {
			weather = delegate.getWeatherBy(zipCode);

			assertThat(weather, notNullValue());
			assertThat(weather.getZip(), equalTo(zipCode));
		} catch (WeatherException e) {
			e.printStackTrace();
			fail();
		}

	}
	
	@Test
	public void testGetWeatherByZipCode_ZipCodeNotFound() {
		String zipCode = "10000";

		try {
			delegate.getWeatherBy(zipCode);

			fail();
		} catch (WeatherException e) {
			assertThat(e.getErrorType(), equalTo("querynotfound"));
		}
	}
}
