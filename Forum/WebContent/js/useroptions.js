var pass1 = document.getElementById('userinfo_password');
var pass2 = document.getElementById('userinfo_confirmpassword');
var message = document.getElementById('pass_message');
var goodColor = "#009933";
var badColor = "#990000";

if (pass1.value == pass2.value) {
	pass2.style.backgroundColor = goodColor;
	message.style.color = goodColor;
	message.innerHTML = "Passwords Match!"
} else {
	pass2.style.backgroundColor = badColor;
	message.style.color = badColor;
	message.innerHTML = "Passwords Do Not Match!"
}







