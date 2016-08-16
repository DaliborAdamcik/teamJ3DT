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
    
    // theme menu
    $('#themeMenu').menu(); 
}

function themeMenuPopup(themeid)
{
	var $thmenu = $('#themeMenu'); 
	$thmenu.data('ident', themeid);
	// save ident to data
	var e = window.event;
	$thmenu.css({'top':e.pageY-50,'left':e.pageX, 'position':'absolute', 'border':'1px solid black', 'padding':'5px'});
	$thmenu.show();
}

function themeMenuItemClick(itemname)
{
	var $thmenu = $('#themeMenu'); 
	$thmenu.hide();
	var ident = $thmenu.data('ident');
	switch(itemname)
	{
		case 'close': break; // we dont need to do nothing there
		case 'edit': editThemeDlgPopup(ident); break;
		
		default:
			alert('menu action not implemented: '+itemname);
	}
}


function editThemeDlgPopup(themeid){
	var $edthdlg = $( "#editThemeDlg" );
	$edthdlg.dialog('option', 'title', (themeid==0?'New':'Edit')+' theme');
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
	{
		editThemeDlg_data2field();
		$edthdlg.dialog('open');
	}
}

function editThemeDlg_data2field(){
	var $edthdlg = $('#editThemeDlg');

	if($edthdlg.data('themeid')==0) // empty dlg, erase
	{
		$('#editThemeDlg_name').val('');
		$('#editThemeDlg_desc').val('');
		$('#editThemeDlg_pub').prop('checked', true);
		$('#editThemeDlg_ownerInfo').hide();
		return;
	}
	
	var theme = $edthdlg.data("theme");
	$('#editThemeDlg_name').val(theme.name);
	$('#editThemeDlg_desc').val(theme.description);
	$('#editThemeDlg_pub').prop('checked', theme.isPublic);
	$('#editThemeDlg_ownerInfo').html(Mustache.to_html($('#editThemeDlg_ownerInfo_tmpl').html(), theme));
	$('#editThemeDlg_ownerInfo').show();
}

function editThemeDlg_save(){
	var $edthdlg = $( "#editThemeDlg" );
	$edthdlg.dialog('open');
}
