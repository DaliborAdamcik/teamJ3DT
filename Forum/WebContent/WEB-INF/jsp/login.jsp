<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


	<form method='post'>
		<div class="main_div">
			<label id="label">Log in:</label>
			<br> <br>
			<input type="text" required="required" id="user_login" name = "user_login"placeholder="Nick Name" autofocus> 
			<br> <br> 
			<input type="password" required="required" id="user_pass" name = "user_pass" placeholder="Password"> 
			<br> <br> <br>
			<input type="submit" value="Submit">
			<br> <br>
<!-- 		<a href="Register">Not registered yet? Click here to register.</a> -->
			<br>
<!-- 		<a href="welcome.html">Back to welcome page</a> -->
			<jsp:include page="footer.jsp"/>
			
		</div>
	</form>
	
