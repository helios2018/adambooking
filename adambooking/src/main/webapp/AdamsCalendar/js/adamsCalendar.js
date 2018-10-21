    var selectedBarber = "";
    
    $(document).ready(function(e) {
    	
    	//hide confirmation and booking
        $("#adam").show();
        $("#moe").show();
        $("#calendar").show();
        $("#confirmation").hide();
        $("#booking").hide();
        

        // page is now ready, initialize the calendar...
    	selectedBarber = "adam";
    	var serviceTime = getQueryVariable("serviceTime");
    	//var calendarEvents = "js/adamEvents.json";
    	var calendarEvents = "http://localhost:8080/adambooking/service/bookingController/getAvailableTimeSlots/" + serviceTime + "/" + selectedBarber;
    	
    	loadCalendar(calendarEvents);
    	
    	//barbers events
        $(".img-check").click(function(){
            $(this).toggleClass("check");
            console.log($(this).attr("alt"));
            selectedBarber = $(this).attr("alt");
            if (selectedBarber == "adam"){
              $("#moe").find("img").removeClass("check");
              //calendarEvents = "js/adamEvents.json";
              calendarEvents = "http://localhost:8080/adambooking/service/bookingController/getAvailableTimeSlots/" + serviceTime + "/" + selectedBarber;
              loadCalendar(calendarEvents);
              $('#calendar').fullCalendar('destroy');
              $('#calendar').fullCalendar('render');
              //console.log("calendarEvents:" + calendarEvents);
            }
            if (selectedBarber == "moe"){
              $("#adam").find("img").removeClass("check");
              calendarEvents = "http://localhost:8080/adambooking/service/bookingController/getAvailableTimeSlots/" + serviceTime + "/" + selectedBarber;
              //calendarEvents = "js/moEvents.json";
              loadCalendar(calendarEvents);
              $('#calendar').fullCalendar('destroy');
              $('#calendar').fullCalendar('render');
              //console.log("calendarEvents:" + calendarEvents);
            }
        });

    });//end of document ready function
    
    function loadCalendar(calendarEvents) {

        // code here can use carName 
    	console.log("loadCalendar: " + calendarEvents);
    	
      	$.getJSON(calendarEvents, function (data) {
      		console.log("calendarEvents:" + calendarEvents);
            var arrItems = [];      // THE ARRAY TO STORE JSON ITEMS.
            $.each(data, function (index, value) {
                arrItems.push(value);       // PUSH THE VALUES INSIDE THE ARRAY.
            });

             for (var i = 0; i < arrItems.length; i++) {
             		console.log("**************");
             		console.log("event " + i)
             		console.log(arrItems[i].title);
             		console.log(arrItems[i].start);
             		console.log(arrItems[i].end);
                }

	  		$('#calendar').fullCalendar({

			    //events : fetchEvents(),
			    defaultView: (function () { if ($(window).width() <= 768){return defaultView = "agendaDay";} else {return defaultView = "agendaWeek";} } )(),
	  			//defaultView: 'basicWeek',
			    minTime: '10:00:00',
			    maxTime: '18:00:00',
			  
			    businessHours: {
					    // days of week. an array of zero-based day of week integers (0=Sunday)
					    dow: [1,2,3,4,5], // Monday - Thursday

					    start: '10:00', // a start time (10am in this example)
					    end: '18:00', // an end time (6pm in this example)
				},
				events: arrItems,
			    /*events: [
			        {
				      title: 'Available',
			          start: '2018-05-24T10:30:00',
			          end: '2018-05-24T11:00:00',
			          rendering: 'backgroud',
			          backgroundColor: 'red',
			          editable: 'true'
			         
				    },
				    {
				      title: 'Lunch Break',
			          start: '2018-05-24T13:00:00',
			          end: '2018-05-24T13:30:00',
			          //rendering: 'backgroud',
			          backgroundColor: 'red'
			          //editable: 'false'
	        
				    },
			    ],*/
		        
			    //Event Click
			    eventClick: function(calEvent, jsEvent, view) {
			    	console.log('view: ' + view);
			        console.log('Event: ' + calEvent.title);
			        
			        //Event Start Time without formatting: 2018-09-29T14:00:00
			        console.log('Event Start Time without formatting: ' + moment(calEvent.start).format());
			        
			        //Event Start Time YYYY-MM-DD HH:mm: 2018-09-29 14:00 PM
			        console.log('Event Start Time YYYY-MM-DD HH:mm: ' + moment(calEvent.start).format('YYYY-MM-DD HH:mm A'));
			        
			        //Event Start Time: 2:00:00 pm
			        console.log('Event Start Time: ' + moment(calEvent.start).format('h:mm:ss a'));
			        
			        //Event Start Time: 2:30:00 pm
			        console.log('Event Start Time: ' + moment(calEvent.end).format('h:mm:ss a'));
			        
			        console.log('Event belongs to:' + selectedBarber);
			        if (selectedBarber == ""){
			        	alert("Please login to proceed!");
			        }
			        
			        var slotAvailable =  $(this).css('background-color');
			        console.log("slotAvailable: " + slotAvailable);
			        if (slotAvailable == "rgb(255, 0, 0)"){
			        	alert("This time is unavailable!");
			        }
			        else{
				        // change the border color just for fun
				        $(this).css('border-color', 'red');

				        
				        var queryString = window.location.search;
				        
				        var service = getQueryVariable("service");
				        console.log("service: " + service);
				        
				        var startTime = moment(calEvent.start).format('YYYY-MM-DD HH:mm A');
				        console.log("startTime: " + startTime);
				       
				        var startTimeCalendar = moment(calEvent.start).format();
				        console.log("startTimeCalendar: " + startTimeCalendar);
				        
				        var endTime = moment(calEvent.end).format('YYYY-MM-DD HH:mm A')
				        console.log("endTime: " + endTime);
				     
				        var endTimeCalendar = moment(calEvent.end).format();
				        console.log("endTimeCalendar: " + endTimeCalendar);
				        
				     	//show booking
				        $("#adam").hide();
				        $("#moe").hide();
				        $("#calendar").hide();
				        $("#confirmation").hide();
				        $("#booking").show();
				        
				        $("#service").text(service);
				        $("#barber").text(selectedBarber);
				        $("#startTime").text(startTime);
				        $("#startTimeCalendar").text(startTimeCalendar);
				        $("#endTime").text(endTime);
				        $("#endTimeCalendar").text(endTimeCalendar);
				        
				        /*window.open("../Book/booking.html?" + 
				        		"service=" + service +
				        		"&barber=" + selectedBarber +
				        		"&startTime=" + startTime +
				        		"&startTimeCalendar=" + startTimeCalendar +
				        		"&endTime=" + endTime +
				        		"&endTimeCalendar=" + endTimeCalendar 
				        		);*/
				        
			        }
			    }
				
			}); //end of fullCalendar

  	  	}); //end of getJSON

     }
    

	function getQueryVariable(variable)
	{
	       var query = window.location.search.substring(1);
	       var vars = query.split("&");
	       for (var i=0;i<vars.length;i++) {
	               var pair = vars[i].split("=");
	               if(pair[0] == variable){return pair[1];}
	       }
	       return(false);
	}

