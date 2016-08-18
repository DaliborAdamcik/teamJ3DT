<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<div class="container">

<h1 id = "title"></h1>

<ul class="nav nav-tabs">
			
	<li role="presentation"><button id="personalinfo_button" onclick="hidePersonalInfoField();">Change
		Personal Information</button>
				<li role="presentation"><button id="password_button" onclick="hidePasswordField();">Change
		password</button>
			<li role="presentation"><button id="topic_button" onclick="hideButtonField();">Choose
		favorite topics</button>
</ul>

	<br> <br> <br> <br>

	<form id="personalinfo_change" method="post" action="Useroptions"
		hidden="true">
		<table>
			<tr>
				<td>Real name:
				<td><input id="userinfo_realname" type="text"
					required="required" name="new_username" autofocus><br>
			<tr>
				<td>Date of birth:
				<td><input type="text" required="required" name="new_date"
					id="userinfo_birthdate"><br>
		</table>
		 <input id="submit_changepersonalinfo" type="submit" value="Submit">

	</form>


	<form id="password_change" method="post" action="Useroptions"
		hidden="true">
		
		<table>
		<tr>
			<tr>
			<td>Old Password
			<td><input type="password" required="required"
					name="old_password" id="userinfo_oldpassword" autofocus><br>
				<tr><td>New password
				<td><input type="password" required="required"
					name="new_password" id="userinfo_password"><br>
			<tr>
				<td>Confirm new password
				<td><input type="password" required="required"
					name="new_confirmpassword" id="userinfo_confirmpassword"><br>
		</table>

		 <input
			type="submit" value="Change password"/>
			<br><h3 id="pass_message" class="confirmMessage"></h3>

	</form>

	<div id="topic_change" hidden="true"></div>
</div>

<script id="topicTemplate" type="text/template">

 <legend>Choose your favourtie topics: </legend>
<table>
{{#usertopics}}

   <tr><td id="topic_change_{{id}}"><button class="removetopic_button" onclick="removetopic({{id}}, this,'{{name}}');">{{name}}</button>

 {{/usertopics}}
{{#alltopics}}

  <tr><td id="topic_change_{{id}}"><button class="addtopic_button" onclick="addtopic({{id}}, this,'{{name}}');">{{name}}</button>

 {{/alltopics}}
</table>
</script>

<script src="js/useroptions.js"></script>

<script>
	loadOptionsPage();
</script>