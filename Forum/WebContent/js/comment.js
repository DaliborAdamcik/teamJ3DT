/**
 * Reteive list of comments and topic (json, ajax call)
 * Result of ajax call is passed into function comments2page  
 * @param themeId Unique ID of theme to show
 * @returns void
 */
function loadComments(themeId)
{
	$.ajax({
        type: "GET",
        url: "Comment/"+themeId+"/",
        contentType:"application/json;charset=UTF-8",
        success: comments2page,
        error: ajaxFailureMessage
    });
	
	$("#welcome_pg").hide("slow");
	$("#comments_pg").show("slow");
	entityMenuButtonHide();
}

/**
 * Parses ajax response of call loadComments(thmeId)
 * Adds visual elemnts to page, sets visibility of visual components (menu and so on) 
 * @param response JSON representation of objects
 * @returns void
 */
function comments2page(response)
{
	console.log(response);
	var template = $('#commentTemplate').html();
    var html = Mustache.to_html(template, response);
    $('#commentBoxer').html(html);
    
    $('#themeId').val(response.theme.id);
    $('#themeName').html(response.theme.topic.name + ' &gt; '+response.theme.name);
    $('#themeDescription').text(response.theme.description);
    
    entityMenuButtonShow();
    
    if(user.role=="GUEST")
    	$('#addComment').hide();
    else
    	$('#addComment').show();
    
}

/**
 * An submit logic for form addComment
 * Ajax call sends new comment to servlet.
 * Reteives (on success) new comment (json object) and shows it visually on page
 */
$('#addComment').submit(function(ev){
	//TODO disable form
	
	try {
		var jsobj = {};
	    jsobj.comment = $('#newComment').val().trim();
	    
	    if(jsobj.comment.length == 0)
    	{
    		alert("please specify your comment");
    		return false;
    	}
	
	    console.log(jsobj);
	    $.ajax({
	        type: "PUT",
	        url: "Comment/"+ $('#themeId').val() +"/",
	        contentType:"application/json;charset=UTF-8",
	        dataType: "json",
	        data: JSON.stringify(jsobj),
	        success: function (response) {
	        	console.log(response);
	        	var simul = {};
	        	simul.comments= [response.comment];
	        	console.log(simul);
	        	
	        	var template = $('#commentTemplate').html();
	            var html = Mustache.to_html(template, simul);
	            var $cobo = $('#commentBoxer')
	            
	            $cobo.html($cobo.html()+html);
	            $cobo.scrollTop($cobo.prop("scrollHeight"));
	            $('#newComment').val("");
	        },
	        error: ajaxFailureMessage
	    });

	}
	catch(err) {
	    console.log(err);
	} 
	finally {
		//TODO enable form
	}
    return false; // prevent subnit form
});

var $commentEditDlg = $('#editCommentDlg'); // dialog, edit comment
var $commentEditForm = $('#editCommentFrm'); // form inside dialog edit comment

/**
 * Initiates  visual components on page (visual style)
 * @returns void
 */
function commentUIinit()
{
	$commentEditDlg.dialog($.extend({ 
		height: "auto",
		width: "auto",
		buttons: {
			"Edit": commentEditDlgModify,
			Close: function() {
				$( this ).dialog( "close" );
			}
		}
	}, $comonDlgOpts));
}

/**
 * Show edit dialog for comment
 * @parameter ident an Unique ID of comment
 * @returns
 */
function commentEditDlgPopup(ident)
{
	$commentEditDlg.find("input").val($('#ent_'+ident+'_txt').html());
	$commentEditDlg.data('ident', ident);
	$commentEditDlg.dialog('open');
}

/**
 * This is called when edit dialog is confirmed
 * @returns void
 */
function commentEditDlgModify(){
	// TODO disable form
	try {
		var ident = $commentEditDlg.data('ident');
		var jsobj = {};
	    jsobj.comment = $commentEditDlg.find("input").val().trim();
	    jsobj.id = ident;
	    
	    if(jsobj.comment.length==0)
    	{
	    	alert("comment cant be empty");
	    	return;
    	}
	    
	    console.log(jsobj);
	    $.ajax({
	        type: "POST",
	        url: "Comment/"+ ident +"/",
	        contentType:"application/json;charset=UTF-8",
	        dataType: "json",
	        data: JSON.stringify(jsobj),
	        success: function (response) {
	        	$commentEditDlg.dialog('close');
	        	console.log(response);
	        	$('#ent_'+ident+'_txt').html(response.comment.comment);
	        },
	        error: ajaxFailureMessage
	    });

	}
	catch(err) {
	    console.log(err);
	} 
	finally {
		// TODO enable form
	}
}

commentUIinit();
