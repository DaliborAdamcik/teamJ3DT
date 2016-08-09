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
	<%-- <jsp:include page="header.jsp"></jsp:include> --%>
	<c:choose>
		<c:when test="${loggeduser==null ||loggeduser.role !='ADMIN'}">
			<h1>Only admins can view this page</h1>
		</c:when>
		<c:otherwise>
			<table>
				<c:forEach items="${listofusers}" var="user">
					<tr>
						<td>${user.userName}
						<td>${user.blocked}
						<td><c:choose>
								<c:when test="${user.blocked ==null}">
									<button>ban</button>
								</c:when>
								<c:otherwise>
									<button>unban</button>
								</c:otherwise>
							</c:choose>

							<button>promote</button>
							<button>demote</button>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>