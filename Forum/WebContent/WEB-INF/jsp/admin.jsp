<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<title>Admin</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css" />
</head>
<body>
	<div class="container">



		<h1 id="title"></h1>

		<form id="add_topic" method="post" action="Admin">
			<table>
				<tr>
					<td>new topic:
					<td><input id="topic_newname" type="text" required="required"
						name="new_username" autofocus><br>
				<tr>
					<td><input id="topic_ispublic" type="checkbox" name="ispublic"
						value="true" checked> public<br>
			</table>
			<input id="submit_newtopic" type="submit" value="Submit">
		</form>



		<br> <br>

		<div>
			<br> <br>
			<div class="col-md-6">
				<table class="table">
					<tbody id="table_of_users">
				</table>
			</div>
			<div class="col-md-6">
				<table class="table">
					<tbody id="table_of_topics">

					</tbody>
				</table>
			</div>
		</div>


	</div>
	<div id="reasondialog" title="REASON">
		<table>
			<tr>
				<th colspan="3" id="objectname_reason">
				<td>&nbsp;&nbsp;&nbsp;&nbsp; blocked by&nbsp;&nbsp;
				<td id="
				username_reason">druhe meno
			<tr>
				<td><br>
		</table>
		<table>
			<tr>
				<th colspan="5">blocked for:
			<tr>
				<td colspan="5" id="reason_field">reason
		</table>
	</div>
	
	<script id="userTemplate" type="text/template">
<tr><th> User Name<th>Role<th> Blocked For<th>Block<th>Promote
{{#users}} <tr><td id="username">{{userName}} 
<td>{{role}}
<td  id="blockedfor_{{id}}">{{#blocked}}<button class="showreason_button" onclick="showreason('{{userName}}','{{blockedBy.userName}}','{{reason}}',this);">REASON</button>{{/blocked}}
<td id="block_{{id}}" >{{{blockbutton}}}
<td id="promote_{{id}}">{{{promotebutton}}}
{{/users}}
</script>
	<script id="topicTemplate" type="text/template">
<tr><th> Topic Name<th> Blocked For<th>Block<th>Mark
{{#topics}} <tr><td>
	<input size='15' id="newtopicname_{{id}}" type='text' value='{{name}}' />
	<button id="submitnewname_{{id}}" onclick="changetopic({{id}})">asd</button>
<td id="blockedfor_{{id}}">{{#blocked}}<button class="showreason_button" onclick="showreason('{{name}}','{{blockedBy.userName}}','{{reason}}',this);">REASON</button>{{/blocked}}
<td id="block_{{id}}">{{{blockbutton}}}
<td id="promote_{{id}}">{{{markbutton}}}
{{/topics}}
</script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.2.1/mustache.min.js"></script>
	<script type="text/javascript" src="js/adminnew.js"></script>
	<script>
		loadAdminPage();
	</script>