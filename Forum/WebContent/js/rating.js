function upvote(id){
	var jsobj = {};
	jsobj.id=id;
	console.log("sem dosiel");
	console.log(jsobj);
	$.ajax({
		type : "PUT",
		url : "Rating/"+id+"/upvote",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			console.log(response);
			buttonsChange(id,response.finalrating,response.rating);
		}
		});
	
	return false;
}


function downvote(id){
	var jsobj = {};
	jsobj.id=id;
	console.log("sem dosiel");
	console.log(jsobj);
	$.ajax({
		type : "PUT",
		url : "Rating/"+id+"/downvote",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			console.log(response);
			buttonsChange(id,response.finalrating,response.rating);
		}
		});
	
	return false;
}

function buttonsChange(id,finalrating,rating){
	if(finalrating==0){
		$("#tdupvote_"+id).html("<button onclick='upvote("+id+")' id='upvote_"+id+"' class='upvote_button_black'></button>");
		$("#tddownvote_"+id).html("<button onclick='downvote("+id+")' id='downvote_"+id+"' class='downvote_button_black'></button>");
		$("#tdrating_"+id).html(rating);
		
	}
	if(finalrating==-1){
		$("#tdupvote_"+id).html("<button onclick='upvote("+id+")' id='upvote_"+id+"' class='upvote_button_black'></button>");
		$("#tddownvote_"+id).html("<button onclick='downvote("+id+")' id='downvote_"+id+"' class='downvote_button' black></button>");
		$("#tdrating_"+id).html(rating);
	}
	if(finalrating==1){
		$("#tdupvote_"+id).html("<button onclick='upvote("+id+")' id='upvote_"+id+"' class='upvote_button' blocked></button>");
		$("#tddownvote_"+id).html("<button onclick='downvote("+id+")' id='downvote_"+id+"' class='downvote_button_black' ></button>");
		$("#tdrating_"+id).html(rating);
	}
	
}