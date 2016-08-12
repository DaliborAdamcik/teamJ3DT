$('#register').submit(function(ev){
	try {
		if($('#reg_pass1').val()!=$('#reg_pass2').val())
		{
    		document.getElementById('confirmMessage').innerHTML = "Password and its confirmation is incorrect. Please retype.";
    		document.getElementById('confirmMessage').style.color = 'red';
			return false;
		}
		
		var jsobj = {};
	    jsobj.nick = $('#reg_login').val();
	    jsobj.realname = $('#reg_real').val();
	
	    //jsobj.name = $('#reg_email');
	    jsobj.birth =  date2str($( "#reg_birthdate" ).datepicker( "getDate" ), "dd.MM.yyyy");//TODO check for valid date
	    jsobj.pass = $('#reg_pass1').val(); //TODO check for valid format
	    
	    //confirmMessage
	    var sendobj = {};
	    sendobj.register = jsobj;
	    console.log(sendobj);
	    $.ajax({
	        type: "POST",
	        url: "Register",
	        contentType:"application/json;charset=UTF-8",
	        dataType: "json",
	        data: JSON.stringify(sendobj),
	        success: function (response) {
	        	console.log(response);
	        	if(response.error)
	        	{
	        		document.getElementById('confirmMessage').innerHTML = response.error;
	        		document.getElementById('confirmMessage').style.color = 'red';
	        		return;
	        	}
	
	        	if(response.registered && response.registered == true )
	        		window.location.href = window.location.href.replace('/Register', '');       
	        },
	        error: function (jxhr) {
	            window.alert("Spracovanie neúspešné. Údaje neboli zapísané. Kód chyby:" + status + "\n" + jxhr.statusText + "\n" + jxhr.responseText);
	        }
	    });

	}
	catch(err) {
	    console.log(err);
	} 
    return false; // prevent subnit form
});

function stateMessage(objID, message, state)
{
	var stateImg = 'err.png';
	switch(state)
	{
		case 'err': break;
		case 'ok': stateImg = 'ok.png'; break;
		case 'load': stateImg = 'loader.gif'; break;
		case 'critic': stateImg = 'warn_red.png'; break;
		case 'warning': stateImg = 'warn_yel.png'; break;
		default:
			console.log("stateMessage invalid option: ", state); break;
	}
	
	$('#'+objID+'_img').attr('src', 'images/'+stateImg);
	$('#'+objID+'_state').text(message);
}

/*Checks user-name is correct and validates is free( online)
 * TODO Can be associated another event of input  
 */
$('#reg_login').keyup(function(e){
	var $newnick = $('#reg_login').val();
	
	console.log($newnick);
	if($newnick.length<4)
	{
		stateMessage('reg_login', 'Your new nickname is too short', 'err');
		return;
	}

	var testValidChars = /^([a-z][a-z0-9]{3,})$/g;
	if(!testValidChars.test($newnick))
	{
		stateMessage('reg_login', 'Nickname can contain only a-z and 0-9 characters.', 'err');
		return;
	}
	
	stateMessage('reg_login', 'Validating availibility online', 'load');
	
    var jsobj = {};
    jsobj.checknick = {};
    jsobj.checknick.nick = $newnick;
    
    console.log(jsobj, JSON.stringify(jsobj));

    $.ajax({
        type: "POST",
        url: "Register",
        contentType:"application/json;charset=UTF-8",
        dataType: "json",
        data:JSON.stringify(jsobj),
        success: function (response) {
        	console.log(response);
        	if(response.exists)
    		{
        		stateMessage('reg_login', 'Nickname already exists, please type another nickname', 'warning');
    		}
        	else
        	if(response.error)
    		{
        		stateMessage('reg_login', 'LET: '+response.error, 'err');
    		}
        	else
        		stateMessage('reg_login', 'Your nickname is free for fregistration', 'ok');
        },
        error: function (jxhr) {
    		stateMessage('reg_login', 'ERROR - Cant check nickname online: '+ status + " / " + jxhr.statusText, 'critic');
            // jxhr.responseText
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

function date2str(x, y) {
    var z = {
        M: x.getMonth() + 1,
        d: x.getDate(),
        h: x.getHours(),
        m: x.getMinutes(),
        s: x.getSeconds()
    };
    y = y.replace(/(M+|d+|h+|m+|s+)/g, function(v) {
        return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1))).slice(-2)
    });

    return y.replace(/(y+)/g, function(v) {
        return x.getFullYear().toString().slice(-v.length)
    });
}
