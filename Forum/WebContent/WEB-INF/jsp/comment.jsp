<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css" href="comment.css">

<div class="detailBox">
	<div class="titleBox">
		<label>Comment Box</label>
		<!-- 		<button type="button" class="close" aria-hidden="true">&times;</button> -->
	</div>
	<div class="commentBox">

		<p class="taskDescription">This topic is very interesting....</p>
	</div>
	<div class="actionBox">
		<ul class="commentList">
			<c:forEach items="${comments}" var="comment">
				<li>
					<div class="commenterImage">
						<img src="images/userPicture.png" alt = userPicture height=50 width=50 />
					</div>
					<div class="commentText">
						<p class="">${comment.getComment()}</p>
						<span class="date sub-text">from
							${comment.owner.getUserName()} on ${comment.getCreationDate()}</span>

					</div>
				</li>
			</c:forEach>
		</ul>
		<form class="form-inline" role="form">
			<div class="form-group">
				<input class="form-control" type="text" placeholder="Your comments" />
			</div>
			<div class="form-group">
				<button class="btn btn-default">Add</button>
			</div>
		</form>

	</div>
</div>