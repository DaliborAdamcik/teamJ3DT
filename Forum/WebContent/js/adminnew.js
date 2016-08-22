$(function() {
	$("#reasondialog").dialog();
	$("#reasondialog").dialog("close");
});

var $comonDlgOpts = {
	autoOpen : false,
	modal : true,
	show : {
		effect : "blind",
		duration : 500
	},
	hide : {
		effect : "blind",
		duration : 500
	}
}/**
	 * function called in jsp, calls ajax from admin servlet
	 * 
	 */
function loadAdminPage() {
	$.ajax({
		type : "GET",
		url : "Admin/0/",
		contentType : "application/json;charset=UTF-8",
		success : makeAdminPage,
		error : ajaxFailureMessage
	});
}

/**
 * Puts data from ajax to elements specified in jsp
 * 
 * @param response
 */
function makeAdminPage(response) {
	console.log(response);
	if (response.users == null || response.topics == null) {
		var title = document.getElementById("title");
		title.innerHTML = response;
		return;
	}
	response.users
			.forEach(function(item) {
				if (item.role == "GUEST") {
					item.promotebutton = "<button class=\"promote_button\" onclick=\"promoteuser("
							+ item.id + ", this);\">PROMOTE</button>";
				}

				if (item.blocked == null) {
					item.blockbutton = "<button class=\"block_button\" onclick=\"block("
							+ item.id + ");\">BLOCK</button>";
				} else {
					item.blockbutton = "<button class=\"unblock_button\" onclick=\"unblock("
							+ item.id + ");\">UNBLOCK</button>";
				}

			});
	response.topics
			.forEach(function(item) {
				if (item.isPublic) {
					item.markbutton = "<button class=\"marknonpublic_button\" onclick=\"mark("
							+ item.id + ", this,false);\">PRIVATE</button>";
				} else {
					item.markbutton = "<button class=\"markpublic_button\" onclick=\"mark("
							+ item.id + ", this,true);\">PUBLIC</button>";
				}

				if (item.blocked == null) {
					item.blockbutton = "<button class=\"block_button\"onclick=\"block("
							+ item.id + ");\">BLOCK</button>";
				} else {
					item.blockbutton = "<button class=\"unblock_button\" onclick=\"unblock("
							+ item.id + ");\">UNBLOCK</button>";
				}
			});
	var userTemplate = $('#userTemplate').html();
	var topicTemplate = $('#topicTemplate').html();
	var userHTML = Mustache.to_html(userTemplate, response);
	var topicHTML = Mustache.to_html(topicTemplate, response);
	$('#table_of_users').html(userHTML);
	$('#table_of_topics').html(topicHTML);
}
/**
 * calleda after clicking on "promote to regular user" button. Uses the put
 * method to send data to servlet, which tries to handle it and returns
 * success/failure status
 * 
 * @param id
 * @param button
 */
function promoteuser(id, button) {
	var jsobj = {};
	jsobj.id = id;
	$.ajax({
		type : "PUT",
		url : "Admin/" + id + "/promote",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			$(button).hide();
		},
		error : ajaxFailureMessage

	});
}
/**
 * called after clicking on "mark" button. Sends JSON to servlet, receives
 * response of success/failure
 * 
 * @param id
 * @param button
 * @param isMarked
 */
function mark(id, button, isMarked) {
	var jsobj = {};
	jsobj.id = id;
	$
			.ajax({
				type : "PUT",
				url : "Admin/" + id + "/mark",
				contentType : "application/json;charset=UTF-8",
				dataType : "json",
				data : JSON.stringify(jsobj),
				success : function(response) {
					var promid = "promote_" + id
					var td = document.getElementById(promid);
					if ($(button).html() == "PRIVATE") {
						td.innerHTML = "<button class=\"markpublic_button\" onclick=\"mark("
								+ id + ",this);\">PUBLIC</button>";
					} else if ($(button).html() == "PUBLIC") {
						td.innerHTML = "<button class=\"marknonpublic_button\" onclick=\"mark("
								+ id + ",this);\">PRIVATE</button>";
					}
				},
				error : ajaxFailureMessage
			});
}
/**
 * unblocking function
 * 
 * @param id
 */
function unblock(id) {

	var jsobj = {};
	jsobj.id = id;
	$.ajax({
		type : "PUT",
		url : "Admin/" + id + "/unblock",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			var tid = "block_" + id;
			var td = document.getElementById(tid);
			td.innerHTML = "<button class=\"block_button\" onclick=\"block("
					+ id + ");\">BLOCK</button>";
			var blockedfor = document.getElementById("blockedfor_" + id);
			blockedfor.innerHTML = "";
		},
		error : ajaxFailureMessage
	});
}
/**
 * blocking function. calls function from script.js
 * 
 * @param id
 */
function block(id) {
	blockCommonDlgPopup(id, successBlock);
}
/**
 * callback function for blockCommonDlgPopup.executed on successful block
 * 
 * @param response
 * @param id
 * @param reason
 */
function successBlock(response, id, reason) {
	var tid = "block_" + id;
	var td = document.getElementById(tid);
	td.innerHTML = "<button class=\"unblock_button\" onclick=\"unblock(" + id
			+ ");\">UNBLOCK</button>";
	var blockedfor = document.getElementById("blockedfor_" + id);
	var jsobj = {};
	jsobj.id = id;
	blockedfor.innerHTML = "blocked successfully";
}

function showreason(objName, blockedby, reason, button) {
	$("#objectname_reason").html(objName);
	$("#username_reason").html(blockedby);
	$("#reason_field").html(reason);
	$("#reasondialog").dialog("open");
	return false;
}

$("#add_topic").submit(function(ev) {
	var jsobj = {};
	jsobj.topicname = $('#topic_newname').val();
	jsobj.ispublic = document.getElementById('topic_ispublic').checked;
	console.log("sem dosiel");
	console.log(jsobj);
	$.ajax({
		type : "PUT",
		url : "Admin/1/addtopic",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			console.log(response);
			alertDlg("Success!", "topic added successfuly", "info");
			}
		
	});
	return false;
});

function changetopic(id){
	var jsobj = {};
	jsobj.id = id;
	if($('#newtopicname_'+id).val()==""){
		alertDlg("Error", "topic name can't be empty", "warn");
		return false;
	}
	jsobj.newname = $('#newtopicname_'+id).val();
	console.log(jsobj);
	$.ajax({
		type : "PUT",
		url : "Admin/"+id+"/changetopic",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			if(response.error!=null){
				alertDlg("Error", response.error, "warn");
				return false;
			}
			console.log(response);
			alertDlg("Success!", "topic changed successfuly", "info");
			}
		
	});
	return false;
}





