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

function commentmenu(ident) {
	var e = window.event;
	var $commentmenu = $('#mousemenu');
	$commentmenu.css({'top':e.pageY-50,'left':e.pageX, 'position':'absolute', 'border':'1px solid black', 'padding':'5px'});
	$commentmenu.show();
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


