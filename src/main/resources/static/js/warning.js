(function ($) {
	'use strict';
	$("body").on("click", "#order-fuel", function() {
		swal.setDefaults({
			confirmButtonText: 'Next &rarr;',
			showCancelButton: true,
			animation: false,
			progressSteps: ['1', '2']
		});
		var steps = [
			{
				title: 'Fuel Order',
				text: 'What type of fuel, would you like to order?',
				input: 'radio',
				inputOptions: {
					'Diesel': 'Diesel',
					'Super Benzine': 'Benzine'
				}
			},
			{
				title: 'How much?',
				text: 'How much of it?',
				input: 'number'
			}
		];
		swal.queue(steps).then(function (result) {
			console.log(result);
			swal.resetDefaults();
			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: '/orderfuel',
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
		return JSON.stringify(
			{
				fuelType: result[0],
				amount: result[1]
			}
		)
	}
})(jQuery);
