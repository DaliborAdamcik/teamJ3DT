<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="container" align="center">
	<form method='post' name="regi" id="register">
		<div class="main_div">
			<label id="label">New User Registration:</label> <br> <br>
			<input type="text" required="required" id="reg_login"
				placeholder="Nick Name" autofocus> &nbsp;<img
				src="images/err.png" alt="state" id="reg_login_img" /><br>
			<div id="reg_login_state">Required field</div>
			<br>
			<!-- <input type="email" required="required" id="reg_email" placeholder="E-mail"> 
		<br> <br> -->
			<input type="text" required="required" id="reg_birthdate"
				placeholder="Date of birth"> &nbsp;<img src="images/err.png"
				alt="state" id="reg_birthdate_img" /><br>
			<div id="reg_birthdate_state">Required field</div>

			<br> <br> <input type="password" required="required"
				id="reg_pass1" placeholder="Password"> <br> <br> <input
				type="password" required="required" id="reg_pass2"
				placeholder="Re-Type Password" onkeyup="checkPass();"> <br>
			<span id="confirmMessage" class="confirmMessage"></span> <br> <br>
			<input type="submit" value="Submit">
		</div>
	</form>
</div>
<script src="js/register.js"></script>
