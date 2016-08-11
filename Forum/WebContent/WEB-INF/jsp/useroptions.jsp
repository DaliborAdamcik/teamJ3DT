<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<c:choose>
<c:when test="${loggeduser!=null}">
<h3>Currently logged as ${loggeduser.userName}</h3>
</c:when>
</c:choose>



<button id="personalinfo_button" onclick="hidePersonalInfoField();">Change Personal Information</button><br><br><br>


	<form id="personalinfo_change" method="post" action="Useroptions" hidden="true">
		<table>
		<tr><td>Real name: <td><input type="text" required="required" value="${loggeduser.realName}" id="userinfo_realname" autofocus><br>
	
		<tr><td>Date of birth:<td> <input type="date" required="required" value="${formatteddate}" id="userinfo_birthdate" ><br>
		
	</table>
	<span id="userinfo_message" class="confirmMessage"></span>
		<input type="submit" value="Submit">
		
	</form>
<br><br><br><br>
<button id="password_button" onclick="hidePasswordField();">Change password</button><br><br><br>
	<form id="password_change" method="post" action="Useroptions" hidden="true">
	<table>
		<tr><td>New password<td><input type="password" required="required"  id="userinfo_password" autofocus><br>
	<tr><td>Confirm new password<td><input type="password" required="required"  id="userinfo_confirmpassword" ><br>
	</table>
	<span id="pass_message" class="confirmMessage"></span>
	<input type="submit" value="Change password">

</form>

<script src="js/useroptions.js" ></script>

