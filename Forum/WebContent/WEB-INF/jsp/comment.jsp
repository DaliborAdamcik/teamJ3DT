<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css" href="css/comment.css">

<div class="container">
	<div class="detailBox">
		<div class="titleBox">
			<label>${topicName}</label>
			<!-- 		<button type="button" class="close" aria-hidden="true">&times;</button> -->
		</div>
		<div class="commentBox">

			<p class="taskDescription">This topic is very interesting....</p>
		</div>
		<div class="actionBox">
			<ul class="commentList">
				<c:forEach items="${comments}" var="comment">
					<li>
						<button type="button" class="close" aria-hidden="true">&Xi;</button>
						
						<div class="commenterImage">
							<img src="images/userPicture.png" alt=userPicture height=30
								width=30 />
						</div>
						<div class="commentText">
							<p class="">${comment.getComment()}</p>
							<span class="date sub-text">from
								${comment.owner.getUserName()} on ${comment.getCreated()}</span>

						</div>
					</li>
				</c:forEach>
			</ul>
			
			<c:choose>
				<c:when test="${CURRENT_USER!=null && CURRENT_USER.role != 'GUEST'}">
					<form class="form-inline" role="form">
						<div class="form-group">
							<input type="hidden" name="action" value="insert_comment">
							<input type="hidden" name="topicid" value="${topicid}"> <input
								class="form-control" type="text" name="comment"
								placeholder="Your comments" />
						</div>
						<div class="form-group">
							<button class="btn btn-default">Add</button>
						</div>
					</form>
				</c:when>
			</c:choose>

		</div>
	</div>
</div>

<ul id="mousemenu">
  <li class="ui-state-disabled"><div>Toys (n/a)</div></li>
  <li><div>Books</div></li>
  <li><div>Clothing</div></li>
  <li><div>Electronics</div>
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
  <li class="ui-state-disabled"><div>Specials (n/a)</div></li>
</ul>

<script>
  $( function() {
    $( "#mousemenu" ).menu();
  } );
 </script>
 <style>
  .ui-menu { width: 150px; }
 </style>

