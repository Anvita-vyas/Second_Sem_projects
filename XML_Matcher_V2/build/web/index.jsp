<%-- 
    Document   : index
    Created on : Apr 24, 2016, 10:35:39 PM
    Author     : kush
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<title>XML Matcher</title>
	<link rel="stylesheet" type="text/css" href="diffview.css">
	<script type="text/javascript" src="diffview.js"></script>
	<script type="text/javascript" src="difflib.js"></script>
<style type="text/css">
body {
	font-size: 12px;
	font-family: Sans-Serif;
}
h2 {
	margin: 0.5em 0 0.1em;
	text-align: center;
}
.top {
	text-align: center;
}
.textInput {
	display: block;
	width: 49%;
	float: left;
}
textarea {
	width:100%;
	height:300px;
}
label:hover {
	text-decoration: underline;
	cursor: pointer;
}
.spacer {
	margin-left: 10px;
}
.viewType {
	font-size: 16px;
	clear: both;
	text-align: center;
	padding: 1em;
}
#diffoutput {
	width: 100%;
}
</style>

<script type="text/javascript">

function diffUsingJS(viewType) {
	"use strict";
	var byId = function (id) { return document.getElementById(id); },
		base = difflib.stringAsLines(byId("baseText").value),
		newtxt = difflib.stringAsLines(byId("newText").value),
		sm = new difflib.SequenceMatcher(base, newtxt),
		opcodes = sm.get_opcodes(),
		diffoutputdiv = byId("diffoutput"),
		contextSize = byId("contextSize").value;

	diffoutputdiv.innerHTML = "";
	contextSize = contextSize || null;

	diffoutputdiv.appendChild(diffview.buildView({
		baseTextLines: base,
		newTextLines: newtxt,
		opcodes: opcodes,
		baseTextName: "Base Text",
		newTextName: "New Text",
		contextSize: contextSize,
		viewType: viewType
	}));
}

</script>
</head>
<body>
   <form method="post" action="FileCompareServlet">
	<h1 class="top">XML Matcher</h1>
	<div class="top">
		<strong>Context size (optional):</strong> <input type="text" id="contextSize" value="" visibility=false>
	</div>
	<div class="textInput">
		    <input id="file1" name="file1" type="file" onchange="onChange1()"/>
                    <textarea name="baseText" id="baseText">
                        <% Object text1 = request.getAttribute("text1");%>
                        <%=text1%>
                    </textarea>
                    
                    <script type="text/javascript">
                      //METHOD#4
                      function onChange1(){
                        //filePath1 = document.getElementById('file1').value;
                        //alert(filepath.toString());
                         file1 = document.getElementById("file1").files[0];
                        var reader = new FileReader();
                        reader.onload = function (e) {
                          
                          textArea1 = document.getElementById("baseText");
                          textArea1.value=" ";
                          textArea1.value = e.target.result;
                        };
                        reader.readAsText(file1);
                      }
                    </script>
                
                
                
                
                
                
                
                
                
                
                
                
	</div>
	<div class="textInput spacer">
	 <input id="file2" name="file2" type="file" onchange="onChange2()"/>
                    <textarea name="newText"  id="newText">
                     <% Object text2 = request.getAttribute("text2");%>
                    <%=text2%>
                    </textarea>
                    
                    <script type="text/javascript">
                      function onChange2(){
                       
                        //var filepath = document.getElementById('file2').value; 
                        //  alert(filepath.toString());
                         file2 = document.getElementById("file2").files[0];
                        var reader = new FileReader();
                        reader.onload = function (e) {
                          
                          var textArea2 = document.getElementById("newText");
                          
                          textArea2.value= e.target.result;
                        };
                        reader.readAsText(file2);
                      }
                    </script>	
            
            
            
            
            
            
            
            
            
            
            
            
            
	</div>
	<div class="viewType">
            <%--<input type="submit" name="_viewtype" id="sidebyside" onclick="diffUsingJS(0);"> <label for="sidebyside">Difference</label>
            --%>
             <input type="submit" value="Percentage" >
             <input type="Button" name="_viewtype" value="Difference" id="sidebyside" onclick="diffUsingJS(0);"> 
             
            
            
            
            <p><label> Percentage match :</label>
                      <% Object value = request.getAttribute("value");%>
                      <text name="myText" value ="">
                      <%=value%>
                      </text>
                    </p>
	</div>
	<div id="diffoutput"> </div>

   </form>
</body></html>