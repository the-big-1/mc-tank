(function ($) {
    'use strict';
    $("body").on("click", "#create-user", function() {
        swal.setDefaults({
            confirmButtonText: 'Next &rarr;',
            showCancelButton: true,
            animation: false,
            progressSteps: ['1', '2', '3']
        });
        var steps = [
            {
                title: 'Enter username please',
                text: 'Will be displayed',
                input: 'text'
            },
            {
                title: 'Enter user\'s password please',
                text: 'User can log in with this password.',
                input: 'password'
            },
            {
                title: 'What role has this user?',
                text: 'Minimum one role',
                input: 'radio',
                inputOptions: {
                					'CUSTOMER': 'Customer',
                					'MANAGER': 'Manager'
                				}
            }
        ];
        swal.queue(steps).then(function (result) {
            console.log(result);
            swal.resetDefaults();
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/customer/new',
                data: makeData(result),
                success: function () {
                    swal({
                        title: 'User added successfully!',
                        text: 'It will appear soon in the system.',
                        type: 'success',
                        confirmButtonText: 'Done!',
                        confirmButtonClass: 'btn btn-success',
                        showCancelButton: false
                    });
                },
                error: function (){
                    swal({
                        title: 'User added failed :(',
                        text: 'This user exists already or other error occurred.',
                        type: 'error',
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

    function makeData(result) {
        return JSON.stringify(
            {
                username: result[0],
                password: result[1],
                role:     result[2]
                }
            )
    }

    function collectData() {
        var result = [];
        if ($("#Admin")[0].checked) result.push("McWash");
        if ($("#Manager")[0].checked) result.push("McZapf");
        if ($("#User")[0].checked) result.push("McSit");
        return result;

    }
})(jQuery);