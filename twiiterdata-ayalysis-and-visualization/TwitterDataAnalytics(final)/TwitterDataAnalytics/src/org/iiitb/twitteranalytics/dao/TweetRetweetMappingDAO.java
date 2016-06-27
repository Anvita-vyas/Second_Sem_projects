package org.iiitb.twitteranalytics.dao;

import java.util.Date;
import java.util.List;

import org.iiitb.twitteranalytics.model.CountryTweet;
import org.iiitb.twitteranalytics.model.TweetRetweetMapping;

public interface TweetRetweetMappingDAO {
	//void saveTwitterData(TweetRetweetMapping tweetRetweetMpgFormBean)throws Exception;
	CountryTweet getTweetDtls(String country, String clasification,Date startDate, Date endDate,String handler) throws Exception;

	List<CountryTweet> getRetweetsDtls(String country,String clasification, Date startDate, Date endDate,String handler)throws Exception;
	
	//void saveCountryData(String country, String lat, String lon)throws Exception;
	CountryTweet getCountryCordinates(String country);
}