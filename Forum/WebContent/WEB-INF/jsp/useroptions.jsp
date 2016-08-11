<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<c:choose>
<c:when test="${CURRENT_USER_ATTRIB!=null}">
<h3>Currently logged as ${CURRENT_USER_ATTRIB.userName}</h3>
</c:when>
</c:choose>


<a href="?action=changeinfo">change personal information</a>


	<form id="personalinfo_change" method="post" action="Useroptions">
		<table>
		<tr><td>Real name: <td><input type="text" required="required" value="${CURRENT_USER_ATTRIB.realName}" id="userinfo_realname" autofocus><br>
		<tr><td>Date of birth:<td> <input type="date" required="required" value="${CURRENT_USER_ATTRIB.birthDame}" id="userinfo_birthdate" ><br>
		
	</table>
	<span id="userinfo_message" class="confirmMessage"></span>
		<input type="submit" value="Submit">
		
	</form>

<a href="?action=changepassword">change password</a>
	<form id="password_change" method="post" action="Useroptions">
	<table>
		<tr><td>New password<td><input type="password" required="required"  id="userinfo_password" autofocus><br>
	<tr><td>Confirm new password<td><input type="password" required="required"  id="userinfo_confirmpassword" ><br>
	</table>
	<span id="pass_message" class="confirmMessage"></span>
	<input type="submit" value="Change password">

</form>

