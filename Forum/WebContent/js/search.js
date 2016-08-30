var templateComment = $('#srch_comment').html();
var templateTheme = $('#srch_theme').html();
var templateTopic = $('#srch_topic').html();
var templateResults = $('#srch_res').html();
var locker = null;
var lastse = "";

$('#tosearch').keyup(function(ev){
	if(locker)
		clearTimeout(locker);
		
	locker = setTimeout(doSrch, 1000);
});

function doSrch() {
	var waht = $('#tosearch').val().trim();
	if(waht.length<2 || waht==lastse)
		return;
	
	lastse = waht;
	
    $.ajax({
        type: "GET",
        url: "Search",
        data: {srch: waht},
        success: function (response) {
    		$('#resultcount').html(Mustache.to_html(templateResults, response));
			$('#srch_results').html('');
        	if(ajxErrorDlg(response))
    		{
        		return;
    		}
        	
        	var colopt = response.what.trim().replace(/[\s]+/g, ".*");
        	
        	if(response.result)
        		response.result.forEach(function(res){
        			var rendered;
    				res.lastModified = timeStmp2strDate(res.lastModified);
    				if(res.comment)
    					res.comment = colorize(colopt, res.comment);

    				
        			if(res.idComment>0) {
        				rendered = Mustache.to_html(templateComment, res);
        			}
        			else
        			if(res.idTheme>0) {
        				rendered = Mustache.to_html(templateTheme, res);
        			}
        			else
        				rendered = Mustache.to_html(templateTopic, res);
        			
        			$('#srch_results').append($(rendered));
        		});    
        },
        error: ajaxFailureMessage
    });
   	
}

function colorize(what, where) {
	var res = where;
	
	var re = new RegExp('(?!<span class="hili">)('+what+')(?!<\/span>)', "gmi");
	res = res.replace(re, '<span class="hili">$1</span>');
	
	return res;
}