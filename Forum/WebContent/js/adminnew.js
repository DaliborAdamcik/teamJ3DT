function loadAdminPage() {
	$.ajax({
		type : "GET",
		url : "Admin/0/",
		contentType : "application/json;charset=UTF-8",
		success : makeAdminPage,
		error : ajaxFailureMessage
	});
}

function makeAdminPage(response) {
	console.log(response);
	response.users.forEach(function(item) {
		if (item.role == "GUEST") {
			item.promotebutton = "<button onclick=\"promoteuser(" + item.id
					+ ", this);\">promote to regular</button>";
		}

		if (item.blocked == null) {
			item.blockbutton = "<button onclick=\"block(" + item.id
					+ ");\">block</button>";
		} else {
			item.blockbutton = "<button onclick=\"unblock(" + item.id
					+ ");\">unblock</button>";
		}

	});
	response.topics.forEach(function(item) {
		if (item.isPublic) {
			item.markbutton = "<button onclick=\"mark(" + item.id
					+ ", this,false);\">mark non public</button>";
		} else {
			item.markbutton = "<button onclick=\"mark(" + item.id
					+ ", this,true);\">mark public</button>";
		}

		if (item.blocked == null) {
			item.blockbutton = "<button onclick=\"block(" + item.id
					+ ");\">block</button>";
		} else {
			item.blockbutton = "<button onclick=\"unblock(" + item.id
					+ ");\">unblock</button>";
		}
	});
	var userTemplate = $('#userTemplate').html();
	var topicTemplate = $('#topicTemplate').html();
	var userHTML = Mustache.to_html(userTemplate, response);
	var topicHTML = Mustache.to_html(topicTemplate, response);
	$('#table_of_users').html(userHTML);
	$('#table_of_topics').html(topicHTML);
}

function promoteuser(id, button) {
	var jsobj = {};
	jsobj.id = id;
	console.log(jsobj);
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

function mark(id, button, isMarked) {

	var jsobj = {};
	jsobj.id = id;
	console.log(jsobj);
	$.ajax({
		type : "PUT",
		url : "Admin/" + id + "/mark",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {

			if ($(button).html() == "mark non public") {
				$(button).html('mark public');
			} else if ($(button).html() == "mark public") {
				$(button).html('mark non public');
			}

		},
		error : ajaxFailureMessage

	});
}

function unblock(id) {

	var jsobj = {};
	jsobj.id = id;
	console.log(jsobj);
	$.ajax({
		type : "PUT",
		url : "Admin/" + id + "/unblock",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			var tid = "block_" + id;
			console.log(tid);
			var td = document.getElementById(tid);
			td.innerHTML = "<button onclick=\"block(" + id
					+ ");\">block</button>";

		},
		error : ajaxFailureMessage
	});
}

function block(id) {
	blockCommonDlgPopup(id);
	location.reload();
	
	
}
