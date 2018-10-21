function confirm(service, barber, firstName, lastName, phone, email, startTime, endTime){
	
	
	
	console.log("confirmation page loaded.");
   
	    if (service == "hairCut"){
		  	$("#service1").text("Hair Cut");
	    }
	    else{
	  	  $("#service1").text(service);
	    }
	  
	
	  console.log("barber: " + barber);
	  $("#barber1").text(barber);
	  
	 
	  console.log("startTime: " + startTime);
	  //var startTimeFormatted1 = startTime.replace("%20", " ");
	  //var startTimeFormatted2 = startTimeFormatted1.replace("%20", " ");
	  //console.log("startTimeFormatted2: " + startTimeFormatted2);
	  //$("#startTime").text(startTimeFormatted2);
	  
	  $("#startTime1").text(startTime);
	  
	  $("#endTime1").text(endTime);
   
    console.log("firstName: " + firstName);
    $("#firstName1").text(firstName);
    
   
    console.log("lastName: " + lastName);
    $("#lastName1").text(lastName);

   
    console.log("phone: " + phone);
    $("#phone1").text(phone);
   
   
    console.log("email: " + email);
    $("#email1").text(email);
	
	
	
	
	
	
	
}