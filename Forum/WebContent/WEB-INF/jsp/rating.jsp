<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="css/rating.css" />
<c:forEach items="${allcomments}" var="comment">
<table border="1">
<tr><td id="tdupvote_${comment.id}"><button onclick="upvote(${comment.id})" id="upvote_${comment.id}" class="upvote_button_black"></button>
<td id="tdrating_${comment.id}">${comment.rating.rating}
<td id="tddownvote_${comment.id}"><button onclick="downvote(${comment.id})" id="downvote_${comment.id}" class="downvote_button_black"></button>
</table>
${comment.comment}
<br><br>
</c:forEach>
<script id="ratingtemplate" type="text/template">
<table>
	<tr><td id="tdupvote_{{id}}"><button onclick="upvote({{id}})" id="upvote_{{id}}" class="upvote_button_black"></button>
	<td id="tdrating_{{id}}">{{rating.rating}}
	<td id="tddownvote_{{id}}}"><button onclick="downvote({{id}})" id="downvote_{{id}}" class="downvote_button_black"></button>
</table>
</script>
<script type="text/javascript" src="js/rating.js"></script>
<script>initializeRateButtons();</script>