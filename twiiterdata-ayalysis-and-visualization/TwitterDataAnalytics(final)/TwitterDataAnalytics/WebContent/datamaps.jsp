<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html5>
<%@ page import="java.sql.*" %>
<%@ page import ="org.iiitb.twitteranalytics.util.ConnectionPool" %>


<%  
	Statement stmt = null;
	ResultSet rs=null;
	Connection conn = null;
	ConnectionPool connect1 = new ConnectionPool();
	try{
		//conn = ConnectionPool.getConnection();
		conn=connect1.db_connect();
		stmt = conn.createStatement(); 
		String query="select distinct classification from twitter_data.tweet_retweet_mpg";
		rs=stmt.executeQuery(query);
		//while(rs.next()){
//			System.out.println(rs.getString(1));
		//}
	}
	catch(SQLException e){
		e.printStackTrace();
	}
%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" lang="text/stylesheet" rel="stylesheet"/>
<link href="css/jquery-ui.min.css" lang="text/stylesheet" rel="stylesheet"/>
<link href="css/jquery-ui.theme.css" lang="text/stylesheet" rel="stylesheet"/>

<link href="css/spinner.css" lang="text/stylesheet" rel="stylesheet"/>

<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/d3.min.js"></script>
<script type="text/javascript" src="script/topojson.min.js"></script>
<script type="text/javascript" src="script/datamaps.world.min.js"></script>
<script type="text/javascript" src="script/jquery-ui.min.js"></script>
<script type="text/javascript" src="script/main.js"></script>
</head>

<body>
<div class="main" >
	<div class="header">
		<div class="head"><h1>Twitter Data Analytics</h1></div>
	</div>
	
	<div class="maps" id="m1">
		
		<div class="param">
			<ul>
			<li class="paramComponent">
				<label>Classification</label>
				<!-- <input type="text" id="classification"> -->
				<select name="classification" id="classification" style="width: 150px">
				<%
				String classification;
				while(rs.next()){
					classification=rs.getString(1);
					%><option value=<%=classification%>> <%=classification%> </option>
				<%} 
				//connect1.conn_close();%>
				</select>
			</li>
						
			<li class="paramComponent">
			<label>Start Date</label>
				<input type="text" id="dp1">
			</li>
			
			<li class="paramComponent">
				<label>End Date</label>
				<input type="text" id="dp2">
			</li>
			<br></br>
			<li class="paramComponent">
			<label>Tweet Handler</label>
				<input type="text" id="th">
			</li>
			<li class="paramComponent">
				<a href="datamaps.jsp"><input type="button" id="bt" value="Reset" ></input></a>
			</li>
			<li class="paramComponent">
				<a href="index.jsp"><input type="button" id="bt" value="Back to home" ></input></a>
			</li>
			</ul>
		</div>
		<div id="container" class="mapsContainer">
		</div>
	</div>

</div>
<!-- script for map drawing -->
<script>
		//https://maps.googleapis.com/maps/api/geocode/json?address=india&key=AIzaSyA7de32Xlv6WRqIuexW_ydpbsY1uNXbLjI
		//var arcs = [];
		var map = new Datamap({
			element : document.getElementById('container'),
			projection : 'mercator',
			
			geographyConfig : {
				popupOnHover : true,
				highlightOnHover : true
			},
			fills : {
			//	'USA' : '#e06000',
			//	'RUS' : '#ffbf00',
			//	'PRK' : '#ff7f0e',
			//	'PRC' : '#2ca02c',
			//	'IND' : '#00bfff',
			//	'GBR' : '#8c564b',
			//	'FRA' : '#d62728',
			//	'PAK' : '#00ff40',
			//	defaultFill : '#bbff33'
				'bubble_color': '#00bfff',
				defaultFill : 'grey'
			},
			data : {
				'RUS' : {
					fillKey : 'RUS'
				},
				'PRK' : {
					fillKey : 'PRK'
				},
				'PRC' : {
					fillKey : 'PRC'
				},
				'IND' : {
					fillKey : 'IND'
				},
				'GBR' : {
					fillKey : 'GBR'
				},
				'FRA' : {
					fillKey : 'FRA'
				},
				'PAK' : {
					fillKey : 'PAK'
				},
				'USA' : {
					fillKey : 'USA'
				}
			}
		});
		
		
		map.svg.selectAll('.datamaps-subunit').on('click',function(geography) {
				var loc = geography.properties.name;
				var sDate = $("#dp1").val();
				var eDate = $("#dp2").val();
				var text = $("#classification").val();
				
				var handler = $("#th").val();
				
				// trim()Removes leading and trailing whitespace
				if(text == null || $.trim(text).length == 0){
					alert("Please set Classification");
					return;
				}else if(sDate == null ||$.trim(sDate).length == 0){
					alert("Please choose Start Date");
					return;
				}else if(eDate == null || $.trim(eDate).length == 0){
					alert("Please choose End Date");
					return;
				}
				console.log(loc);
				/* console.log(handler);
				console.log(sDate); */
				getTweetCount(loc,text,sDate,eDate,map,handler);
				
		});	
				
	</script>

</body>
</html>

