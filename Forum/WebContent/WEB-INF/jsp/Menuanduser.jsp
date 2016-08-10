<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


</head>
<body>
	<div class="main_div" style="padding: 0.5em;">
	<ul>
		<c:choose>
			<c:when test="${loggeduser != null}">
	<li>logged as: ${loggeduser.userName} (${loggeduser.role}) </li><li><a
					href="Welcome?parameter=logout">Logout</a></li>

			</c:when>
			<c:otherwise>
	<li>not logged in </li><li><a href="SignIn">Login</a></li><li><a href="Register">Register</a>
		</li>
	</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${loggeduser.role == 'ADMIN'}">
			<li><a href="Admin">Admin</a></li>
			</c:when>
			</c:choose>
		 <li><a href=".">Back to welcome page</a></li>
		 </ul>
		<hr />
	</div>


</body>
</html>
