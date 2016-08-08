//http://stackoverflow.com/questions/3831680/httpservletrequest-get-json-post-data
//$('form [name="reg"]');
$('#register').submit(function(ev){
	
    //var $form = $('#register');
    var jsobj = {};
    jsobj.name = $('#reg_login').val();

    //jsobj.name = $('#reg_email');
    jsobj.birth = $('#reg_birthdate').val(); //TODO check for valid date
    jsobj.pass = $('#reg_pass1').val(); //TODO check for valid format
    
    //confirmMessage
    console.log(jsobj);
    
    
    $.ajax({
        type: "POST",
        url: "Register",
        contentType:"application/json;charset=UTF-8",
        dataType: "json",
        data:JSON.stringify(jsobj),
        success: function (response) {
        	console.log(response);
        	
/*            if(response.id){
                console.log(response.id);
                $frm.trigger('reset');
                loadAnk(response.id);
                localStorage.setItem(localStorageName, "anketoval");
                $frm.hide();
                $('#hlasujzas').show();
            }*/
        },
        error: function (jxhr) {
            window.alert("Spracovanie neúspešné. Údaje neboli zapísané. Kód chyby:" + status + "\n" + jxhr.statusText + "\n" + jxhr.responseText);
        }
    });

    return false; // prevent subnit form
});

function checkPass() {
	var pass1 = document.getElementById('reg_pass1');
	var pass2 = document.getElementById('reg_pass2');
	var message = document.getElementById('confirmMessage');
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
}

