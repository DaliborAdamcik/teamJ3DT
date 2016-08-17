<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css" />

<div id="welcome_pg"><!-- Begin of welcome page content, DO NOT REMOVE THIS TAG -->
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
					<div class="list-group-item" id="ent_${theme.getId()}" data-etype="theme" data-owner="${theme.getAuthor().getId()}">
						<span style="cursor:pointer; text-decoration: underline;" onclick="loadComments(${theme.getId()});" id="ent_${theme.getId()}_name">${theme.getName()}</span> 
						<span>${theme.getAuthor().getUserName()}</span> 
						<button type="button" class="close entitymenucls" aria-hidden="true" onclick="entityMenuPopup(${theme.getId()});">&Xi;</button>
						<p>${theme.getDescription()}</p>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
	<!-- <div style='overflow: auto; width: 800px; height: 300px;'></div> -->	
</div>
</div> <!-- end of welcome page content, DO NOT REMOVE THIS TAG -->

<!-- DO NOT MODIFY / REMOVE comon html dialogs -->
<div id="editThemeDlg" title="Edit theme">
	<p>
		<span id="editThemeDlg_ownerInfo"></span>
		<label for="editThemeDlg_name">Name</label>
		<input class="form-control" type="text" placeholder="Name" id="editThemeDlg_name" />
		<label for="editThemeDlg_desc">Description</label>
		<input class="form-control" type="text" placeholder="Description" id="editThemeDlg_desc" />
		<label for="editThemeDlg_pub">Public</label>
		<input class="form-control"  type="checkbox" id="editThemeDlg_pub" />
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

<ul id="entityMenu" style="display:none;">
	<li onclick="entityMenuItemClick('close');"><div>Close menu</div></li>
	<li id="entityMenu_iEdit" class="entMenuOwnerOption"><div>Edit</div></li>
	<li onclick="entityMenuItemClick('owner.remove');" class="entMenuOwnerOption"><div>Remove</div></li>

	<li id="commentMenuItemClose" class="entMenuAdminOption"><div>Admin options</div>
	  <ul>
		<li onclick="entityMenuItemClick('admin.block');" class="entMenuAdminOption"><div>Block</div></li>
		<li onclick="entityMenuItemClick('admin.unblock');" class="entMenuAdminOption"><div>UnBlock</div></li>
	  </ul>
	</li>
</ul>

<script type="text/javascript" src="js/popupmenu.js"></script>
<script type="text/javascript" src="js/welcome.js"></script>
<script>
	
</script>