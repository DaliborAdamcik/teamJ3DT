<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="css/rating.css" />
<c:forEach items="${allcomments}" var="comment">
<table border="1">
<tr><td id="tdupvote_${comment.id}"><button onclick="upvote(${comment.id})" id="upvote_${comment.id}" class="upvote_button"></button>
<tr><td id="tdrating_${comment.id}">${comment.rating.rating}
<tr><td id="tddownvote_${comment.id}"><button onclick="downvote(${comment.id})" id="downvote_${comment.id}" class="downvote_button"></button>
</table>
${comment.comment}
<br><br>
</c:forEach>

<script type="text/javascript" src="js/rating.js"></script>
