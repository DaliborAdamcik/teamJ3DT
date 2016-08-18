/**
 * An timer to start receive of fresh data
 */
var commentRefreshTimeout= undefined;
/**
 * Identifier of opened theme
 */
var themeIdentForComments= undefined;
/**
 * template for comments (mustache) 
 */
var commentTemplate=undefined;
/**
 * Prepares comment wiew to show and autorefresh webpage
 * @param themeId Unique ID of theme to show
 * @returns void
 * @author Dalibor Adamcik
 */
function loadComments(themeId)
{
	$('#commentBoxer').html('');
	themeIdentForComments = themeId;
	ajaxComments(false);
	commentRefreshTimeout = setInterval(ajaxComments, 30000);
	$("#welcome_pg").hide("slow");
	$("#comments_pg").show("slow");
	entityMenuButtonHide();
}

/**
 * Stops automatic synchronization of comments
 * @returns void
 * @author Dalibor Adamcik
 */
function stopCommentSynchonize() {
	if(commentRefreshTimeout)
	{
		clearInterval(commentRefreshTimeout);
		commentRefreshTimeout = undefined;
	}
}

/**
 * Calls servlet and receive comments
 * After first call (receive all) receives only changes  
 * @param news undefined = only news
 * @returns void
 * @author Dalibor Adamcik
 */
function ajaxComments(news) {
	$.ajax({
        type: "GET",
        url: "Comment/"+themeIdentForComments+"/"+(news===undefined?'newonly/':''),
        contentType:"application/json;charset=UTF-8",
        success: comments2page,
        error: function(resp) {ajaxFailureMessage(resp); showWelcomePage();}
    });
}

/**
 * Parses ajax response of call ajaxComments()
 * Adds visual elements to page, sets visibility of visual components (menu and so on)
 * @param response JSON representation of objects
 * @returns void
 */
function comments2page(response)
{
	try	{
		console.log(response);
		
	    if(response.error && response.error.type==='WEBNoPermissionException')
    	{
	    	showWelcomePage();
	    	alert("Sorry: "+response.error.message);
    	}

		if(response.comments) // draw received comments
			response.comments.forEach(comment2page);    
	    
		if(response.theme){
		    $('#themeId').val(response.theme.id);
		    $('#themeName').html(response.theme.topic.name + ' &gt; '+response.theme.name);
		    $('#themeDescription').text(response.theme.description);
		}
		
		if(response.deleted && response.deleted.length>0) 
			response.deleted.forEach( function(ident){
				var $old = $('#commentBoxer').find('#ent_'+ident);
				if($old.length!==0)
					$old.remove();
			});
		
	
		entityMenuButtonShow();
	    
	    if(user.role=="GUEST" || response.theme && response.theme.blocked)
	    	$('#addComment').hide();
	    else
	    	$('#addComment').show();
	}
	catch(err) {
		console.error("comments2page: ", err);
	}
}

/**
 * Draws comments on webpage 
 * @param comment An comment JSON to add to page in HTML format
 * @returns void
 */
function comment2page(comment){
	try {
		console.log(comment);
		comment.created = timeStmp2strDate(comment.created);
		if(comment.blocked)
			comment.blocked.created = timeStmp2strDate(comment.blocked.created);

	    var html = Mustache.to_html(commentTemplate, comment);
		var $old = $('#commentBoxer').find('#ent_'+comment.id);
		if($old.length!==0)
		$old.replaceWith(html);
		else
	    $('#commentBoxer').append($(html));
	}
	catch(err) {
		console.error("comment2page: ", err);
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
	        	comment2page(response.comment);

	        	var $cobo = $('#commentBoxer'); 
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
	
	commentTemplate = $('#commentTemplate').html();
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
	        	comment2page(response.comment);
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