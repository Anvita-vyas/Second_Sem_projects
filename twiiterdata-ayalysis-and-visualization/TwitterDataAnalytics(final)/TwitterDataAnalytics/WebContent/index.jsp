<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" lang="text/stylesheet" rel="stylesheet"/>
<title>Twitter Data Flow Visualization</title>
</head>
<body>
<div class="main" >
	<div class="header">
		<div class="head"><h1>Twitter Data Analytics</h1></div>
	</div>
	Select a file (in ".txt" format )containing Tweet Handles to upload: <br />
<form action="Twitter" method="post" enctype="multipart/form-data">
<input type="file" name="myFile" size="50"/>
<br />
<input type="submit" value="Collect Tweets" />
</form>
	<h3><a href="datamaps.jsp">Click here to proceed to Twitter Data Flow Visualization</a></h3>
</div>
</body>
</html>