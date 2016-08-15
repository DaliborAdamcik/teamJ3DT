<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css" href="css/comment.css">
<style>
 .ui-menu { width: 150px; }
</style>

<div class="container">
	<div class="detailBox">
		<div class="titleBox">
			<label id="themeName"></label>
		</div>
		<div class="commentBox" ><p class="taskDescription" id="themeDescription"></p></div>
		<div class="actionBox">
			<ul class="commentList" id="commentBoxer"></ul>
			<form class="form-inline" role="form" style="display:none;" id="addComment">
				<div class="form-group">
					<input type="hidden" name="themeId" id="themeId" value=""> 
					<input
						class="form-control" type="text" name="newComment" id="newComment"
						placeholder="Your comments" />
				</div>
				<div class="form-group">
					<button class="btn btn-default">Add</button>
				</div>
			</form>
		</div>
	</div>
</div>


<script id="commentTemplate" type="text/template">
{{#comments}}<li id="comment_{{id}}">
	<button type="button" class="close" aria-hidden="true" onclick="commentMenuPopup('{{id}}');">&Xi;</button>
	
	<div class="commenterImage">
		<img src="images/userPicture.png" alt=userPicture height=30
			width=30 />
	</div>
	<div class="commentText">
		<p class="" id="comment_{{id}}_txt">{{comment}}</p>
		<span class="date sub-text">from {{#owner}}{{userName}}{{/owner}} on {{created}}</span>

	</div>
</li>{{/comments}}
</script>


<ul id="commentMenu" style="display:none;">
	<li onclick="commentMenuItemClick('close');"><div>Close menu</div></li>
	<li onclick="commentMenuItemClick('edit');"><div>Edit</div></li>
	<li onclick="commentMenuItemClick('remove');"><div>Remove</div></li>

	<li><div>Admin options</div>
	  <ul>
	    <li class="ui-state-disabled"><div>Home Entertainment</div></li>
	    <li><div>Car Hifi</div></li>
	    <li><div>Utilities</div></li>
	  </ul>
	</li>
</ul>

<div id="editCommentDlg" title="Edit comment">
	<form class="form-inline" role="form" id="editCommentFrm">
		<div class="form-group">
			<input
				class="form-control" type="text" name="editCommentVal" placeholder="Your comment" />
		</div>
		<div class="form-group">
			<button class="btn btn-default">Add</button>
		</div>
	</form>
</div>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.2.1/mustache.min.js"></script>
<script type="text/javascript" src="js/comment.js"></script>

<script>
	commentUIinit();
	loadComments(${themeid});
</script>
