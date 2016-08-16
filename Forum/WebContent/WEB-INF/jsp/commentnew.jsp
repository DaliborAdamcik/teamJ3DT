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
{{#comments}}<li id="ent_{{id}}" data-owner="{{#owner}}{{id}}{{/owner}}">
	<button type="button" class="close commentmenucls" aria-hidden="true" onclick="commentMenuPopup('{{id}}');">&Xi;</button>
	
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
	<li onclick="commentMenuItemClick('edit');" class="commentMenuOwnerOption"><div>Edit</div></li>
	<li onclick="commentMenuItemClick('remove');" class="commentMenuOwnerOption"><div>Remove</div></li>

	<li id="commentMenuItemClose" class="commentMenuAdminOption"><div>Admin options</div>
	  <ul>
		<li onclick="commentMenuItemClick('block');" class="commentMenuAdminOption"><div>Block</div></li>
	  </ul>
	</li>
</ul>

<div id="editCommentDlg" title="Edit comment">
	<p>
		Modify comment:<br/>
		<input class="form-control" type="text" placeholder="Your comment" />
	</p>
</div>

<div id="eraseCommentDlg" title="Erase comment">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Do you want erase this comment?<br/><span id="eraseComment_txt"></span></p>
</div>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.2.1/mustache.min.js"></script>
<script type="text/javascript" src="js/comment.js"></script>

<script>
	commentUIinit();
</script>
