(function ($) {
	function showAndroidToast() {
		Android.showToast("hahafdasdf");
	}

	var app = {
		init: function() {
			$("#btn").on("click", showAndroidToast);
		},

		run: function() {
			this.init();
		}
	}

	app.run();
})(jQuery);
