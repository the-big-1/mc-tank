(function ($) {
    'use strict';
    $("body").on("click", "#order-fuel", function() {
        swal.setDefaults({
            confirmButtonText: 'OK',
            showCancelButton: true,
            animation: false,
            title: 'Fuel Order',
            text: 'Amount of benzine to order',
            input: 'text'
            text: 'Will be displayed to customer',
            input: 'text'

        });
        swal.then(function (result) {
            console.log(result);
            swal.resetDefaults();
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/orderfuel',
                data: makeData(result),
                success: function () {
                    swal({
                        title: 'Order complete',
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

    function makeData(result) {
        return JSON.stringify(
            {
                amountBenzine: result[0],
                amountDiesel: result[1],
                }
            )
    }
    }
})(jQuery);