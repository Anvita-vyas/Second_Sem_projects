$(document).ready(function(){
	$("#dp1").datepicker( { dateFormat: 'yy-mm-dd' });
	$("#dp2").datepicker({ dateFormat: 'yy-mm-dd' });
	
}); //when page DOM is ready for execution of javascript code(code inside this runs only once) 

function getTweetCount(country,classification,startDate,endDate,map,handler){
	
	//converting JavaScript data structures into JSON text
	var data = JSON.stringify({country:country,classification:classification,startDate:startDate,endDate:endDate,handler:handler});
	
	ajaxindicatorstart('loading data.. please wait..');
	callAjax();
	function callAjax()
	{
		//Perform an asynchronous HTTP (Ajax) request
		$.ajax({
			url:"getRetweetDtls",                            //A string containing the URL to which the request is sent
			dataType: "json",
			contentType: 'application/json;charset=utf-8',
			data : data,                                     //sending data to server
			type: "post",
			
			//A function to be called if the request succeeds
			success:function(data){
				 
				var arr = [];
				var arr1=[];
				
				var org = {
						latitude : data.tweetdtls.latitude,
						longitude : data.tweetdtls.longitude
					};
				
				console.log(data.reTweetLst);
				/*checking whether there exists some retweets or not*/
				if(data.reTweetLst !=null && data.reTweetLst.length !=0){   
				$.each(data.reTweetLst,function(ar,v){
					
					console.log(v.country+" "+v.latitude+" "+v.longitude+" "+v.noOfTweets);
					var temp = {number:v.noOfTweets,origin:org,destination:{latitude : v.latitude,longitude : v.longitude}};
					arr.push(temp);
					
					/*for plotting bubbles*/
					var bubble={country:v.country,number:v.noOfTweets,latitude:v.latitude, longitude: v.longitude, radius: 16, fillKey: 'bubble_color'};
					arr1.push(bubble);
									
				});
						
				map.bubbles(arr1,
								{
											popupTemplate: function(geo, v) {
													return "<div class='hoverinfo'><h3>Country: "+v.country+"<br/>Number of retweets:  " + v.number + "</h3></div>";
											}
								}
				
						   );

				map.arc(arr,{
						strokeWidth : 4,
						arcSharpness : 2,
						animationSpeed : 800,
					});
			}
			else{
				alert("No Retweets on Tweets of "+country+" in \""+classification+"\" classification.");
			}
				
			},
			failure:function(){
				alert("Sorry an error has occured!");
			},
		});
	}
	
  //ajaxStart- Specifies a function to run when the first AJAX request begins
  jQuery(document).ajaxStart(function () {        
   		//show ajax indicator
		ajaxindicatorstart('loading data.. please wait..');
  })
  //ajaxStop-Specifies a function to run when all AJAX requests have completed
  .ajaxStop(function () {
		//hide ajax indicator
		ajaxindicatorstop();
  });
		
}
function ajaxindicatorstart(text)
{
	if(jQuery('body').find('#resultLoading').attr('id') != 'resultLoading'){
	jQuery('body').append('<div id="resultLoading" style="display:none"><div><img src="css/images/ajax-loader.gif"><div>'+text+'</div></div><div class="bg"></div></div>');
	}
	
	jQuery('#resultLoading').css({
		'width':'100%',
		'height':'100%',
		'position':'fixed',
		'z-index':'10000000',
		'top':'0',
		'left':'0',
		'right':'0',
		'bottom':'0',
		'margin':'auto'
	});	
	
	jQuery('#resultLoading .bg').css({
		'background':'#000000',
		'opacity':'0.7',
		'width':'100%',
		'height':'100%',
		'position':'absolute',
		'top':'0'
	});
	
	jQuery('#resultLoading>div:first').css({
		'width': '250px',
		'height':'75px',
		'text-align': 'center',
		'position': 'fixed',
		'top':'0',
		'left':'0',
		'right':'0',
		'bottom':'0',
		'margin':'auto',
		'font-size':'16px',
		'z-index':'10',
		'color':'#ffffff'
		
	});

    jQuery('#resultLoading .bg').height('100%');
    jQuery('#resultLoading').fadeIn(300);
    jQuery('body').css('cursor', 'wait');
}
function ajaxindicatorstop()
{
    jQuery('#resultLoading .bg').height('100%');
    jQuery('#resultLoading').fadeOut(300);
    jQuery('body').css('cursor', 'default');
}