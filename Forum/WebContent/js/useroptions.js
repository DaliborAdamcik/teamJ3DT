//date initialization
$(function() {
	$("#userinfo_birthdate").datepicker({
		dateFormat : "dd.mm.yy",
			maxDate: "+0m +0w"
	});
});

// $(function() {
// $("input.checkbox").checkboxradio();
// });
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
 * 
 * @param response
 */
function makeOptionsPage(response) {

	if (response.user == null) {
		$('#title').html("you need to  log in first");
		return;
	}
	$('#options_menu').show();
	$('#personalinfo_change').show();
	$('#userinfo_realname').val(response.user.realName);
	console.log(response.user.realName);
	$('#userinfo_birthdate').val(response.datestring);
	putAllTopics(response);
}
/**
 * puts all topics into form
 * 
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
			var newdate = $('#userinfo_birthdate').val();
			console.log(newdate);
			
			var jsobj = {};
			jsobj.newdate = date2str($('#userinfo_birthdate').datepicker(
					"getDate"), "dd.MM.yyyy");
			var datecompare = /^((0?[1-9]|[12][0-9]|3[01]).(0?[13578]|1[02])| (0?[1-9]|[12][0-9]|3[0]).(0?[469]|1[1]) |(0?[1-9]|[12][0-9]).(0?2)).((19|20)[0-9]{2})$/;
			
			if(!datecompare.test(newdate)){
				alertDlg("Failed!", "Date in incorrect format. please insert date in format dd.mm.yyyy", "warn");
				return false;
			}
			jsobj.newrealname = $('#userinfo_realname').val();
			if (jsobj.newrealname.length == 0) {
				alertDlg("Error", "specify new name", "warn");
				return false;
			}
			$.ajax({
				type : "PUT",
				url : "Useroptions/1/changeinfo",
				contentType : "application/json;charset=UTF-8",
				dataType : "json",
				data : JSON.stringify(jsobj),
				success : function(response) {
					if(response.error!=null){
					alertDlg("fail!", response.error, "warn");
					return false ;
					}
					alertDlg("Success!", "personal info changed sucessfully", "info");
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
 * 
 * @param id
 * @param button
 * @param name
 */
function addtopic(id, button, name) {
	var jsobj = {};
	jsobj.id = id;
	$
			.ajax({
				type : "PUT",
				url : "Useroptions/" + id + "/addtopic",
				contentType : "application/json;charset=UTF-8",
				dataType : "json",
				data : JSON.stringify(jsobj),
				success : function(response) {
					var promid = "topic_change_" + id
					var td = document.getElementById(promid);
					td.innerHTML = "<button class=\"removetopic_button\" onclick=\"removetopic("
							+ id
							+ ", this,'"
							+ name
							+ "');\">"
							+ name
							+ "</button>";
					console.log("sucess add")
				},
				error : ajaxFailureMessage
			});
}
/**
 * remove topic specified by ID from currently logged user
 * 
 * @param id
 * @param button
 * @param name
 */
function removetopic(id, button, name) {
	var jsobj = {};
	jsobj.id = id;
	$
			.ajax({
				type : "PUT",
				url : "Useroptions/" + id + "/removetopic",
				contentType : "application/json;charset=UTF-8",
				dataType : "json",
				data : JSON.stringify(jsobj),
				success : function(response) {
					var promid = "topic_change_" + id
					var td = document.getElementById(promid);
					td.innerHTML = "<button class=\"addtopic_button\" onclick=\"addtopic("
							+ id
							+ ", this,'"
							+ name
							+ "');\">"
							+ name
							+ "</button>";
				},
				error : ajaxFailureMessage

			});

}
$("#personalinfo_button").click(function(event) {
	event.preventDefault();
	$('form#personalinfo_change').show();
	$('form#password_change').hide();
	$('div#topic_change').hide();
	$('div#profilepicture_change').hide();
	$("#picturechange_button").css("background-color", "#C0C0C0");
	$("#personalinfo_button").css("text-decoration", "none");
	$("#personalinfo_button").css("background-color", "#A0A0A0");
	$("#password_button").css("background-color", "#C0C0C0");
	$("#topic_button").css("background-color", "#C0C0C0");
});
$("#password_button").click(function(event) {
	event.preventDefault();
	$('form#password_change').show();
	$('form#personalinfo_change').hide();
	$('div#topic_change').hide();
	$('div#profilepicture_change').hide();
	$("#picturechange_button").css("background-color", "#C0C0C0");
	$("#password_button").css("text-decoration", "none");
	$("#personalinfo_button").css("background-color", "#C0C0C0");
	$("#password_button").css("background-color", "#A0A0A0");
	$("#topic_button").css("background-color", "#C0C0C0");
});

$("#topic_button").click(function(event) {
	event.preventDefault();
	$('div#topic_change').show();
	$('form#password_change').hide();
	$('form#personalinfo_change').hide();
	$('div#profilepicture_change').hide();
	$("#picturechange_button").css("background-color", "#C0C0C0");
	$("#topic_button").css("text-decoration", "none");
	$("#personalinfo_button").css("background-color", "#C0C0C0");
	$("#password_button").css("background-color", "#C0C0C0");
	$("#topic_button").css("background-color", "#A0A0A0");
});

$("#picturechange_button").click(function(event) {
	event.preventDefault();
	$('div#profilepicture_change').show();
	$('div#topic_change').hide();
	$('form#password_change').hide();
	$('form#personalinfo_change').hide();
	$("#topic_button").css("text-decoration", "none");
	$("#picturechange_button").css("background-color", "#A0A0A0");
	$("#personalinfo_button").css("background-color", "#C0C0C0");
	$("#password_button").css("background-color", "#C0C0C0");
	$("#topic_button").css("background-color", "#C0C0C0");
});
