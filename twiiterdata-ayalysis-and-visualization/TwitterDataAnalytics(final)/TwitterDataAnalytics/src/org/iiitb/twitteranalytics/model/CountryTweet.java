package org.iiitb.twitteranalytics.model;

public class CountryTweet {
	private String Country;
	private Integer noOfTweets;
	private String latitude;
	private String longitude;
	
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public Integer getNoOfTweets() {
		return noOfTweets;
	}
	public void setNoOfTweets(Integer noOfTweets) {
		this.noOfTweets = noOfTweets;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
