<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="main_div">
	<label id="label">Already registered? &middot; </label> <a href="login.html">Login</a>
	<br>
	<label id="label">Not registered yet? &middot; </label> <a href="regform.html">Registration</a>
	<br> <br>
</div>
<form method='post'>
	<label id="label">New Topic:</label>
	<br> <br>
	<input type="text" required="required" name="new_topic_name" placeholder="New Topic Name" autofocus> 
	<br> <br>
	<input type="submit" value="Submit">
	<br> <br>
</form>
	