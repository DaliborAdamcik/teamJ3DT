function initializeRateButtons() {
	var jsobj = {};
	$.ajax({
		type : "PUT",
		url : "Rating/1/getinitialbuttons",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			console.log(response);
			response.ratings.forEach(function(item) {
				buttonsChange2(item.comment.id, item.rating);
			});
		}
	});
}

function upvote(id) {
	var jsobj = {};
	jsobj.id = id;
	console.log("sem dosiel");
	console.log(jsobj);
	$.ajax({
		type : "PUT",
		url : "Rating/" + id + "/upvote",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			console.log(response);
			buttonsChange(id, response.finalrating, response.rating);
		}
	});

	return false;
}

function downvote(id) {
	var jsobj = {};
	jsobj.id = id;
	console.log("sem dosiel");
	console.log(jsobj);
	$.ajax({
		type : "PUT",
		url : "Rating/" + id + "/downvote",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			console.log(response);
			buttonsChange(id, response.finalrating, response.rating);
		}
	});

	return false;
}

function buttonsChange(id, finalrating, rating) {
	if (finalrating == 0) {
		$("#tdupvote_" + id).html(
				"<button onclick='upvote(" + id + ")' id='upvote_" + id
						+ "' class='upvote_button_black'></button>");
		$("#tddownvote_" + id).html(
				"<button onclick='downvote(" + id + ")' id='downvote_" + id
						+ "' class='downvote_button_black'></button>");
		$("#tdrating_" + id).html(rating);

	}
	if (finalrating == -1) {
		$("#tdupvote_" + id).html(
				"<button onclick='upvote(" + id + ")' id='upvote_" + id
						+ "' class='upvote_button_black'></button>");
		$("#tddownvote_" + id).html(
				"<button id='downvote_" + id
						+ "' class='downvote_button' disabled></button>");
		$("#tdrating_" + id).html(rating);
	}
	if (finalrating == 1) {
		$("#tdupvote_" + id).html(
				"<button id='upvote_" + id
						+ "' class='upvote_button' disabled></button>");
		$("#tddownvote_" + id).html(
				"<button onclick='downvote(" + id + ")' id='downvote_" + id
						+ "' class='downvote_button_black' ></button>");
		$("#tdrating_" + id).html(rating);
	}

}

function buttonsChange2(id, finalrating){
	if (finalrating == 0) {
		$("#tdupvote_" + id).html(
				"<button onclick='upvote(" + id + ")' id='upvote_" + id
						+ "' class='upvote_button_black'></button>");
		$("#tddownvote_" + id).html(
				"<button onclick='downvote(" + id + ")' id='downvote_" + id
						+ "' class='downvote_button_black'></button>");

	}
	if (finalrating == -1) {
		$("#tdupvote_" + id).html(
				"<button onclick='upvote(" + id + ")' id='upvote_" + id
						+ "' class='upvote_button_black'></button>");
		$("#tddownvote_" + id).html(
				"<button id='downvote_" + id
						+ "' class='downvote_button' disabled></button>");
	}
	if (finalrating == 1) {
		$("#tdupvote_" + id).html(
				"<button id='upvote_" + id
						+ "' class='upvote_button' disabled></button>");
		$("#tddownvote_" + id).html(
				"<button onclick='downvote(" + id + ")' id='downvote_" + id
						+ "' class='downvote_button_black' ></button>");
	}
}