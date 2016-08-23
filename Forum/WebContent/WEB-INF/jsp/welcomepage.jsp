<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="welcome_pg">
	<!-- Begin of welcome page content, DO NOT REMOVE THIS TAG -->
	<div class="container" align="center">
		<div id="topicList" align="left">
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
	class="list-group-item{{#blocked}} blockedentity{{/blocked}} pubclass{{isPublic}}"
	id="ent_{{id}}" data-etype="theme"
	data-owner="{{#author}}{{id}}{{/author}}">
	<div class="row">
		<div class="col-sm-11 col-md-10">
			<span style="cursor: pointer; text-decoration: underline;"
				onclick="loadComments({{id}});"
				id="ent_{{id}}_name">{{name}}</span> <span>by
				{{#author}}{{userName}}{{/author}}</span>
			<div class="commenterImage">
				<img src="Picture/{{#author}}{{id}}{{/author}}/" alt=userPicture height=30
					width=30 />
			</div>

			<br> <span>on {{created}}</span> <br> <br>
			<p>{{description}}</p>
			{{#blocked}}<div class="sub-text" title="{{reason}}">Blocked by <b>{{#blockedBy}}{{userName}}{{/blockedBy}}</b> at <i>{{created}}</i></div>{{/blocked}}
		</div>
		<div class="col-sm-1 col-md-2">
			<button type="button" class="close entitymenucls"
				aria-hidden="true"
				onclick="entityMenuPopup({{id}});">&Xi;</button>
			{{#rating}}
			<span title="Number of comments">{{commentCount}}<span class="ui-icon ui-icon-comment"></span></span> 
			<span title="Commenters in theme">{{userCount}}<span class="ui-icon ui-icon-person"></span></span>
			<span title="Number of ratings">{{ratingCount}}<span class="ui-icon ui-icon-flag"></span></span> 
			<span title="Average rating">{{averageRating}}<span class="ui-icon ui-icon-check"></span></span> 
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
<jsp:include page="comment.jsp"></jsp:include>