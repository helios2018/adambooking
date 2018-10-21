
    
    $(document).ready(function() {

        // page is now ready, initialize the calendar...

      	$.getJSON("js/events.json", function (data) {

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
			    defaultView: 'agendaWeek',
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
			          start: '2017-11-21T10:00:00',
			          end: '2017-11-21T11:00:00',
			          //rendering: 'backgroud',
			          backgroundColor: 'green'
			          //editable: 'false'
	        
				    },
				    {
				      title: 'Lunch Break',
			          start: '2017-11-21T13:00:00',
			          end: '2017-11-21T14:00:00',
			          //rendering: 'backgroud',
			          backgroundColor: 'red'
			          //editable: 'false'
	        
				    },
			    ],*/
			    
		        
			    //Event Click
			    eventClick: function(calEvent, jsEvent, view) {
			    	
			    	if (calEvent.backgroundColor == 'red'){
			    		alert("time slot is unavailable.")
			    	}
			    	
			    	else{
			    		console.log("time slot will be booked.")
			    		$(this).css('backgroundColor', 'red');
			    		event.title = "Booked!";
			    		console.log("event.title: " + event.title);
			            $('#calendar').fullCalendar('updateEvent', event);
			    	}
			    		
			        console.log('Event: ' + calEvent.title);
			        console.log("Start Time as on calendar: " + calEvent.start.format());
			        console.log('Event Start Time: ' + moment(calEvent.start).format('h:mm:ss a'));
			        console.log('Event End Time: ' + moment(calEvent.end).format('h:mm:ss a'));
			        console.log('Event Color: ' + calEvent.backgroundColor);

			        // change the border color just for fun
			        $(this).css('border-color', 'yellow');

			    }


			}); //end of fullCalendar

  	  	}); //end of getJSON

    });//end of document ready function

