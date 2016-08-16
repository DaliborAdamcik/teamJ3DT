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
		<div class="col-md-6">
			<table class="table" >
			<tbody id="table_of_users">
			
			
			</table>
		</div>
		<div class="col-md-6">
			<table class="table" >
			<tbody id="table_of_topics">
			
			</tbody>
			</table>
		</div>


<script id="userTemplate" type="text/template">
<tr><th> User Name<th>Role<th> Blocked For<th><th>
{{#users}} <tr><td id="username">{{userName}} 
<td>{{role}}
 <td  id="blockedfor">{{blocked.reason}} 
<td id="block_{{id}}" >{{{blockbutton}}}
<td id="promote_{{id}}">{{{promotebutton}}}
{{/users}}
</script>

<script id="topicTemplate" type="text/template">
<tr><th> Topic Name<th> Blocked For<th><th>
{{#topics}} <tr><td>{{name}} 
 <td >{{blocked.reason}} 
<td id="block_{{id}}">{{{blockbutton}}}
<td id="promote_{{id}}">{{{markbutton}}}
{{/topics}}
</script>



	</div>


<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.2.1/mustache.min.js"></script>
<script type="text/javascript" src="js/adminnew.js"></script>

<script>
	loadAdminPage();
</script>