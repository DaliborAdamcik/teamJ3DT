/**
 * Shows menu for entity
 * @param ident unique identifier of comment
 * @returns void
 */
function entityMenuPopup(ident) {
	var $menu = $('#entityMenu');
	$menu.data('ident', ident);

	var etype = undefined;
	var owner = undefined;
	try {
		var $entity = $('#ent_'+ident);
		etype = $entity.data('etype');
		owner = $entity.data('owner');
		
		if($entity == undefined || etype == undefined || owner == undefined)
			throw "Cant get options for entity id: "+ident;
	}
	catch(err) {
		console.error("entityMenuPopup: Cannot poput entity menu. ReaSON:", err);
		return;
	}
	$('#entityMenu_iEdit').off("click");
	$('#entityMenu_iEdit').click(function () { entityMenuItemClick(etype+'.edit'); });
	
	if(user.id!=owner && user.role!='ADMIN')
		$menu.find('.entMenuOwnerOption').addClass('ui-state-disabled');
	
	if(user.id==owner || user.role=='ADMIN')
		$menu.find('.entMenuOwnerOption').removeClass('ui-state-disabled');

	if(user.role=='ADMIN')
		$menu.find('.entMenuAdminOption').removeClass('ui-state-disabled');
	else
		$menu.find('.entMenuAdminOption').addClass('ui-state-disabled');
	
	var e = window.event;
	$menu.css({'top':e.pageY-50,'left':e.pageX, 'position':'absolute', 'border':'1px solid black', 'padding':'5px'});
	$menu.show();
}

/**
 * An logic for what to do on menu item click
 * @param itemname an name of clicked item.
 * @returns void
 */
function entityMenuItemClick(itemname)
{
	var $menu = $('#entityMenu');
	$menu.hide();
	var ident = $menu.data('ident');
	switch(itemname)
	{
		case 'close': break; // we dont need to do anything there
		case 'owner.remove': removeCommonDlgPopup(ident); break;
		case 'admin.block':  blockCommonDlgPopup(ident); break;
		case 'admin.unblock':  unblockCommonDlgPopup(ident); break;

		case 'theme.edit':  editThemeDlgPopup(ident); break;
		case 'comment.edit':  commentEditDlgPopup(ident); break;
		
		default:
			console.error('Entity popup menu action not implemented: ', itemname);
	}
}

/**
 * Shows / hides popup menu buttons on page
 */
function entityMenuButtonShow()
{
    if(user.role=="GUEST")
    	$('.entitymenucls').hide();
    else
    	$('.entitymenucls').show();
}

function entityMenuButtonHide()
{
	$('#entityMenu').hide();
}

/* DONT MODIFY OR REMOVE */
$('#entityMenu').menu();