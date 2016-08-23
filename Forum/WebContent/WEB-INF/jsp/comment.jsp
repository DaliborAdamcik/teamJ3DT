<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css" href="css/comment.css">
<link rel="stylesheet" type="text/css" href="css/rating.css">
<style>
 .ui-menu { width: 150px; }
</style>

<div id="comments_pg" style="display: none;"> <!-- Begin of comments page content, DO NOT REMOVE/MODIFY THIS TAG --> 

<div class="container" align=center>
	<div class="detailBox" align=left>
		<div class="titleBox">
			<label id="themeName"></label><button type="button" class="close entitymenucls" aria-hidden="true" onclick="entityMenuPopup(themeIdentForComments);">&Xi;</button>
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
<li id="ent_{{id}}" data-owner="{{#owner}}{{id}}{{/owner}}" data-etype="comment"{{#blocked}} class="blockedentity"{{/blocked}}>
	<button type="button" class="close entitymenucls" aria-hidden="true" onclick="entityMenuPopup('{{id}}');">&Xi;</button>
	
	<div class="commenterImage">
		<img src="Picture/{{#owner}}{{id}}{{/owner}}/" alt=userPicture height=30
			width=30 />
	</div>
	<div class="commentText">
		<p class="" id="ent_{{id}}_txt">{{comment}}</p>
		<span class="date sub-text">from {{#owner}}{{userName}}{{/owner}} on {{created}}</span>
		{{#blocked}}<div class="sub-text" title="{{reason}}">Blocked by <b>{{#blockedBy}}{{userName}}{{/blockedBy}}</b> at <i>{{created}}</i></div>{{/blocked}}
	</div>
<div class="votingtable">
	<table>
	<tr><td id="tdupvote_{{id}}"><button onclick="upvote({{id}})" id="upvote_{{id}}" class="upvote_button_black"></button>
	<td id="tdrating_{{id}}">{{rating.rating}}
	<td id="tddownvote_{{id}}"><button onclick="downvote({{id}})" id="downvote_{{id}}" class="downvote_button_black"></button>
	</table>
	</div>
</li>
</script>

<div id="editCommentDlg" title="Edit comment">
	<p>
		Modify comment:<br/>
		<input class="form-control" type="text" placeholder="Your comment" />
	</p>
</div>

<script type="text/javascript" src="js/comment.js"></script>
<script type="text/javascript" src="js/rating.js"></script>

