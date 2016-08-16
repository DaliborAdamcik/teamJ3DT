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
    
    if(user.role=="GUEST")
    {
    	$('#addComment').hide();
    	$('.commentmenucls').hide();
    }
    else
    {
    	$('#addComment').show();
    }
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

var $commentMenu = $('#commentMenu'); // comment menu
var $commentEditDlg = $('#editCommentDlg'); // dialog, edit comment
var $commentEditForm = $('#editCommentFrm'); // form inside dialog edit comment

var $commentRemoveDlg = $('#eraseCommentDlg'); // dialog, edit comment

var commentMenuChildID = null; // currently selected comment (popup menu opened)

/**
 * Initiates  visual components on page (visual style)
 * @returns void
 */
function commentUIinit()
{
	$commentMenu.menu(); // comment menu
	// edit dialog
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
	  
	// remove dialog
	$commentRemoveDlg.dialog($.extend({
		resizable: true,
		height: "auto",
		width: 400,
		buttons: {
			"Yes": commentRemoveDlgRemove,
			"No": function() {
				$( this ).dialog( "close" );
			}
		}
	}, $comonDlgOpts));
}

/**
 * Shows menu for comment
 * @param ident unique identifier of comment
 * @returns void
 */
function commentMenuPopup(ident) {
	commentMenuChildID = ident;
	
	var e = window.event;
	$commentMenu.css({'top':e.pageY-50,'left':e.pageX, 'position':'absolute', 'border':'1px solid black', 'padding':'5px'});
	var owner = $('#ent_'+commentMenuChildID).data("owner");

	console.log(user.id, owner);
	if(user.id!=owner && user.role!='ADMIN')
		$commentMenu.find('.commentMenuOwnerOption').addClass('ui-state-disabled');
	
	if(user.id==owner || user.role=='ADMIN')
		$commentMenu.find('.commentMenuOwnerOption').removeClass('ui-state-disabled');

	if(user.role=='ADMIN')
		$commentMenu.find('.commentMenuAdminOption').removeClass('ui-state-disabled');
	else
		$commentMenu.find('.commentMenuAdminOption').addClass('ui-state-disabled');
	
	$commentMenu.show();
}

/**
 * An logic for what to do on menu item click
 * @param itemname an name of clicked item.
 * @returns void
 */
function commentMenuItemClick(itemname)
{
	$commentMenu.hide();
	switch(itemname)
	{
		case 'close': break; // we dont need to do nothing there
		case 'edit': commentEditDlgPopup(); break;
		case 'remove': commentRemoveDlgPopup(); break;
		case 'block': blockCommonDlgPopup(commentMenuChildID); break;
		
		default:
			alert('menu action not implemented: '+itemname);
	}
}

/**
 * Show edit dialog for comment
 * @returns
 */
function commentEditDlgPopup()
{
	$commentEditDlg.find("input").val($('#comment_'+commentMenuChildID+'_txt').html());
	$commentEditDlg.dialog('open');
}

/**
 * This is called when edit dialog is confirmed
 * @returns void
 */
function commentEditDlgModify(){
	// TODO disable form
	try {
		var jsobj = {};
	    jsobj.comment = $commentEditDlg.find("input").val().trim();
	    jsobj.id = commentMenuChildID;
	    
	    if(jsobj.comment.length==0)
    	{
	    	alert("comment cant be empty");
	    	return;
    	}
	    
	    console.log(jsobj);
	    $.ajax({
	        type: "POST",
	        url: "Comment/"+ commentMenuChildID +"/",
	        contentType:"application/json;charset=UTF-8",
	        dataType: "json",
	        data: JSON.stringify(jsobj),
	        success: function (response) {
	        	$commentEditDlg.dialog('close');
	        	console.log(response);
	        	$('#comment_'+commentMenuChildID+'_txt').html(response.comment.comment);
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

/**
 * Show remove dialog
 * @returns void
 */
function commentRemoveDlgPopup() {
	$commentRemoveDlg.find('#eraseComment_txt').html($('#comment_'+commentMenuChildID+'_txt').html());
	$commentRemoveDlg.dialog('open');
}

/**
 * This is called when remove is confirmed
 * @returns
 */
function commentRemoveDlgRemove() {
	// TODO not yet implemented
	alert("not yet implemented");
	$commentRemoveDlg.dialog('close');
}