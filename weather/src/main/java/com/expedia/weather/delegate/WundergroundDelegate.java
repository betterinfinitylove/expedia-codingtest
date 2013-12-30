package com.expedia.weather.delegate;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.expedia.weather.exception.WeatherException;
import com.expedia.weather.model.Weather;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WundergroundDelegate {
	
	private String apiKey;
	
	public WundergroundDelegate(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public Weather getWeatherBy(String zipCode) throws WeatherException {
		
		Weather weather = null;
		
		CloseableHttpResponse response = null;
		
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(getWundergroundURI(zipCode));
			response = httpClient.execute(httpGet);
			
			HttpEntity entity = response.getEntity();
			
			if(entity != null) {
				InputStream is = entity.getContent();
				StringWriter writer = new StringWriter();
				IOUtils.copy(is, writer);
				
				if(writer.toString().length() > 0) {
					weather = translateResponseToModel(writer.toString());
				}
			}
			
		} catch (ClientProtocolException e) { 
			e.printStackTrace();
			
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			
			throw new RuntimeException(e.getMessage());
		} finally {
			if(response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
					
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		
		return weather;
	}
	
	private URI getWundergroundURI(String zipCode)  { 
		URI uri = null;
		try {
			uri = new URIBuilder()
						.setScheme("http")
						.setHost("api.wunderground.com")
						.setPath("/api/" + apiKey + "/conditions/q/" + zipCode + ".json")
						.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			
			throw new RuntimeException(e.getMessage());
		}
		
		return uri;
	}
	
	private Weather translateResponseToModel(String response) throws WeatherException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode tree = objectMapper.readTree(response);
			
			if(!tree.path("response").path("error").isMissingNode()) {
				String errorType = objectMapper.treeToValue(tree.path("response").path("error").get("type"), String.class);
				String errorDescription = objectMapper.treeToValue(tree.path("response").path("error").get("description"), String.class);
				
				throw new WeatherException(errorType, errorDescription); 
			}
			
			Weather weather = objectMapper.treeToValue(tree.path("current_observation").get("display_location"), Weather.class);
			float tempF = objectMapper.treeToValue(tree.path("current_observation").get("temp_f"), Float.class);
			
			weather.setFahrenheitTemp(tempF);
			
			return weather;
			
		} catch (JsonParseException e) {
			e.printStackTrace();
			
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			
			throw new RuntimeException(e.getMessage());
		}
	}
}
