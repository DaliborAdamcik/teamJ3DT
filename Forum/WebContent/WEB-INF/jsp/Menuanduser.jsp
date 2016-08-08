<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


</head>
<body>
	<div class="main_div" style="padding: 0.5em;">
		<c:choose>
			<c:when test="${loggeduser != null}">
	logged as: ${loggeduser.userName} (${loggeduser.role}) <a
					href="Welcome?parameter=logout">Logout</a> &middot;

			</c:when>
			<c:otherwise>
	not logged in <a href="SignIn">Login</a> &middot; <a href="Register">Register</a>
		&middot;
	</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${loggeduser.role == 'ADMIN'}">
			<a href="Admin">Admin</a> &middot;
			</c:when>
			</c:choose>
		 <a href=".">Back to welcome page</a>
		 
		<hr />


	</div>


</body>
</html>