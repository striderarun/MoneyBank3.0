var PapaBank = PapaBank || {};
jQuery(function ($) {

	PapaBank.Run = {

			init: function () {
				this.bindEvents();
			},

			bindEvents: function () {
				$('body')
					.on('click', '#transfer', $.proxy(this.transferFunds, this))
			},

			transferFunds: function (evt) {
				evt.preventDefault();
				var transferObj = {
						'accountNo'	: parseInt($('#accountNo').val()),
						'amount' 	: parseInt($('#amount').val())
					   };
				var	transferResponse = PapaBank.App.postData(PapaBank.Url.transferFunds,transferObj);
			}
		}

	PapaBank.Run.init();
});

