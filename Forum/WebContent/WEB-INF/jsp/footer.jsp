<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css" />


<footer>
	<div class="container" align="center">
		<hr>
		&copy; 2016 made by J3DT <br> (Jano, Jozef, Janka, Dalibor,
		Tomas)<br/>
		<a href="https://github.com/DaliborAdamcik/teamJ3DT/" target="_blank">Project on GitHub</a> &#8226;
		<a href="doc/index.html" target="_blank">Java doc</a> &#8226;
		<a href="diagrams.zip" target="_blank">Diagrams</a> 
	</div>
</footer>

<!--  DO NOT REMOVE THEESE TAGS -->
<div id="blockCommonDlg" title="Block">
	<p>
		Block reason:<br /> <input class="form-control" type="text"
			placeholder="Block reason" />
	</p>
</div>

<div id="alertDlg" title="Alert dialog" style="display: none">
	<span style="float: left"><img src="images/err.png" alt="nic"></span> 
	<span id="alertDlg_msg"></span>
</div>

<div id="yesNoCommonDlg" title="Yes No title">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 12px 12px 20px 0;"></span> <span
			id="yesNoCommonDlg_message"></span>
	</p>
</div>

<div id="ajaxErrorDlg" title="Error during AJAX request"
	style="background-color: #ff9999;"></div>
<script id="ajaxErrorDlg_tmpl" type="text/template">
<h2>Error during AJAX request - {{status}}: {{statusText}}</h2>
Server response:<div>{{{responseText}}}</div>
</script>


</body>
</html>