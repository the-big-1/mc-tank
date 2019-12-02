(function ($) {
    'use strict';
    $("body").on("click", "#create-item", function() {
        swal.setDefaults({
            confirmButtonText: 'Next &rarr;',
            showCancelButton: true,
            animation: false,
            progressSteps: ['1', '2', '3']
        });
        var steps = [
            {
                title: 'Name of the Product',
                text: 'Will be displayed to customer',
                input: 'text'
            },
            {
                title: 'Cost of the Product',
                text: 'In EUR',
                input: 'text'
            },
            {
                title: 'Where customer can by it?',
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
                productCategories: points
                }
            )
    }

    function collectData() {
        var result = [];
        if ($("#McWash")[0].checked) result.push("McWash");
        if ($("#McDrive")[0].checked) result.push("McDrive");
        if ($("#McZapf")[0].checked) result.push("McZapf");
        if ($("#McSit")[0].checked) result.push("McSit");
        return result;

    }
})(jQuery);