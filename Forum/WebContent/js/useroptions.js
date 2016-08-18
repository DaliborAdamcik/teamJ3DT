//date initialization
$(function() {
	$("#userinfo_birthdate").datepicker({
		dateFormat : "dd.mm.yy"
	});
});
//$(function() {
//	$("input.checkbox").checkboxradio();
//});
/**
 * function called in jsp, calls ajax from the servlet
 * 
 */
function loadOptionsPage() {
	$.ajax({
		type : "GET",
		url : "Useroptions/0/",
		contentType : "application/json;charset=UTF-8",
		success : makeOptionsPage,
		error : ajaxFailureMessage
	});
}
/**
 * Puts data from ajax to elements specified in jsp
 * @param response
 */
function makeOptionsPage(response) {
	console.log(response);
	if (response.user == null) {
		$('#title').html("you need to  log in first");
		// break;
	} else {
		$('#options_menu').show();
	}
	$('#options_menu').show();
	$('#userinfo_realname').val(response.user.realName);
	$('#userinfo_birthdate').val(response.datestring);
	putAllTopics(response);

}
/**
 * puts all topics into form
 * @param response
 */
function putAllTopics(response) {
	var userTemplate = $('#topicTemplate').html();
	var topicHTML = Mustache.to_html(userTemplate, response);
	$('#topic_change').html(topicHTML);
	
}
/**
 * called after submitting the form for changing user basic info
 */
$("#personalinfo_change").submit(
		function(ev) {
			var jsobj = {};
			jsobj.newdate = date2str($('#userinfo_birthdate').datepicker(
					"getDate"), "dd.MM.yyyy");
			jsobj.newrealname = $('#userinfo_realname').val();
			if (jsobj.newrealname.length == 0) {
				alert("specify new name");
				return false;
			}
			console.log(jsobj);
			$.ajax({
				type : "PUT",
				url : "Useroptions/1/changeinfo",
				contentType : "application/json;charset=UTF-8",
				dataType : "json",
				data : JSON.stringify(jsobj),
				success : function(response) {
					console.log(response);
					alert("personal info changed sucessfully");
					$('#userinfo_birthdate').val(response.date);
					$('#userinfo_realname').val(response.realname);

				}
			});
			return false;

		});
/**
 * called after submitting the form for changing password
 */
$("#password_change").submit(function(ev) {
	var jsobj = {};
	jsobj.oldpassword = $('#userinfo_oldpassword').val();
	jsobj.newpassword = $('#userinfo_password').val();
	jsobj.confirmpassword = $('#userinfo_confirmpassword').val();
	console.log(jsobj);
	$.ajax({
		type : "PUT",
		url : "Useroptions/1/changepassword",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			$("#pass_message").html(response.errMessage);
			if (response.errMessage == "success") {
				$('#userinfo_oldpassword').val("");
				$('#userinfo_password').val("");
				$('#userinfo_confirmpassword').val("");
			}
		}
	});
	return false;
});
/**
 * Parses date to string in proper format
 */
function date2str(x, y) {
	var z = {
		M : x.getMonth() + 1,
		d : x.getDate(),
		h : x.getHours(),
		m : x.getMinutes(),
		s : x.getSeconds()
	};
	y = y.replace(/(M+|d+|h+|m+|s+)/g, function(v) {
		return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1))).slice(-2)
	});

	return y.replace(/(y+)/g, function(v) {
		return x.getFullYear().toString().slice(-v.length)
	});
}

/**
 * adds topic
 * @param id
 * @param button
 * @param name
 */
function addtopic(id, button, name){
	
	var jsobj = {};
	jsobj.id = id;
	console.log(jsobj);
	$.ajax({
		type : "PUT",
		url : "Useroptions/" + id + "/addtopic",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			console.log(response);
			var promid = "topic_change_"+id
			var td = document.getElementById(promid);
		
				td.innerHTML = "<button class=\"removetopic_button\" onclick=\"removetopic("+id+", this,'"+name+"');\">"+name+"</button>";
				console.log("sucess add")
		},
		error : ajaxFailureMessage

	});

}
/**
 * remove topic specified by ID from currently logged user
 * @param id
 * @param button
 * @param name
 */
function removetopic(id, button, name){
	
	var jsobj = {};
	jsobj.id = id;
	console.log(jsobj);
	$.ajax({
		type : "PUT",
		url : "Useroptions/" + id + "/removetopic",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			console.log(response);
			var promid = "topic_change_"+id
			var td = document.getElementById(promid);
		
				td.innerHTML = "<button class=\"addtopic_button\" onclick=\"addtopic("+id+", this,'"+name+"');\">"+name+"</button>";
				console.log("sucess remove")
		},
		error : ajaxFailureMessage

	});

}
$( "#personalinfo_button" ).click(function( event ) {
	  event.preventDefault();
	  $('form#personalinfo_change').show();
		$('form#password_change').hide();
		$('div#topic_change').hide();
		$( "#personalinfo_button" ).css("text-decoration", "none");
		$( "#personalinfo_button" ).css("background-color", "#A0A0A0");
		$( "#password_button" ).css("background-color", "#C0C0C0");
		$( "#topic_button" ).css("background-color", "#C0C0C0");
	});
$( "#password_button" ).click(function( event ) {
	  event.preventDefault();
	  $('form#password_change').show();
		$('form#personalinfo_change').hide();
		$('div#topic_change').hide();
		$( "#password_button" ).css("text-decoration", "none");
		$( "#personalinfo_button" ).css("background-color", "#C0C0C0");
		$( "#password_button" ).css("background-color", "#A0A0A0");
		$( "#topic_button" ).css("background-color", "#C0C0C0");
	});

$( "#topic_button" ).click(function( event ) {
	  event.preventDefault();
	  $('div#topic_change').show();
		$('form#password_change').hide();
		$('form#personalinfo_change').hide();
		$( "#topic_button" ).css("text-decoration", "none");
		$( "#personalinfo_button" ).css("background-color", "#C0C0C0");
		$( "#password_button" ).css("background-color", "#C0C0C0");
		$( "#topic_button" ).css("background-color", "#A0A0A0");
	});


