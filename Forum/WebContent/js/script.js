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

/******* DO NOT REMOVE, DO NOT MODIFY ***/

/**
 * Current user state on page
 */
var user = {
	role: "GUEST",
	id: -1
};

/**
 * Common dialog options
 */
var $comonDlgOpts =
{
	autoOpen: false,
	modal: true,
	show: {
		effect: "blind",
		duration: 500
	},
	hide: {
		effect: "blind",
		duration: 500
	}
}

/**
 * Prepare common UI to show
 * @author Dalibor Adamcik
 */
$( document ).ready(function() {
	// block dialog settings
	$('#blockCommonDlg').dialog($.extend({ 
		height: "auto",
		width: "auto", 
		buttons: {
			"Block": blockCommonDlg_DoBlock,
			Close: function() {
				$( this ).dialog( "close" );
			}
		}
	}, $comonDlgOpts));
	
	
	// ajax common error dialog
	$('#ajaxErrorDlg').dialog($.extend({ 
		height: "auto",
		width: "auto", 
		buttons: {
			Close: function() {
				$( this ).dialog( "close" );
			}
		}
	}, $comonDlgOpts));	
	
});

/**
 * Show block dialog
 * Administrative option
 * @param ident an unique ID of entity to be blocked
 * @param callback an function to be called after success ajax (function (response, ident))
 * @returns void
 * @author Dalibor Adamcik
 */
function blockCommonDlgPopup(ident, callback)
{
	var $blockDlg = $('#blockCommonDlg'); 
	$blockDlg.data('ident', ident);
	$blockDlg.data('calltome', callback);
	$blockDlg.find("input").val('');
	$blockDlg.dialog('open');
}

/**
 * This is called when block is confirmed
 * @returns void
 * @author Dalibor Adamcik
 */
function blockCommonDlg_DoBlock()
{
	var $blockDlg = $('#blockCommonDlg');
	
	// TODO disable form
	try {
	    var reason = $blockDlg.find("input").val().trim();
	    
	    if(reason.length==0)
    	{
	    	alert("reason cant be empty"); // TODO temporary message box
	    	return;
    	}
	    

	    $.ajax({
	        type: "POST",
	        url: "./Welcome",
		    data: { 
		    	block: $blockDlg.data('ident'), 
		    	block_reason: reason
		    },
	        success: blockCommonDlg_BlockSucces,
	        error: ajaxFailureMessage
	    });

	}
	catch(err) {
	    console.log(err);
	} 
	finally {
		// TODO enable form
	}
	$blockDlg.dialog('close');
}

/**
 * Called after success of ajax call for block
 */
function blockCommonDlg_BlockSucces(response) {
	console.log("blockCommonDlg_BlockSucces response:", response);
	try
	{
		var $dlg = $('#blockCommonDlg');
		if($dlg.data('calltome')!=undefined)
		$dlg.data('calltome')(response, $dlg.data('ident'), $dlg.find("input").val().trim());
	}
	catch(err) {console.log("cant call callback", err);}
}

function bajkolajko(aber, burde)
{
	console.log("callback", aber, burde);
}


/**
 * this is called after ajax failure response
 */
function ajaxFailureMessage(jxhr) {
	var $ajxErrorDlg = $('#ajaxErrorDlg')
	
    $ajxErrorDlg.html(Mustache.to_html($('#ajaxErrorDlg_tmpl').html(), jxhr));
	$ajxErrorDlg.dialog('open');
}

function showWelcomePage()
{
	var formumname = 'Forum';
	var loc = window.location.href;
	loc = loc.substring(loc.indexOf(formumname)+formumname.length).replace('/', '').replace('Welcome', '');
	if(loc.length>0)
		window.location.href= "Welcome";
	else {
		$("#welcome_pg").show("slow");
		$("#comments_pg").hide("slow");
	}
	return false;
}
