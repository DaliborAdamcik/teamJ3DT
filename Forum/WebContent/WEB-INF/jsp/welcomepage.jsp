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

<button onclick="editThemeDlgPopup(0);">New theme</button>

<div class="container" align="center">
	<div id="topicList" align="left">
		<c:forEach var="topics" items="${topthemlis}">
			<h3>${topics.key.getName()}</h3>
			<div>
				<c:forEach items="${topics.value}" var="theme">
					<div class="list-group-item" onclick="loadComments(${theme.getId()});">
						<a href="Comment?topicid=${theme.getId()}">${theme.getName()}</a> <span>${theme.getAuthor().getUserName()}</span>
						<p>${theme.getDescription()}</p>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
	<!-- <div style='overflow: auto; width: 800px; height: 300px;'></div> -->	
</div>

<div id="editThemeDlg" title="Edit theme">
	<p>
		<label for="editThemeDlg_name">Name</label>
		<input class="form-control" type="text" placeholder="Name" id="editThemeDlg_name" />
		<label for="editThemeDlg_desc">Description</label>
		<input class="form-control" type="text" placeholder="Description" id="editThemeDlg_desc" />
		<label for="editThemeDlg_pub">Public</label>
		<input class="form-control"  type="checkbox" id="editThemeDlg_pub" />
	</p>
</div>


<script type="text/javascript" src="js/welcome.js"></script>
<script>
	welcomeUIinit();
</script>