<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>FORUM</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- <link rel="stylesheet" href="css/normalize.css" /> -->
<link rel="stylesheet" href="css/style.css" />


<script type="text/javascript" src="js/script.js"></script>
<script src="https://code.jquery.com/jquery-3.1.0.min.js"
	integrity="sha256-cCueBR6CsyA4/9szpPfrX3s49M9vUU5BgtiJj06wt/s="
	crossorigin="anonymous"></script>
<jsp:include page="jqueryui.jsp" />
</head>

<body>
	<div class="main_div" align="center">
		<div class="brand">
			<img src="images/grayForumLogo.png" min-width="20px">
		</div>

		<!-- Navigation -->
		<nav class="navbar navbar-default" role="navigation">
			<div class="container">
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="/Forum/">Home</a></li>
						<li><a href="/Forum/">Topics</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<c:choose>
							<c:when test="${loggeduser!= null}">
								<li><a href="/Forum/Useroptions">${loggeduser.userName} (${loggeduser.role})</a></li>

								<li><a href="Welcome?parameter=logout"><span
										class="glyphicon glyphicon-log-out"></span> Logout</a></li>
							</c:when>
							<c:otherwise>
								
								<li><a href="Register"><span
										class="glyphicon glyphicon-user"></span> Register</a></li>
								<li><a href="SignIn"><span
										class="glyphicon glyphicon-log-in"></span> Login</a></li>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${loggeduser.role == 'ADMIN'}">
								<li><a href="Admin">Admin</a></li>
							</c:when>
						</c:choose>
					</ul>

				</div>
			</div>
		</nav>
	</div>