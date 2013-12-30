package com.expedia.weather.exception;


public class WeatherException extends Exception {

	private static final long serialVersionUID = 4662419560755218533L;

	private String errorType;
	private String errorDescription;

	public WeatherException(String errorType, String errorDescription) {
		this.errorType = errorType;
		this.errorDescription = errorDescription;
	}

	public String getErrorType() {
		return errorType;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

}
