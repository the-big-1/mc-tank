(function ($) {
    'use strict';
    $("body").on("click", "#create-user", function() {
        swal.setDefaults({
            confirmButtonText: 'Next &rarr;',
            showCancelButton: true,
            animation: false,
            progressSteps: ['1', '2']
        });
        var steps = [
            {
                title: 'Enter user\'s username please',
                text: 'Will be displayed to customer',
                input: 'text'
            },
            {
                title: 'Enter user\'s password please',
                text: 'User can log in with this password.',
                input: 'password'
            }
            // {
            //     title: 'What roles have this user?',
            //     text: 'Minimum one role',
            //     html:
            //         '<div class="custom-control custom-checkbox">' +
            //         '<input type="checkbox" class="custom-control-input" id="Admin">' +
            //         '<label class="custom-control-label" for="Admin">Admin</label>' +
            //         '</div>' +
            //         '<div class="custom-control custom-checkbox">' +
            //         '<input type="checkbox" class="custom-control-input" id="Manager">' +
            //         '<label class="custom-control-label" for="Manager">Manager</label>' +
            //         '</div>' +
            //         '<div class="custom-control custom-checkbox">' +
            //         '<input type="checkbox" class="custom-control-input" id="User">' +
            //         '<label class="custom-control-label" for="User">User</label>' +
            //         '</div>',
            //     showLoaderOnConfirm: true
            //
            // }
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
                password: result[1]
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