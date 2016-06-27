package org.iiitb.twitteranalytics.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.iiitb.twitteranalytics.util.ConnectionPool;

import twitter4j.ResponseList;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import com.opensymphony.xwork2.ActionSupport;

public class Twitter extends ActionSupport {
	private static final long serialVersionUID = 1L;
	String filePath="";
	private File myFile;
	
    public Twitter() {
        super();
        // TODO Auto-generated constructor stub
    }

	public String execute(){
		System.out.println("Src File name: " + myFile);
		doWork(); 
		return SUCCESS;
	}
	public void doWork(){
		System.out.println("i'm here");
		Connection conn = null;
	     try {
			conn=ConnectionPool.db_connect();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	       
	    // loads com.mysql.jdbc.Driver into memory
	        try
	        {
	        
	            
	       Classification classify=new Classification();
	        Statement stmt = (Statement) conn.createStatement();
	          
	      
	         // FileInputStream in = new FileInputStream("J:/IIITB/Semester2/Project_elective/Eclipse/TwitterDataAnalytics/WebContent/inp1.txt");
	        FileInputStream in = new FileInputStream(myFile);
	           BufferedReader br=new BufferedReader(new InputStreamReader(in));
	           String line=null;
	           while((line=br.readLine())!=null)
	           {
	               System.out.println(line); 
	          
	                ConfigurationBuilder cb = new ConfigurationBuilder();
	                cb.setDebugEnabled(true)
	               .setOAuthConsumerKey("18tG6Z8UwoOmceIDhALTD8UHQ")
	               .setOAuthConsumerSecret("KbKHeVKXl3ItcEI4afTLp08h7HKcjhNSDcQpJ10ti4ijcMkKlN")
	               .setOAuthAccessToken("4788856057-1cPr4YTgC5wU8H1UsppjG8vVH5eh3EJjsR51WmC")
	               .setOAuthAccessTokenSecret("yITRVUuoQWydursccAgAs3Grf60D3w1EJObZpnTABDZCo");
	                int count=0;
	        
	           TwitterFactory tf = new TwitterFactory(cb.build());
	           twitter4j.Twitter twitter = tf.getInstance();
	        
	           ResponseList<twitter4j.Status> status = twitter.getUserTimeline(line);
	         String country="";
	           
	           for(twitter4j.Status st : status)
	           {
                     String type="tweet";
	                 String t=st.getText();
	                 String tweet=t.replaceAll("\"", "\'");
	                 System.out.println("tweet---"+tweet);
	                 String classification=classify.tweet_classify(tweet);
	                 if(classification.equals(""))
	                {
	                    continue;
	                }
	               
	                String place=st.getUser().getLocation();
	                if(place == null)
	                {
	                    continue;
	                }
	                System.out.println("place="+place);
	                
	                location loc=new location();
	                if(count == 0)
	                {
	                    
	                country=loc.getCountry(place);
	                
	                count=1;
	                System.out.println(country+" "+count);
	                }
	                if(country.equals(""))
	                {
	                    continue;
	                }
	                
	                java.sql.Date tweet_date= new java.sql.Date (st.getCreatedAt().getTime());
	                String tweet_id=String.valueOf(st.getId());
	               
	                String name=st.getUser().getName();
	                String handler=st.getUser().getScreenName();
	                
	               String sql= "INSERT IGNORE INTO tweet_retweet_mpg (tweet_type,origin_country,tweet_id,tweet_date,classification,handler,user_name)"+" VALUES (?,?,?,?,?,?,?)";
	                    PreparedStatement ps=(PreparedStatement) conn.prepareStatement(sql);
	                    ps.setString(1,type);
	                    ps.setString(2,country);
	                    ps.setString(3,tweet_id);
	                    ps.setDate(4,tweet_date);
	                    ps.setString(5,classification);
	                    ps.setString(6, handler);
	                    ps.setString(7,name);
	                    ps.executeUpdate();
	                    
	               ResponseList<twitter4j.Status> rstatus = twitter.getRetweets(st.getId());
	               String country1="";
	               for(twitter4j.Status rst : rstatus)
	               {
	                  type="retweet";
	                    String place1=rst.getUser().getLocation();
	                      if(place1 == null)
	                    {
	                    continue;
	                    }
	                
	                    System.out.println(rst.getId()+"--"+rst.getUser().getName()+"--"+rst.getUser().getLocation()+"---"+rst.getGeoLocation());
	                     country1=loc.getCountry(place1);
	                if(country1.equals(""))
	                {
	                    continue;
	                }
	                java.sql.Date retweet_date= new java.sql.Date (rst.getCreatedAt().getTime());
	                String retweet_id=String.valueOf(rst.getId());
	                
	               String name1=rst.getUser().getName();
	               String handler1=rst.getUser().getScreenName();
	                
	                sql= "INSERT IGNORE INTO tweet_retweet_mpg (tweet_type,origin_country,tweet_id,tweet_date,classification,handler,user_name,ref_tweet_id)"+" VALUES (?,?,?,?,?,?,?,?)";
	                    ps=(PreparedStatement) conn.prepareStatement(sql);
	                    ps.setString(1,type);
	                    ps.setString(2,country1);
	                    ps.setString(3,retweet_id);
	                    ps.setDate(4,retweet_date);
	                    ps.setString(5,classification);
	                    ps.setString(6, handler1);
	                    ps.setString(7,name1);
	                    ps.setString(8,tweet_id);
	                    
	                    ps.executeUpdate();
	               
	                   //System.out.println(rst.getId()+"--"+rst.getUser().getName()+"--"+rst.getUser().getLocation()+"---"+rst.getGeoLocation());
	               }
	               
	              }
	          }
	                    
	        }
	        catch(Exception e)
	        {
	        System.out.println(e.getMessage());
	        }
	        //db_conn.conn_close();
	}
	public File getMyFile() {
	      return myFile;
	   }
	   public void setMyFile(File myFile) {
	      this.myFile = myFile;
	   }
}
