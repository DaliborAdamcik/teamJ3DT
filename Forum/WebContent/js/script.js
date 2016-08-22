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

function timeStmp2strDate(timestamp) {
	var dat = new Date(timestamp);
	return dat.toGMTString();
}

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

	$('#alertDlg').dialog($.extend({ 
		height: "auto",
		width: "auto", 
		buttons: {
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
	
	// common YesNo dlg
	$('#yesNoCommonDlg').dialog($.extend({
			resizable: true,
			height: "auto",
			width: "auto",
			buttons: {
				"Yes": yesNoCommonDlg_answer_yes,
				"No": yesNoCommonDlg_answer_no
			}
		}, $comonDlgOpts));
});

//AlertDlg ---------------------------------------------------------
/**
 * Shows common alert dialog. 
 * Callback template: function(bool answer, function cbcparam){}
 * @param title Dialog title
 * @param message Message in dialog
 * @param style = load icon, default info
 * @author Dalibor Adamcik
 */
function alertDlg(title, message, style) {
	var $dlg = $('#alertDlg').clone().appendTo( document.body );
	$dlg.dialog($.extend({ 
		height: "auto",
		width: "auto", 
		buttons: {
			Close: function() {
				$( this ).dialog( "close" );
				$( this ).remove();
			}
		}
	}, $comonDlgOpts));
	
	$dlg.dialog('option', 'title', title);
	$dlg.find('#alertDlg_msg').html(message);
	if(style==undefined)
		style="info";
	$dlg.find('img').attr('src', 'images/aldlg/'+style+'.png');
	$dlg.dialog('open');
}

/**
 * Show an error dialog
 * @param errSON an eeror response from servlet
 */
function errorDlg(errSON) {
	if(errSON.error == undefined)
		return;
	alertDlg("Error: "+errSON.error.type, errSON.error.message, 'error');
}

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
	        type: "PUT",
	        url: "./Welcome/"+$blockDlg.data('ident')+"/block/",
			contentType : "application/json;charset=UTF-8",
			dataType : "json",
			data : JSON.stringify({ 
		    	block: $blockDlg.data('ident'), 
		    	block_reason: reason
		    }),
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
	errorDlg(response);
	try
	{
		var $dlg = $('#blockCommonDlg');
		if($dlg.data('calltome')!=undefined)
		$dlg.data('calltome')(response, $dlg.data('ident'), $dlg.find("input").val().trim());
	}
	catch(err) {console.log("cant call callback", err);}
}

/**
 * Unblock entity
 * Administrative option
 * @param ident an unique ID of entity to be unblocked
 * @param callback an function to be called after success ajax (function (response, ident))
 * @returns void
 * @author 
 */
function unblockCommonDlgPopup(ident, callback){
	var jsobj = {};
	jsobj.id = ident;
	$.ajax({
		type : "PUT",
		url : "Admin/" + ident + "/unblock",
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		data : JSON.stringify(jsobj),
		success : function(response) {
			errorDlg(response);
			try {
				callback();
			}
			catch(err) {}
		},
		error : ajaxFailureMessage
	});
}

/**
 * Remove entity dialog
 * User option
 * @param ident an unique ID of entity to be removed
 * @param callback an function to be called after success ajax (function (response, ident))
 * @returns void
 * @author Dalibor Adamcik
 */
function removeCommonDlgPopup(ident, callback){
	if(callback === undefined)
		callback = removeCommonDlg_defaultCallBack;
	
	// TODO get entity information and show (eg coment or other)
	var type = $('#ent_'+ident).data('etype');
	var subtext = undefined;
	switch(type) {
		case 'comment': subtext = $('#ent_'+ident+'_txt').html(); break;
		case 'theme': subtext = $('#ent_'+ident+'_name').html(); break;
	}

	yesNoCommonDlg('Remove '+type, 'Do you want remove '+type+(subtext===undefined?'':' "'+subtext+'"')+'?', 
			removeCommonDlg_answer, {id: ident, cbc: callback});
}


/**
 * An default callback function for remove entity common dialog
 * @param response an response from server
 * @param ident an ident of removed entity
 */
function removeCommonDlg_defaultCallBack(response, ident) {
	console.log(response, ident);
	// todo check response here
	$('#ent_'+ident).hide('slow');	
}


/**
 * Callback function to remomoveCommonDlgPopup()
 * @param answer boolean 
 * @param cbcparam call back parameter (to remove callback)
 */
function removeCommonDlg_answer(answer, cbcparam)
{
	if(!answer)
		return;

	$.ajax({
        type: "POST",
        url: "./Welcome",
	    data: { 
	    	block: cbcparam.id, 
	    	block_reason: 'Erased by OWNER'
	    },
        success: function (resp) {
        	errorDlg(resp);
        	try {
        		console.log(cbcparam);
        		cbcparam.cbc(resp, cbcparam.id);
        	}
        	catch(err) {
        		console.error("Cant call callback in removeCommonDlg_answer / ajax: ", err);
        	}
        },
        error: ajaxFailureMessage
    });
}
// YES / NO dialog ---------------------------------------------------------
/**
 * Shows common yes no dialog. 
 * Callback template: function(bool answer, function cbcparam){}
 * @param title Dialog title
 * @param message Message in dialog
 * @param cbcfunc An function to be called after click on yes/no button
 * @param cbcparam Parameters to be send to callback function
 * @author Dalibor Adamcik
 */
function yesNoCommonDlg(title, message, cbcfunc, cbcparam) {
	var $dlg = $('#yesNoCommonDlg');
	$dlg.dialog('option', 'title', title);
	$('#yesNoCommonDlg_message').html(message);
	$dlg.data('callback', cbcfunc);
	$dlg.data('backparam', cbcparam);
	$dlg.dialog('open');
}

function yesNoCommonDlg_answer_yes()
{
	yesNoCommonDlg_answer(true);
}
function yesNoCommonDlg_answer_no()
{
	yesNoCommonDlg_answer(false);	
}

/**
 * Call callback for YES/NO dialog
 * @param answer an option YES / NO
 * @author Dalibor Adamcik
 */
function yesNoCommonDlg_answer(answer) {
	var $dlg = $('#yesNoCommonDlg');
	$dlg.dialog('close');
	
	var cbcfunc = $dlg.data('callback');
	var cbcparam = 	$dlg.data('backparam');
	try { 
		cbcfunc(answer, cbcparam);
	}
	catch(err) {
		console.error("Yes/No dialog: error calling callback ", err);
	}
}

// ajax failure dialog ---------------------------------------------------------
/**
 * this is called after ajax failure response
 */
function ajaxFailureMessage(jxhr) {
	var $ajxErrorDlg = $('#ajaxErrorDlg')
	
    $ajxErrorDlg.html(Mustache.to_html($('#ajaxErrorDlg_tmpl').html(), jxhr));
	$ajxErrorDlg.dialog('open');
}

//Welcome page show / hide ---------------------------------------------------------
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
		entityMenuButtonHide();
		stopCommentSynchonize();
	}
	return false;
}