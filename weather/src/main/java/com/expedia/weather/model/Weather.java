package com.expedia.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
	private String city;
	private String state;
	private float fahrenheitTemp;
	private String zip;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@JsonProperty("state_name")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public float getFahrenheitTemp() {
		return fahrenheitTemp;
	}
	public void setFahrenheitTemp(float fahrenheitTemp) {
		this.fahrenheitTemp = fahrenheitTemp;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
