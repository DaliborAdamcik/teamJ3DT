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

function editThemeDlgPopup(themeid){
	var $edthdlg = $( "#editThemeDlg" );
	$edthdlg.dialog('option', 'title', 'Edit theme');
	$edthdlg.data('themeid', themeid)
	
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
	
	var thsend = {
		name: $('#editThemeDlg_name').val().trim(),
		description: $('#editThemeDlg_desc').val().trim(),
		isPublic: $('#editThemeDlg_pub').prop('checked')
	};
	
	
	console.log(thsend);
	
	var theme = $edthdlg.data("theme");

	

	
	$edthdlg.dialog('close');
}

function newThemeDlgPopup(topicId){
	var $edthdlg = $( "#editThemeDlg" );
	$edthdlg.dialog('option', 'title', 'New theme');
	$edthdlg.data('topicid', topicId)
	editThemeDlg_erase();
	$edthdlg.dialog('open');
}

/* do not remove */
welcomeUIinit();