package org.iiitb.twitteranalytics.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.iiitb.twitteranalytics.dao.TweetRetweetMappingDAO;
import org.iiitb.twitteranalytics.model.CountryTweet;
import org.iiitb.twitteranalytics.model.TweetRetweetMapping;
import org.iiitb.twitteranalytics.util.ConnectionPool;

 
public class TweetRetweetMappingDAOImpl implements TweetRetweetMappingDAO {

	ConnectionPool connect=new ConnectionPool();
	//below method gives no. of tweets from that country(clicked) and its lat,long position
	public CountryTweet getTweetDtls(String country, String classification,Date startDate, Date endDate,String handler) throws Exception {
		Connection conn = null;
		
		Statement stmt = null;
		CountryTweet countryTweet = new CountryTweet();
		String type = "tweet";
		try {
			//conn = ConnectionPool.getConnection();
			conn = connect.db_connect();
			stmt = conn.createStatement(); 
			StringBuilder sb = new StringBuilder();
			sb.append("select count(t.id) as noOfTweets, c.lat as lat, c.lon as lon from twitter_data.tweet_retweet_mpg t, twitter_data.country_location c where c.country = t.origin_country ");
	
			sb.append(" and t.tweet_type = '" + type + "'");
			if (country != null) {
				sb.append(" and t.origin_country like '" + country + "%'");
			}
			if (startDate != null) {
				sb.append(" and t.tweet_date >= '" + new java.sql.Date(startDate.getTime()));
			}
			if (endDate != null) {
				sb.append("' and t.tweet_date <= '" + new java.sql.Date(endDate.getTime()));
			}
			if (classification != null) {
				sb.append("' and  t.classification = '" + classification + "'");
			}
			if(!handler.isEmpty()){
				sb.append(" and  t.handler = '" + handler + "'");
			}
			sb.append(" ;");
			System.out.println(sb.toString());
			ResultSet rs = stmt.executeQuery(sb.toString());
	
			// Extract data from result set
	
			while (rs.next()) {
				// Retrieve by column name
				countryTweet.setNoOfTweets(rs.getInt("noOfTweets"));
				countryTweet.setLatitude(rs.getString("lat"));
				countryTweet.setLongitude(rs.getString("lon"));
			}
			countryTweet.setCountry(country);
			// Clean-up environment
			rs.close();
			stmt.close();
			//ConnectionPool.freeConnection(conn);
			//connect.conn_close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
//			try {
//				if (conn != null)
//					conn.close();
//			} catch (SQLException se) {
//				se.printStackTrace();
//			}
//			try{
//				connect.conn_close();
//			}
//			catch (SQLException se) {
//				se.printStackTrace();
//			}
		}
		return countryTweet;
	}

	@Override
	public List<CountryTweet> getRetweetsDtls(String country,String classification, Date startDate, Date endDate,String handler)
			throws Exception {
		Connection conn = null;
		Statement stmt = null;
		List<String> tweetIdLst = new ArrayList<String>();
		List<CountryTweet> countryTweetLst = new ArrayList<CountryTweet>();
		String type = "tweet";
		try {
			
			//conn = ConnectionPool.getConnection();
			conn=connect.db_connect();

			// Execute a query
			stmt = conn.createStatement();

			StringBuilder sb = new StringBuilder();
			sb.append("select t.tweet_Id as tweet_Id from twitter_data.tweet_retweet_mpg t where ");

			sb.append(" t.tweet_type = '" + type + "'");
			if (country != null) {
				sb.append(" and t.origin_country like '" + country + "%%'");
			}
			if(!handler.isEmpty()){
				sb.append(" and t.handler = '" + handler + "' ");
			}
				
			sb.append(" ;");
			System.out.println(sb.toString());
			ResultSet rs = stmt.executeQuery(sb.toString());

			// Extract data from result set

			while (rs.next()) {
				// Retrieve by column name
				tweetIdLst.add(rs.getString("tweet_Id"));
			}
			// Clean-up environment
			rs.close();
			stmt.close();
			//conn.close();
			//connect.conn_close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
//			try {
//				if (conn != null)
//					conn.close();
//			} catch (SQLException se) {
//				se.printStackTrace();
//			}
//			try{
//				connect.conn_close();
//			}
//			catch (SQLException se) {
//				se.printStackTrace();
//			}
		}

		Connection con = null;
		Statement stm = null;

		type = "retweet";
		try {
			// Register JDBC driver
			//con = ConnectionPool.getConnection();
			con=connect.db_connect();

			// Execute a query
			stm = con.createStatement();

			StringBuilder sb = new StringBuilder();
			sb.append("select t.origin_country as country,count(t.id) as noOfTweets, c.lat as lat, c.lon as lon from twitter_data.tweet_retweet_mpg t, twitter_data.country_location c where c.country = t.origin_country ");

			sb.append(" and t.tweet_Type = '" + type + "'");
			if (startDate != null) {
				sb.append(" and t.tweet_date >= '" + new java.sql.Date(startDate.getTime()));
			}
			if (endDate != null) {
				sb.append("' and t.tweet_date <= '" + new java.sql.Date(endDate.getTime()));
			}
			if (classification != null) {
				System.out.println(classification);
				sb.append("' and ( t.classification = '" + classification + "')");
			}
			//if(tweetIdLst.isEmpty()){System.out.println("EMPTY LIST");}
			if (!tweetIdLst.isEmpty()) {
				//System.out.println("NOT EMPTY");
				sb.append(" and ref_tweet_id in ( '" + tweetIdLst.get(0));
				
				for(int i=1; i<tweetIdLst.size();i++){
					sb.append("' , '" + tweetIdLst.get(i));
				}
				sb.append("' ) " );
			}
			sb.append(" group by t.origin_country ;");
			System.out.println(sb.toString());
			ResultSet rs = stm.executeQuery(sb.toString());

			// Extract data from result set
			CountryTweet tweet = null;
			while (rs.next()) {
				// Retrieve by column name
				tweet = new CountryTweet();
				tweet.setCountry(rs.getString("country"));
				tweet.setNoOfTweets(rs.getInt("noOfTweets"));
				tweet.setLatitude(rs.getString("lat"));
				tweet.setLongitude(rs.getString("lon"));
				countryTweetLst.add(tweet);
				tweet = null;
			}
			if(tweetIdLst.isEmpty()){
				countryTweetLst=null;
			}
			// Clean-up environment
			rs.close();
			stm.close();
			//ConnectionPool.freeConnection(con);
			//connect.conn_close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stm != null)
					stm.close();
			} catch (SQLException se2) {
			}
//			try {
//				if (con != null)
//					con.close();
//			} catch (SQLException se) {
//				se.printStackTrace();
//			}
//			try{
//				connect.conn_close();
//			}
//			catch (SQLException se) {
//				se.printStackTrace();
//			}
		}
		return countryTweetLst;
	}
	
	public CountryTweet getCountryCordinates(String country){
		Connection conn = null;
		PreparedStatement psmt = null;
		CountryTweet ct = new CountryTweet();
		try{
			//conn = ConnectionPool.getConnection();
			conn=connect.db_connect();
			String sql = "select * from country_location where country = ?";
			psmt = conn.prepareStatement(sql); 
			psmt.setString(1,country);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()){
				ct.setLatitude(rs.getString("lat"));
				ct.setLongitude(rs.getString("lon"));
				ct.setCountry(rs.getString("country"));
			}
			psmt.close();
			//ConnectionPool.freeConnection(conn);
			//connect.conn_close();
		}catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (psmt != null)
					psmt.close();
			} catch (SQLException se2) {
			}
//			try {
//				if (conn != null)
//					conn.close();
//			} catch (SQLException se) {
//				se.printStackTrace();
//			}
//			try{
//				connect.conn_close();
//			}
//			catch (SQLException se) {
//				se.printStackTrace();
//			}
		}
		return ct;
	}
}