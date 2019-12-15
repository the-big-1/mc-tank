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
                title: 'Where customer can by it?',
                text: 'Minimum one place',
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
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/reservation',
                data: makeData(result, time),
                success: function () {
                    swal({
                        title: 'Product added successfully!',
                        text: 'It will appear soon everywhere.',
                        type: 'success',
                        confirmButtonText: 'Done!',
                        confirmButtonClass: 'btn btn-success',
                        showCancelButton: false
                    });
                }
            });
        }, function () {
            swal.resetDefaults()
        })
    });

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
