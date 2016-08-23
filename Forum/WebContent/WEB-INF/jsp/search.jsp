<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
	#srch_results > div { margin-top: 1em;}
	#srch_results > div > span { font-size:0.8em; color: #666699; }
	#srch_results > div > a { font-size: 1.3em }
	.hili {background-color: #cc99ff;}
</style>
<div style="margin: 1em;">
	<input type="text" placeholder="Search text" id="tosearch" /><span id="resultcount" style="font-size: 1.1em; padding-left: 2em;"></span>
	<hr/>
	<div id="srch_results"></div>
</div>
<script id="srch_comment" type="text/template">
<div><a href="Welcome#red2com={{idTheme}}&ci={{idComment}}"> {{topic}} &gt; {{theme}} </a><br>
<div> {{{comment}}} </div>
<span>Modified: {{lastModified}} </span> 
</div>
</script>

<script id="srch_theme" type="text/template">
<div><a href="Welcome#red2com={{idTheme}}"> {{topic}} &gt; {{theme}} </a><br>
<div> {{{comment}}} </div>
<span>Modified: {{lastModified}} </span> 
</div>
</script>

<script id="srch_topic" type="text/template">
<div><a href="Welcome#optop={{idTopic}}"> {{topic}} </a><br>
<span>Modified: {{lastModified}} </span> 
</div>
</script>

<script id="srch_res" type="text/template">
<span style="background: #99ffd6;">{{what}}</span> found in {{count}} results
</script>

<script type="text/javascript" src="js/search.js"></script>