(function ($) {
    'use strict';
    $("body").on("click", "#checkout-button", function() {
        swal.setDefaults({
            confirmButtonText: 'Next &rarr;',
            showCancelButton: true,
            animation: false,
            progressSteps: ['1']
        });
        var steps = [
            // {
            //     title: 'Payment Type',
            //     text: 'How would you like to pay?',
            //     input: 'radio',
            //     inputOptions: {
            //         'cash': 'Cash',
            //         'card': 'Card'
            //     }
            // },
            {
                title: 'Licence',
                text: 'If there is alcohol in order, dont forget to ask for licence!'
            }
        ];
        swal.queue(steps).then(function (result) {
            swal.resetDefaults();
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/cart/checkout',
                data: {paymentType: result[0]},
                success: function () {
                    swal({
                        title: 'Payment proceed successfully!',
                        text: 'Thank for using McTank!',
                        type: 'success',
                        confirmButtonText: 'Done!',
                        confirmButtonClass: 'btn btn-success',
                        showCancelButton: false
                    });
                },
                error: function () {
                    swal({
                        title: 'Error!',
                        text: 'Something went wrong. Sorry.',
                        type: 'failure',
                        confirmButtonText: 'Ok',
                        confirmButtonClass: 'btn btn-success',
                        showCancelButton: false
                    });
                }
            });
        }, function () {
            swal.resetDefaults()
        })
    });

})(jQuery);
