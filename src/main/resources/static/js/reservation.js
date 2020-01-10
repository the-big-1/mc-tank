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
			if (result[0] === "" || timeStringBeforeNow(time) || result[2] === null || result[3] === ""){
				swal("Result was not valid.");
				return;
			}
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/reservation',
                data: makeData(result, time),
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
        }, function () {
            swal.resetDefaults()
        })
    });

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

    function makeData(result, time) {
        return JSON.stringify(
            {
                name: result[0],
                date: time,
                mcPoint: result[2],
                username: result[3]
                }
            )
    }
})(jQuery);
