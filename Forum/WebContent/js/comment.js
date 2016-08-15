function loadComments(themeId)
{
	console.log(themeId);
	$.ajax({
        type: "GET",
        url: "Comment/"+themeId+"/",
        contentType:"application/json;charset=UTF-8",
        success: comments2page,
        error: function (jxhr) {
    		alert('ERROR - Cant check nickname online: '+ status + " / " + jxhr.statusText);
            // jxhr.responseText
        }
    });
}

function comments2page(response)
{
	console.log(response);

	var template = $('#commentTemplate').html();
    var html = Mustache.to_html(template, response);
    $('#commentBoxer').html(html);
    
    $('#themeId').val(response.theme.id);
    $('#themeName').html(response.theme.topic.name + ' &gt; '+response.theme.name);
    $('#themeDescription').text(response.theme.description);
    
    if(response.role && response.role!="GUEST")
    	$('#addComment').show();
}


$('#addComment').submit(function(ev){
	try {
		var jsobj = {};
	    jsobj.comment = $('#newComment').val();
	
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

var $commentMenu = $('#commentMenu');
var $commentEditDlg = $('#editCommentDlg');
var $commentEditForm = $('#editCommentFrm');

var commentMenuChildID = null;

function commentUIinit()
{
	  $commentMenu.menu();
	  $commentEditDlg.dialog({
	  autoOpen: false,
	  show: {
	    effect: "blind",
	    duration: 750
	  },
	  hide: {
	    effect: "blind",
	    duration: 750
	  },
	  height: "auto",
      width: "auto"
	});
}

function commentMenuPopup(ident) {
	// close opened dialogs first
	$commentEditDlg.dialog('close');
	
	var e = window.event;
	$commentMenu.css({'top':e.pageY-50,'left':e.pageX, 'position':'absolute', 'border':'1px solid black', 'padding':'5px'});
	$commentMenu.show();
	commentMenuChildID = ident;
}

function commentMenuItemClick(itemname)
{
	$commentMenu.hide();
	switch(itemname)
	{
		case 'close': break; // we dont need to do nothing there
		case 'edit': commentEditDlgPopup(); break;
		default:
			alert('menu action not implemented: '+itemname)
	}
}

function commentEditDlgPopup()
{
	$commentEditDlg.find("input").val($('#comment_'+commentMenuChildID+'_txt').html());
	$commentEditDlg.dialog('open');
}

$commentEditForm.submit(function(ev){
	try {
		var jsobj = {};
	    jsobj.comment = $commentEditDlg.find("input").val();
	    jsobj.id = commentMenuChildID;
	    
	    console.log(jsobj);
	    $.ajax({
	        type: "POST",
	        url: "Comment/"+ commentMenuChildID +"/",
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

