<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css" href="css/comment.css">
<style>
 .ui-menu { width: 150px; }
</style>

<div id="comments_pg" style="display: none;"> <!-- Begin of comments page content, DO NOT REMOVE/MODIFY THIS TAG --> 

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
</div><!-- end of comment page content, DO NOT REMOVE THIS TAG -->

<script id="commentTemplate" type="text/template">
{{#comments}}<li id="ent_{{id}}" data-owner="{{#owner}}{{id}}{{/owner}}" data-etype="comment">
	<button type="button" class="close entitymenucls" aria-hidden="true" onclick="entityMenuPopup('{{id}}');">&Xi;</button>
	
	<div class="commenterImage">
		<img src="images/userPicture.png" alt=userPicture height=30
			width=30 />
	</div>
	<div class="commentText">
		<p class="" id="ent_{{id}}_txt">{{comment}}</p>
		<span class="date sub-text">from {{#owner}}{{userName}}{{/owner}} on {{created}}</span>

	</div>
</li>{{/comments}}
</script>

<div id="editCommentDlg" title="Edit comment">
	<p>
		Modify comment:<br/>
		<input class="form-control" type="text" placeholder="Your comment" />
	</p>
</div>

<script type="text/javascript" src="js/comment.js"></script>