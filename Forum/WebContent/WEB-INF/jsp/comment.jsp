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
						<div class="commenterImage">
							<img src="images/userPicture.png" alt=userPicture height=30
								width=30 />
						</div>
						<div class="commentText">
							<p class="">${comment.getComment()}</p>
							<span class="date sub-text">from
								${comment.owner.getUserName()} on ${comment.getCreationDate()}</span>

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