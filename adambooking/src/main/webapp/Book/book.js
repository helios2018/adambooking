			
//$('#book').click(function (event) {

function book(){
	
		   console.log("Book Button Clicked");
	       
	       var firstName = $("#firstName").val();
	       console.log("firstName: " + firstName);
	       
	       var lastName = $("#lastName").val();
	       console.log("lastName: " + lastName);
	       
	       var phone = $("#phone").val();
	       console.log("phone: " + phone);
	       
	       var email = $("#email").val();
	       console.log("email: " + email);
	       
	       //var note = $("#note").val();
	       //console.log("note: " + note);
	       
	       var barber = $("#barber").text();
	       console.log("barber: " + barber);
	       
	       var service = $("#service").text();
	       console.log("service: " + service);
	       
	       var startTime = $("#startTime").text();
	       console.log("startTime: " + startTime);
	       
	       var startTimeCalendar = $("#startTimeCalendar").text();
	       console.log("startTimeCalendar: " + startTimeCalendar);
	       
	       var endTime = $("#endTime").text();
	       console.log("endTime: " + endTime);
	       
	       var endTimeCalendar = $("#endTimeCalendar").text();
	       console.log("endTimeCalendar: " + endTimeCalendar);
	      
	       var booking = {
	    		"service" : service,
	   			"barber" : barber,
	   			"firstName" : firstName,
	   			"lastName" : lastName,
	   			"phone" : phone,
	   			"email" : email,
	   			"startTime" : startTimeCalendar,
	   			"endTime" : endTimeCalendar
	       };
	       
	       var d = JSON.stringify(booking);
	       console.log(d);
	       //At this point call saveBooking service
	      
	       //Good Referencec:
	       //https://stackoverflow.com/questions/21699745/how-to-make-post-ajax-call-with-json-data-to-jersey-rest-service?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
	      /* $.ajax({
	           type : "POST",
	           url : "http://localhost:8080/adambooking/service/bookingController/saveBooking",
	           //contentType :"application/json; charSet=UTF-8",
	           contentType :"application/json",
	           data : d,
	           dataType : "json",
	           timeout: 600000,
	           crossDomain:true,
	           beforeSend: function(){
	        	    console.log("I'm sending AJAX.");
	        	    //display loading?
	           },
	           success: function(result){
	        	    console.log("response: " + result);
	           },
	           error: function(er){
	        	    console.log(er.responseText);
	           },
	           complete: function(){
	        	   console.log("I'm done.");
	        	   //hide loading?
	           }
	        });*/
	        var saveBookingUrl = "http://localhost:8080/adambooking/service/bookingController/saveBooking";
	        /*$.post(saveBookingUrl, function (d, status){
	        	alert("Data: " + d + "\nStatus: " + status);
	        });*/
	        
	        $.ajax({
	        	
	        	type: "POST",
	        	url: saveBookingUrl,
	        	data: d,
	        	contentType: "application/json",
	        	dataType: "text",
	        	success: 
	        		function (response)
	        		{
	        			//alert(response.status);
	        		},
	        	error: 
	        		function (response)
	        		{
	        			//alert(response.success);
	        		}
	        	
	        	
	        }); //end of ajax call
	        
	      //show booking
	        $("#adam").hide();
	        $("#moe").hide();
	        $("#calendar").hide();
	        $("#booking").hide();
	        
	        $("#confirmation").show();
	        confirm(service, barber, firstName, lastName, phone, email, startTime, endTime);
	        
	     
	        
	        //Show confirmation page regardless of server response
	        /*window.open("../Confirm/confirmation.html?" + 
	        		"service=" + service +
	        		"&barber=" + barber +
	        		"&firstName=" + firstName +
	        		"&lastName=" + lastName +
	        		"&phone=" + phone +
	        		"&email=" + email +
	        		"&startTime=" + startTime +
	        		"&startTimeCalendar=" + startTimeCalendar +
	        		"&endTime=" + endTime +
	        		"&endTimeCalendar=" + endTimeCalendar 
	        		);
	        	*/
	        
	        
//});
}
  