<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<input type="text" placeholder="Search text" id="tosearch" />
<div id="srch_results">

</div>

<script id="topicTemplate" type="text/template">
<h3 id="ent_{{id}}_tit">{{name}}
	<button type="button" class="close entitymenucls"
		aria-hidden="true"
		onclick="newThemeDlgPopup({{id}});">New	theme</button>
</h3>
<div id="ent_{{id}}_cont"></div>
</script>

<script type="text/javascript" src="js/search.js"></script>