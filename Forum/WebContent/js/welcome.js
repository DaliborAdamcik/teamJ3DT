/**
 * Initiates  visual components on page (visual style)
 * @returns void
 */
function welcomeUIinit()
{
	// list of topics and themes
    $( "#topicList" ).accordion({
      heightStyle: "content"
    });

    // edit theme dialog
    $( "#editThemeDlg" ).dialog($.extend({ 
		height: "auto",
		width: "auto",
		buttons: {
			"Save": editThemeDlg_save,
			Close: function() {
				$( this ).dialog( "close" );
			}
		}
	}, $comonDlgOpts));
}

/**
 * Draws theme  on webpage 
 * @param theme An theme JSON to add to page in HTML format
 * @returns void
 */
function theme2page(theme){
	try {
		console.log(theme);
	    var html = Mustache.to_html(commentTemplate, theme);
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
 * Edit theme dialog
 * @param themeid Unique ID of theme to edit
 * @author Dalibor Adamcik
 */
function editThemeDlgPopup(themeid){
	var $edthdlg = $( "#editThemeDlg" );
	$edthdlg.dialog('option', 'title', 'Edit theme');
	$edthdlg.data('topicid', 0);
	$edthdlg.data('themeid', themeid);
	
	if(themeid>0)
    $.ajax({
        type: "GET",
        url: "Welcome/"+ themeid +"/theme",
        success: function (response) {
        	console.log(response);
        	$('#editThemeDlg').data("theme", response.theme);
        	editThemeDlg_data2field();
        	$('#editThemeDlg').dialog('open');
        },
        error: ajaxFailureMessage
    });
	else
		alert('theme must be specified. Backward compatibility error.');
}

/**
 * New theme dialog
 * @param topicId ID of topic to be parrent of Theme
 * @author Dalibor Adamcik
 */
function newThemeDlgPopup(topicId){
	var $edthdlg = $( "#editThemeDlg" );
	$edthdlg.dialog('option', 'title', 'New theme');
	$edthdlg.data('topicid', topicId);
	$edthdlg.data('themeid', 0);
	editThemeDlg_erase();
	$edthdlg.dialog('open');
}


/**
 * Clears forms in edit theme dialog
 */
function editThemeDlg_erase() {
	var $edthdlg = $('#editThemeDlg');

	if($edthdlg.data('themeid')==0) // empty dlg, erase
	{
		$('#editThemeDlg_name').val('');
		$('#editThemeDlg_desc').val('');
		$('#editThemeDlg_pub').prop('checked', true);
		$('#editThemeDlg_ownerInfo').hide();
		return;
	}
}

function editThemeDlg_data2field() {
	var $edthdlg = $('#editThemeDlg');
	var theme = $edthdlg.data("theme");
	$('#editThemeDlg_name').val(theme.name);
	$('#editThemeDlg_desc').val(theme.description);
	$('#editThemeDlg_pub').prop('checked', theme.isPublic);
	$('#editThemeDlg_ownerInfo').html(Mustache.to_html($('#editThemeDlg_ownerInfo_tmpl').html(), theme));
	$('#editThemeDlg_ownerInfo').show();
}

function editThemeDlg_save(){
	var $edthdlg = $("#editThemeDlg");
	
	var topicId =$edthdlg.data('topicid');
	var themeId = $edthdlg.data('themeid');

	if(topicId==0 && themeId==0 || topicId>0 && themeId>0)
	{
		alert('Error: Nothing to edit');
		return
	}
	
	var jsobj = {
		name: $('#editThemeDlg_name').val().trim(),
		description: $('#editThemeDlg_desc').val().trim(),
		isPublic: $('#editThemeDlg_pub').prop('checked')
	};
	
	try {
		if(jsobj.name.legth==0 || jsobj.description.legth==0)
    	{
	    	alert("Name and description is required fields.");
	    	return;
    	}
		
	    $edthdlg.dialog('close');    
	    console.log(jsobj);
	    $.ajax({
	        type: (themeId>0?'POST':'PUT'),
	        url: 'Welcome/'+ (themeId>0?themeId:topicId)+'/theme/',
	        contentType:"application/json;charset=UTF-8",
	        dataType: "json",
	        data: JSON.stringify(jsobj),
	        success: function (response) {
	        	console.log("edit resp: ", response);
	        	alert('not implementšed succeass of edittheme');
	        },
	        error: ajaxFailureMessage
	    });

	}
	catch(err) {
	    console.log(err);
	}
}
/* do not remove */
welcomeUIinit();