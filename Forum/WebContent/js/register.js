//http://stackoverflow.com/questions/3831680/httpservletrequest-get-json-post-data
//$('form [name="reg"]');
$('#reg').submit(function(ev){
	
    var $form = $('#reg');
    console.log($form);
    console.log($form.serialize());
    console.log($form.serializeObject());
    console.log(JSON.stringify($form.serializeObject()));
    console.log(JSON.stringify($form.serialize()));
    console.log(JSON.stringify($form));
	
    
    return false; // prevent subnit form
});

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

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

