<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css" />

<div class="container" align=center>
	<c:choose>
		<c:when test="${CURRENT_USER.role == 'ADMIN'}">
			<form method='post'>
				<label id="label">Add new topic:</label> <br> <br> <input
					type="text" required="required" name="new_topic"
					placeholder="Topic name" autofocus> <br> <br> <input
					type="submit" value="Submit"> <br> <br>
			</form>
		</c:when>
	</c:choose>
</div>


<div class="container" align="center">
	<h2>TOPICS</h2>
<div id="topicList" align="left">
	<c:forEach var="topics" items="${topthemlis}">
		<h3>${topics.key.getName()}</h3>
		<div>
			<c:forEach items="${topics.value}" var="theme">
				<p><a href="Comment?topicid=${theme.getId()}" class="list-group-item">${theme.getName()}</a></p>
			</c:forEach>
		</div>
	</c:forEach>
</div>

<!-- <div style='overflow: auto; width: 800px; height: 300px;'>

		<div class="list-group" align="left">
			<c:forEach items="${topics}" var="topics">
				<a href="Comment?topicid=${topics.getId()}" class="list-group-item">${topics.getName()}</a>
			</c:forEach>
		</div>
	</div> -->	
</div>
<script type="text/javascript" src="js/welcome.js"></script>
<script>
	welcomeUIinit();
	
</script>