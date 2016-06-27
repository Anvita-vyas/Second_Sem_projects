package org.iiitb.twitteranalytics.model;

import java.util.Date;


public class TweetRetweetMapping {
	private Long id;
	private String tweetType;
	private String originCountry;
	private String tweetId;
	private String refTweetId;
	private Date tweetDate;
	private String classification1;
	//private String classification2;
	//private String classification3;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTweetType() {
		return tweetType;
	}
	public void setTweetType(String tweetType) {
		this.tweetType = tweetType;
	}
	public String getOriginCountry() {
		return originCountry;
	}
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}
	public String getTweetId() {
		return tweetId;
	}
	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}
	public String getRefTweetId() {
		return refTweetId;
	}
	public void setRefTweetId(String refTweetId) {
		this.refTweetId = refTweetId;
	}
	public Date getTweetDate() {
		return tweetDate;
	}
	public void setTweetDate(Date tweetDate) {
		this.tweetDate = tweetDate;
	}
	public String getClassification1() {
		return classification1;
	}
	public void setClassification1(String classification1) {
		this.classification1 = classification1;
	}
//	public String getClassification2() {
//		return classification2;
//	}
//	public void setClassification2(String classification2) {
//		this.classification2 = classification2;
//	}
//	public String getClassification3() {
//		return classification3;
//	}
//	public void setClassification3(String classification3) {
//		this.classification3 = classification3;
//	}
}
