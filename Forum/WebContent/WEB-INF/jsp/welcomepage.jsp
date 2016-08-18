<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css" />

<div id="welcome_pg">
	<!-- Begin of welcome page content, DO NOT REMOVE THIS TAG -->
	<div class="container" align=center>
		<c:choose>
			<c:when test="${CURRENT_USER.role == 'ADMIN'}">
					<label for="new_topic_txt">Add new topic:</label> <br> <br> <input
						type="text" required="required" id="new_topic_txt"
						placeholder="Topic name" /> <br> <br> <button onclick="addNewTopic();">Add topic</button><br> <br>
			</c:when>
		</c:choose>
	</div>

	<div class="container" align="center">
		<div id="topicList" align="left">
			<!-- 
			<c:forEach var="topics" items="${topthemlis}">
				<h3>${topics.key.getName()}
					<button type="button" class="close entitymenucls"
						aria-hidden="true"
						onclick="newThemeDlgPopup(${topics.key.getId()});">New
						theme</button>
				</h3>
				<div>
					<c:forEach items="${topics.value}" var="theme">
						<div
							class="list-group-item<c:choose><c:when test="${theme.getBlocked()!= null}"> blockedentity</c:when></c:choose>"
							id="ent_${theme.getId()}" data-etype="theme"
							data-owner="${theme.getAuthor().getId()}">
							<div class="row">

								<div class="col-sm-11 col-md-10">

									<span style="cursor: pointer; text-decoration: underline;"
										onclick="loadComments(${theme.getId()});"
										id="ent_${theme.getId()}_name">${theme.getName()}</span> <span>by
										${theme.getAuthor().getUserName()}</span>
									<div class="commenterImage">
										<img src="images/userPicture.png" alt=userPicture height=30
											width=30 />
									</div>

									<br> <span>on ${theme.getCreated()}</span> <br> <br>
									<p>${theme.getDescription()}</p>
								</div>
								<div class="col-sm-1 col-md-2">

									<button type="button" class="close entitymenucls"
										aria-hidden="true"
										onclick="entityMenuPopup(${theme.getId()});">&Xi;</button>

									<span>${theme.getRating().getCommentCount()} comments <br></span>
									<span>${theme.getRating().getUserCount()} commenters <br></span>
									<span>${theme.getRating().getRatingCount()} ratings <br></span>
									<span>${theme.getRating().getAverageRating()} rating </span>
								</div>
							</div>

						</div>
					</c:forEach>
				</div>
			</c:forEach>
			 -->
		</div>
	</div>
</div>
<script id="topicTemplate" type="text/template">
<h3 id="ent_{{id}}_tit">{{name}}
	<button type="button" class="close entitymenucls"
		aria-hidden="true"
		onclick="newThemeDlgPopup({{id}});">New	theme</button>
</h3>
<div id="ent_{{id}}_cont"></div>
</script>
<script id="themeTemplate" type="text/template">
<div 
	class="list-group-item{{#blocked}} blockedentity{{/blocked}}"
	id="ent_{{id}}" data-etype="theme"
	data-owner="{{#author}}{{id}}{{/author}}">
	<div class="row">
		<div class="col-sm-11 col-md-10">
			<span style="cursor: pointer; text-decoration: underline;"
				onclick="loadComments({{id}});"
				id="ent_{{id}}_name">{{name}}</span> <span>by
				{{#author}}{{userName}}{{/author}}</span>
			<div class="commenterImage">
				<img src="images/userPicture.png" alt=userPicture height=30
					width=30 />
			</div>

			<br> <span>on {{created}}</span> <br> <br>
			<p>{{description}}</p>
		</div>
		<div class="col-sm-1 col-md-2">

			<button type="button" class="close entitymenucls"
				aria-hidden="true"
				onclick="entityMenuPopup({{id}});">&Xi;</button>
			{{#rating}}
			<span>{{commentCount}} comments <br></span>
			<span>{{userCount}} commenters <br></span>
			<span>{{ratingCount}} ratings <br></span>
			<span>{{averageRating}} rating </span>
			{{/rating}}
		</div>
	</div>
</div>
</script>
<!-- end of welcome page content, DO NOT REMOVE THIS TAG -->

<!-- DO NOT MODIFY / REMOVE comon html dialogs -->
<div id="editThemeDlg" title="Edit theme">
	<p>
		<span id="editThemeDlg_ownerInfo"></span> <label
			for="editThemeDlg_name">Name</label> <input class="form-control"
			type="text" placeholder="Name" id="editThemeDlg_name" /> <label
			for="editThemeDlg_desc">Description</label> <input
			class="form-control" type="text" placeholder="Description"
			id="editThemeDlg_desc" /> <label for="editThemeDlg_pub">Public</label>
		<input class="form-control" type="checkbox" id="editThemeDlg_pub" />
	</p>
</div>
<script id="editThemeDlg_ownerInfo_tmpl" type="text/template">
<dl>
  <dt>Created by:</dt>
  <dd>{{#author}}{{userName}}{{/author}}</dd>
  <dt>Created at:</dt>
  <dd>{{created}}</dd>
  <dt>Topic:</dt>
  <dd>{{#topic}}{{name}}{{/topic}}</dd>
</dl>
</script>

<ul id="entityMenu" style="display: none;">
	<li onclick="entityMenuItemClick('close');"><div>Close menu</div></li>
	<li id="entityMenu_iEdit" class="entMenuOwnerOption"><div>Edit</div></li>
	<li onclick="entityMenuItemClick('owner.remove');"
		class="entMenuOwnerOption"><div>Remove</div></li>

	<li id="commentMenuItemClose" class="entMenuAdminOption"><div>Admin
			options</div>
		<ul>
			<li onclick="entityMenuItemClick('admin.block');"
				class="entMenuAdminOption"><div>Block</div></li>
			<li onclick="entityMenuItemClick('admin.unblock');"
				class="entMenuAdminOption"><div>UnBlock</div></li>
		</ul></li>
</ul>

<script type="text/javascript" src="js/popupmenu.js"></script>
<script type="text/javascript" src="js/welcome.js"></script>
<script>
	
</script>