<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form method='post'>
	<div class="main_div">
		<h1>F &middot; O &middot; R &middot; U &middot; M</h1>
		<label id="label">New User Registration:</label>
		<br> <br>
		<input type="text" required="required" id="reg_login" placeholder="Nick Name" autofocus> 
		<br> <br> 
		<input type="email" required="required" id="reg_email" placeholder="E-mail"> 
		<br> <br> 
		<input type="text" required="required" id="reg_birthdate" placeholder="Date of birth"> 
		<br> <br> 
		<input type="password" required="required" id="reg_pass1" placeholder="Password"> 
		<br> <br> 
		<input type="password" required="required" id="reg_pass2" placeholder="Re-Type Password" onkeyup="checkPass();"> 
		<br>
		<span id="confirmMessage" class="confirmMessage"></span>
		<br> <br>
		<input type="submit" value="Submit">
		<br> <br>
		<label id="label">Already registered? &middot; </label> <a href="login.html">Login</a>
		<br>
		<a href="welcome.html">Back to welcome page</a>

	</div>
</form>
