<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<%-- <jsp:include page="header.jsp"></jsp:include> --%>
		<c:choose>
			<c:when test="${CURRENT_USER==null ||CURRENT_USER.role !='ADMIN'}">
				<h1>Only admins can view this page</h1>
			</c:when>
			<c:otherwise>
				<!-- Table for users -->
				<h2>USERS</h2>
				<table>
					<c:forEach items="${listofusers}" var="user">
						<tr>
							<td>${user.userName}
							<td>${user.role}<c:choose>
									<c:when test="${user.blocked!=null}">
										<td>banned for: ${user.blocked.reason} by
											${user.blocked.blockedBy.userName}
									</c:when>
									<c:otherwise>
										<td>
									</c:otherwise>
								</c:choose>
							<td><c:choose>
									<c:when test="${user.blocked ==null}">
										<td>
											<form method="post" action="Admin">
												<input type="text" name="block_reason"
													placeholder="block reason" required="required">
												<button name="block" value="${user.id}">block</button>
											</form>
									</c:when>
									<c:otherwise>

										<form method="post" action="Admin">
											<button name="unblock" value="${user.id}">unblock</button>
										</form>
										<td>
									</c:otherwise>
								</c:choose> <c:choose>
									<c:when test="${user.role == 'GUEST'}">
										<td>
											<form method="post" action="Admin">
												<button name="promote_to_regular" value="${user.id}">promote
													to regular</button>
											</form>
									</c:when>

								</c:choose>
					</c:forEach>
				</table>

				<!-- Table for topics -->
				<h2>TOPICS</h2>
				<table>
					<c:forEach items="${listoftopics}" var="topic">
						<tr>
							<td>${topic.name}<c:choose>
									<c:when test="${topic.blocked!=null}">
										<td>banned for: ${topic.blocked.reason} by
											${topic.blocked.blockedBy.userName}
									</c:when>
									<c:otherwise>
										<td>
									</c:otherwise>
								</c:choose>
							<td><c:choose>
									<c:when test="${topic.blocked ==null}">
										<td>
											<form method="post" action="Admin">
												<input type="text" name="block_reason"
													placeholder="block reason" required="required">
												<button name="block" value="${topic.id}">block</button>
											</form>
									</c:when>
									<c:otherwise>

										<form method="post" action="Admin">
											<button name="unblock" value="${topic.id}">unblock</button>
										</form>
										<td>
									</c:otherwise>
								</c:choose> <c:choose>
									<c:when test="${topic.isPublic}">
										<td>
											<form method="post" action="Admin">
												<button name="mark_public" value="${topic.id}">mark
													non-public</button>
											</form>
									</c:when>
									<c:otherwise>
										<td>
											<form method="post" action="Admin">
												<button name="mark_public" value="${topic.id}">mark
													public</button>
											</form>
									</c:otherwise>
								</c:choose>
					</c:forEach>



				</table>

				<!-- add new topic from -->
				<form method='post'>
					<h3>New topic:</h3>
					<input type="text" required="required" name="new_topic"
						placeholder="Topic name"> <br> <input type="submit"
						value="Submit">
				</form>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>