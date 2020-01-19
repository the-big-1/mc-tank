(function ($) {
    'use strict';
    $("body").on("click", "#create-item", function() {
        swal.setDefaults({
            confirmButtonText: 'Next &rarr;',
            showCancelButton: true,
            animation: false,
            progressSteps: ['1', '2', '3', '4']
        });
        var steps = [
            {
                title: 'Name of the Product',
                text: 'Will be displayed in Stock',
                input: 'text'
            },
            {
                title: 'Cost of the Product',
                text: 'In EUR',
                input: 'text'
            },
            {
                title: 'Amount of the Product',
                text: 'How many of it can user buy',
                input: "text"
            },
            {
                title: 'Which McPoint?',
                text: 'Minimum one place',
                html:
                    '<div class="custom-control custom-checkbox">' +
                    '<input type="checkbox" class="custom-control-input" id="McWash">' +
                    '<label class="custom-control-label" for="McWash">McWash</label>' +
                    '</div>' +
                    '<div class="custom-control custom-checkbox">' +
                    '<input type="checkbox" class="custom-control-input" id="McDrive">' +
                    '<label class="custom-control-label" for="McDrive">McDrive</label>' +
                    '</div>' +
                    '<div class="custom-control custom-checkbox">' +
                    '<input type="checkbox" class="custom-control-input" id="McZapf">' +
                    '<label class="custom-control-label" for="McZapf">McZapf</label>' +
                    '</div>' +
                    '<div class="custom-control custom-checkbox">' +
                    '<input type="checkbox" class="custom-control-input" id="McSit">' +
                    '<label class="custom-control-label" for="McSit">McSit</label>' +
                    '</div>',
                showLoaderOnConfirm: true

            }
        ];
        swal.queue(steps).then(function (result) {
            console.log(result);
            swal.resetDefaults();
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/item/new',
                data: makeData(result),
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

    function makeData(result) {
        var points = collectData();
        return JSON.stringify(
            {
                productName: result[0],
                price: result[1],
                amount: result[2],
                productCategories: points
                }
            )
    }

    function collectData() {
        var result = [];
        if (checkById("#McWash")) result.push("McWash");
        if (checkById("#McDrive")) result.push("McDrive");
        if (checkById("#McZapf")) result.push("McZapf");
        if (checkById("#McSit")) result.push("McSit");
        return result;
    }

    function checkById(id) {
        return $(id)[0].checked
    }
})(jQuery);