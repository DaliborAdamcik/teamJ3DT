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

<!-- <link rel="stylesheet" type="text/css" href="css/comment.css"> -->

<!--     Bootstrap Core CSS -->
<!--     <link href="css/bootstrap.min.css" rel="stylesheet"> -->

<!--     Custom CSS -->
<!--     <link href="css/business-casual.css" rel="stylesheet"> -->

<!--     Fonts -->
<!--     <link href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800" rel="stylesheet" type="text/css"> -->
<!--     <link href="http://fonts.googleapis.com/css?family=Josefin+Slab:100,300,400,600,700,100italic,300italic,400italic,600italic,700italic" rel="stylesheet" type="text/css"> -->

</head>

<body>
	<div class="main_div" align="center">
		<div class="brand">
			<img src="images/header.png">
		</div>

		<!-- Navigation -->
		<nav class="navbar navbar-default" role="navigation">
			<div class="container">
				<!-- Brand and toggle get grouped for better mobile display -->
				<!--             <div class="navbar-header"> -->
				<!--                 <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> -->
				<!--                     <span class="sr-only">Toggle navigation</span> -->
				<!--                     <span class="icon-bar"></span> -->
				<!--                     <span class="icon-bar"></span> -->
				<!--                     <span class="icon-bar"></span> -->
				<!--                 </button> -->
				<!-- <!--                 navbar-brand is hidden on larger screens, but visible when the menu is collapsed -->
				<!--                 <a class="navbar-brand" href="index.html">Forum</a> -->
				<!--             </div> -->
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="/Forum/">Home</a></li>
						<li><a href="#">Topics</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<c:choose>
							<c:when test="${loggeduser != null}">
								<li>logged as: ${loggeduser.userName} (${loggeduser.role})
								</li>

								<li><a href="Welcome?parameter=logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
							</c:when>
							<c:otherwise>
								<li>not logged in</li>
								<li><a href="Register"><span class="glyphicon glyphicon-user"></span> Register</a></li>
								<li><a href="SignIn"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>								
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${loggeduser.role == 'ADMIN'}">
								<li><a href="Admin">Admin</a></li>
							</c:when>
						</c:choose>
					</ul>

				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container -->
		</nav>
<!-- 		<div class="brand"> -->
<!-- 			<img src="images/bar.jpg" alt="bar" height=100 width=120%)> -->
<!-- 		</div> -->
		<%-- 		<jsp:include page="Menuanduser.jsp" /> --%>
	</div>