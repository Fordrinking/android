var appinterface = {
	callFromActivity: function (msg){
        document.getElementById("testbody").innerHTML = msg;
    }
};

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
