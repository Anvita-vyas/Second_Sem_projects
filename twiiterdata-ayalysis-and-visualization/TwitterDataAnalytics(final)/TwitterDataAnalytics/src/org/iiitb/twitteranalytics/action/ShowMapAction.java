package org.iiitb.twitteranalytics.action;

import java.text.SimpleDateFormat;
import java.util.List;

import org.iiitb.twitteranalytics.dao.TweetRetweetMappingDAO;
import org.iiitb.twitteranalytics.dao.impl.TweetRetweetMappingDAOImpl;
import org.iiitb.twitteranalytics.model.CountryTweet;

import com.opensymphony.xwork2.ActionSupport;


public class ShowMapAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private String country;
	private String classification;
	private String startDate;
	private String endDate;
	private CountryTweet tweetdtls;
	private List<CountryTweet> reTweetLst;
	private String handler;
	
	public String getRetweetsDtls() throws Exception{
		System.out.println("getRetweetDtls");
		TweetRetweetMappingDAO trm = new TweetRetweetMappingDAOImpl();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// setting reTweetLst
		setReTweetLst(trm.getRetweetsDtls(getCountry(), getClassification(), formatter.parse(getStartDate()), formatter.parse(getEndDate()),getHandler()));
		//setting tweetdtls  for getting origin country's lat and long position
		setTweetdtls(trm.getCountryCordinates(getCountry()));
		return SUCCESS;
	}
	
	public String getTweetsDtls() throws Exception{
		System.out.println("getTweetDtls");
		TweetRetweetMappingDAO trm = new TweetRetweetMappingDAOImpl();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		setting tweetdtls for getting country's lat and long from where Tweet have occured (clicked country)
		setTweetdtls(trm.getTweetDtls(getCountry(), getClassification(), formatter.parse(getStartDate()), formatter.parse(getEndDate()),getHandler()));
		return SUCCESS;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public CountryTweet getTweetdtls() {
		return tweetdtls;
	}

	public void setTweetdtls(CountryTweet tweetdtls) {
		this.tweetdtls = tweetdtls;
	}

	public List<CountryTweet> getReTweetLst() {
		return reTweetLst;
	}

	public void setReTweetLst(List<CountryTweet> reTweetLst) {
		//System.out.println("retweet"+ reTweetLst);
		this.reTweetLst = reTweetLst;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		//System.out.println("handler" + handler);
		this.handler = handler;
	}
}