(function ($) {
    'use strict';
    $("body").on("click", "#create-reservation", function() {
        var time;
        swal.setDefaults({
            confirmButtonText: 'Next &rarr;',
            showCancelButton: true,
            animation: false,
            progressSteps: ['1', '2', '3', '4']
        });
        var steps = [
            {
                title: 'Name of the Event',
                text: 'Will be displayed',
                input: 'text'
            },
            {
                title: 'Date and Time',
                text: 'Please select Date and time of event',
                html:
                    '<div class="input-group">' +
                    '<input type="text" id="time-format" class="form-control" placeholder="yyyy/MM/dd - hh:ii aa" aria-describedby="basic-addon5">' +
                    '<div class="input-group-append">' +
                    '<span class="input-group-text" id="basic-addon5">' +
                    '<i class="feather icon-calendar"></i></span></div></div>',
                onOpen: function () {
                    $('#time-format').datepicker({
                        language: 'en',
                        timeFormat: 'hh:ii aa',
                        timepicker: true,
                        dateTimeSeparator: ' - '
                    });
                },
                onClose: function () {
                    time = $("#time-format")[0].value;
                }
            },
            {
                title: 'For which McPoint is the reservation?',
                text: 'McSit or McWash',
                input: 'radio',
                inputOptions: {
                    'McSit': 'McSit',
                    'McWash': 'McWash'
                }
            },
            {
                title: 'Who?',
                text: 'Please enter for who is this reservation.',
                input: 'text'
            }
        ];
        swal.queue(steps).then(function (result) {
            console.log(result);
            swal.resetDefaults();
			if (result[0] === "" || time === null || timeStringBeforeNow(time) || result[2] === null || result[3] === ""){
				swal("Result was not valid.");
				return;
			}
			if (result[2] === 'McSit'){
				swal({title: 'How many persons?',
						text: 'Enter how many persons are expected:',
						input: 'number',
						inputAttributes: {
							'min': '1'
						}
						}).then(function (persons) {
							if (persons === null){
								swal("No input value found.");
								return;
							}
							$.ajax({
									type: "GET",
									contentType: "application/json",
									url: '/reservation/possible',
									data: {personCount : persons,
									resTime: time},
									success: function (data) {
										if (data)
											postData(result, time, persons);
										else {					
											swal("We are fully booked at " + time+".");
											return;
										}
									}
									});
						});
			} else {
				postData(result, time);
			}
        }, function () {
            swal.resetDefaults()
        })
    });

	function postData(result, time){
		let sendData;
		// arguments[2] contains number of persons to order mcsitreservation for
		if (result[2] == "McSit") sendData = makeData(result, time, arguments[2])
		else sendData = makeData(result, time, 0);
		$.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/reservation',
                data: sendData,
                success: function () {
                    swal({
                        title: 'Reservation added successfully!',
                        text: 'It will appear soon everywhere.',
                        type: 'success',
                        confirmButtonText: 'Done!',
                        confirmButtonClass: 'btn btn-success',
                        showCancelButton: false
                    }).then(function(){
									document.location.reload();
					});
                }
            });
	}

	function timeStringBeforeNow(time){
		let dayAndTime = time.split('-');
		let day = dayAndTime[0].trim();
		let daytimeArray = dayAndTime[1].trim().split(' ');
		let hours = parseInt(daytimeArray[0].split(':')[0]);
		let minutes = parseInt(daytimeArray[0].split(':')[1]);
		if (daytimeArray[1] === "pm") hours += 12;
		let dayValues = day.split('/');
		let date = new Date(parseInt(dayValues[0]), parseInt(dayValues[1])-1, parseInt(dayValues[2]), hours, minutes);
		return (date <= new Date());
	}

    function makeData(result, time, personCount) {
        return JSON.stringify(
            {
                name: result[0],
                date: time,
                mcPoint: result[2],
                username: result[3],
				persons: personCount
                }
            )
    }
})(jQuery);
