<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>J3DT FORUM</title>
	
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	<!-- <link rel="stylesheet" href="css/normalize.css" /> -->
	<!-- 	common page style -->
	<link rel="stylesheet" href="css/style.css" />

	<!-- Jquery UI default syle -->
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
	
	<!-- include Jquery and JqueryUI -->
	<script src="https://code.jquery.com/jquery-3.1.0.min.js"
		integrity="sha256-cCueBR6CsyA4/9szpPfrX3s49M9vUU5BgtiJj06wt/s="
		crossorigin="anonymous"></script>
	  <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
	  
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.2.1/mustache.min.js"></script>
	  
	
	<!-- Forum web page script -->
	<script type="text/javascript" src="js/script.js"></script>
</head>

<body>
	<div class="main_div" align="center">
		<div class="brand">
			<img id="forumLogo" src="images/grayForumLogo.png">
		</div>

		<!-- Navigation -->
		<nav class="navbar navbar-default" role="navigation">
			<div class="container">
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a onclick="showWelcomePage();">Topics</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<c:choose>
							<c:when test="${CURRENT_USER!= null}">
								<li><a href="/Forum/Useroptions">${CURRENT_USER.userName} (${CURRENT_USER.role})</a></li>
								<li><a href="Welcome?parameter=logout"><span
										class="glyphicon glyphicon-log-out"></span> Logout</a></li>
								<script type="text/javascript" >
									user.role = "${CURRENT_USER.role}";
									user.id = ${CURRENT_USER.getId()};
								</script>
							</c:when>
							<c:otherwise>
								
								<li><a href="Register"><span
										class="glyphicon glyphicon-user"></span> Register</a></li>
								<li><a href="SignIn"><span
										class="glyphicon glyphicon-log-in"></span> Login</a></li>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${CURRENT_USER.role == 'ADMIN'}">
								<li><a href="Admin">Admin</a></li>
							</c:when>
						</c:choose>
					</ul>

				</div>
			</div>
		</nav>
	</div>