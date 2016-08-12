<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<div class="container">
	<c:choose>
		<c:when test="${CURRENT_USER!=null}">
			<h3>Currently logged as ${CURRENT_USER.userName}</h3>
		</c:when>
	</c:choose>



	<button id="personalinfo_button" onclick="hidePersonalInfoField();">Change
		Personal Information</button>
	<button id="password_button" onclick="hidePasswordField();">Change
		password</button>
	<button id="topic_button" onclick="hideButtonField();">Choose
		favorite topics</button>

	<br> <br> <br> <br>

	<form id="personalinfo_change" method="post" action="Useroptions"
		hidden="true">
		<table>
			<tr>
				<td>Real name:
				<td><input type="text" required="required" name="new_username"
					value="${CURRENT_USER.realName}" id="userinfo_realname" autofocus><br>
			<tr>
				<td>Date of birth:
				<td><input type="date" required="required" name="new_date"
					value="${formatteddate}" id="userinfo_birthdate"><br>
		</table>
		<span id="userinfo_message" class="confirmMessage"></span> <input
			type="submit" value="Submit">

	</form>


	<form id="password_change" method="post" action="Useroptions"
		hidden="true">
		<h5>${passwordmessage}</h5>
		<table>
			<tr>
				<td>New password
				<td><input type="password" required="required"
					name="new_password" id="userinfo_password" autofocus><br>
			<tr>
				<td>Confirm new password
				<td><input type="password" required="required"
					name="new_confirmpassword" id="userinfo_confirmpassword"><br>
		</table>

		<span id="pass_message" class="confirmMessage"></span> <input
			type="submit" value="Change password">

	</form>


	<form id="topic_change" method="post" action="Useroptions"
		hidden="true">
		<table>
			<c:forEach items="${listofusertopics}" var="topicOfUser">

				<tr>
					<td><input type="checkbox" name="favourite_topic"
						value="${topicOfUser.id}" checked>${topicOfUser.name}
			</c:forEach>
			<c:forEach items="${listofalltopics}" var="topic">
				<tr>
					<td><input type="checkbox" name="favourite_topic"
						value="${topic.id}">${topic.name}
			</c:forEach>

		</table>
		<input type="submit" value="Change topic">

	</form>
</div>

<script src="js/useroptions.js"></script>