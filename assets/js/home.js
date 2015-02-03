var appinterface = {
	getNewestBlog: function (jsonStr){
	    $(".blogs").empty();
		var blogs = JSON.parse(jsonStr);
		for (var i = 0; i < blogs.length; i++) {
			var avatar = blogs[i].avatar;
			var username = blogs[i].username;
			var postdate = blogs[i].postdate;
			var content = blogs[i].content;

			var newHTMLs = [];
			newHTMLs.push("<div class='blog-item'>");
			newHTMLs.push("<div class='blog-title'>");
			newHTMLs.push("<div class='blog-user'>");
			newHTMLs.push("<img class='user-img left' src='" + avatar + "'/></div>");
			newHTMLs.push("<div class='blog-info'>");
			newHTMLs.push("<div class='blog-username'>" + username + "</div>");
			newHTMLs.push("<div class='blog-date'>" + postdate + "</div>");
			newHTMLs.push("</div>");
			newHTMLs.push("<div class='blog-action'><i class='fa  fa-angle-down fa-lg'></i></div>");
			newHTMLs.push("</div>");
			newHTMLs.push("<div class='blog-body'>");
			newHTMLs.push(content);
			newHTMLs.push("</div>");
			newHTMLs.push("<div class='blog-footer'>");
			newHTMLs.push("<div class='blog-f-btn blog-repost-btn'><i class='fa fa-share-square-o fa-lg'></i>3</div>");
			newHTMLs.push("<div class='blog-f-btn blog-comment-btn'><i class='fa fa-comment-o fa-lg'></i>12</div>");
			newHTMLs.push("<div class='blog-f-btn blog-like-btn'><i class='fa fa-thumbs-o-up fa-lg'></i>8</div>");
			newHTMLs.push("</div>");
			newHTMLs.push("</div>");

			$(".blogs").append($(newHTMLs.join("\n")));
		}
		HomeFragment.sendRefreshStopCode();
    },

    addNewPostBlog: function () {
        console.log("test");
        var newBlog = HomeFragment.getNewPostBlog();
        console.log(newBlog);
    	$(".blogs").prepend($(newBlog));
    	HomeFragment.clearNewPostBlog();
    }
};

