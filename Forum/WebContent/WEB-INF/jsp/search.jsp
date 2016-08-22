<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<input type="text" placeholder="Search text" id="tosearch" />
<hr/>
<div id="srch_results"></div>

<script id="srch_comment" type="text/template">
<div><a href="Welcome#red2com={{idTheme}}&ci={{idComment}}"> {{topic}} &gt; {{theme}} </a><br>
<div> {{comment}} </div>
Modified: {{lastModified}} 
</div>
</script>

<script id="srch_theme" type="text/template">
<div><a href="Welcome#red2com={{idTheme}}"> {{topic}} &gt; {{theme}} </a><br>
<div> {{comment}} </div>
Modified: {{lastModified}}
</div>
</script>

<script id="srch_topic" type="text/template">
<div><a href="Welcome#optop={{idTopic}}"> {{topic}} </a><br>
Modified: {{lastModified}}
</div>
</script>

<script id="srch_res" type="text/template">
<div style="background: #e6fff5;">
<span style="background: #99ffd6;">{{what}}</span> found in {{count}} results</div>
</script>

<script type="text/javascript" src="js/search.js"></script>