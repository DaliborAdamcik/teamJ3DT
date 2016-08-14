<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css" href="css/comment.css">

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
{{#comments}}<li>
	<button type="button" class="close" aria-hidden="true" onclick="commentmenu('{{id}}');">&Xi;</button>
	
	<div class="commenterImage">
		<img src="images/userPicture.png" alt=userPicture height=30
			width=30 />
	</div>
	<div class="commentText">
		<p class="">{{comment}}</p>
		<span class="date sub-text">from {{#owner}}{{userName}}{{/owner}} on {{created}}</span>

	</div>
</li>{{/comments}}
</script>


<ul id="mousemenu" style="display:none;">
<!--   <li class="ui-state-disabled"><div>Toys (n/a)</div></li> -->
  <li onclick="$('#mousemenu').hide();"><div>Close menu</div></li>
  <li><hr/></li>
 <!-- <li><div>Electronics</div>
    <ul>
      <li class="ui-state-disabled"><div>Home Entertainment</div></li>
      <li><div>Car Hifi</div></li>
      <li><div>Utilities</div></li>
    </ul>
  </li>
  <li><div>Movies</div></li>
  <li><div>Music</div>
    <ul>
      <li><div>Rock</div>
        <ul>
          <li><div>Alternative</div></li>
          <li><div>Classic</div></li>
        </ul>
      </li>
      <li><div>Jazz</div>
        <ul>
          <li><div>Freejazz</div></li>
          <li><div>Big Band</div></li>
          <li><div>Modern</div></li>
        </ul>
      </li>
      <li><div>Pop</div></li>
    </ul>
  </li>
  <li class="ui-state-disabled"><div>Specials (n/a)</div></li> --> 
</ul>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.2.1/mustache.min.js"></script>
<script type="text/javascript" src="js/comment.js"></script>

<script>
	loadComments(${themeid});

  $( function() {
    $( "#mousemenu" ).menu();
  } );
</script>
<style>
 .ui-menu { width: 150px; }
</style>

