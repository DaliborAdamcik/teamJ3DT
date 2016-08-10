<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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


