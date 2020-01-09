(function ($) {
	'use strict';
	$("body").on("click", "#order-fuel", function() {
		swal.setDefaults({
			confirmButtonText: 'Next &rarr;',
			showCancelButton: true,
			animation: false,
			progressSteps: ['1', '2']
		});
		swal({
				title: 'Fuel Order',
				text: 'What type of fuel, would you like to order?',
				input: 'radio',
				inputOptions: {
					'Diesel': 'Diesel',
					'Super Benzine': 'Benzine'
				},
				currentProgressStep: 0,
				// trying to validate input value, not working yet
				preConfirm: function () {
					        return new Promise(function (resolve, reject) {	
									// radio inputs have same id, getElemById finds only one       
					                if (!document.getElementById('swal2-radio-1').checked) {
					                    reject('err');
					                } else {
					                    resolve(undefined);
									}
					        })
					    }
			}).then((value) => {
				amountSwal(value);
				}
			, function () {
			swal.resetDefaults()
		});	
		function amountSwal(fuel){
  			$.ajax({
				type: "GET",
				contentType: "application/json",
				url: '/getFuelPrice',
				data: {fuelType : fuel},
				success: function (price) {
					swal({
						currentProgressStep: 1,
						title: 'How much?',
						text: 'Price per Liter: ' + price,
						input: 'number',
						inputAttributes: {
							'min': '1'
						}
					}).then(function (amount) {
						var result = [fuel, amount];
						console.log(result);
						swal.resetDefaults();
						$.ajax({
							type: "POST",
							contentType: "application/json",
							url: '/orderfuel',
							data: makeData(result),
							success: function () {
								swal({
									title: 'Fuel added successfully!',
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
	  			}});
			}
	function makeData(result) {
		return JSON.stringify(
			{
				fuelType: result[0],
				amount: result[1]
			}
		)
	}
	})
}(jQuery))
