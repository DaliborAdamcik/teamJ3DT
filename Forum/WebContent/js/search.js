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
	// TODO test length
	var waht = $('#tosearch').val().trim();
	if(waht.length<2 || waht==lastse)
		return;
	
	lastse = waht;
	
    $.ajax({
        type: "GET",
        url: "Search",
       //dataType: "application/json",
        data: {srch: waht},
        //contentType:"application/json",
        success: function (response) {
        	//console.log(response);
    		$('#srch_results').html(Mustache.to_html(templateResults, response));
        	if(ajxErrorDlg(response))
    		{
        		return;
    		}
        	
        	var colopt = response.what.split(" "); 
        	
        	if(response.result)
        		response.result.forEach(function(res){
        			var rendered;
    				res.lastModified = timeStmp2strDate(res.lastModified);
    				
        			if(res.idComment>0) {
        				rendered = Mustache.to_html(templateComment, res);
        			}
        			else
        			if(res.idTheme>0) {
        				rendered = Mustache.to_html(templateTheme, res);
        			}
        			else
        				rendered = Mustache.to_html(templateTopic, res);
        			
        			$('#srch_results').append($(colorize(colopt, rendered)));
        		});    
        },
        error: function (jxhr) {
            window.alert("Spracovanie neúspešné. Údaje neboli zapísané. Kód chyby:" + status + "\n" + jxhr.statusText + "\n" + jxhr.responseText);
        }
    });
   	
}

function colorize(what, where) {
	var res = where;
	what.forEach(function(wh){
		var co = wh.trim();
		if(co.length>0)
		{
			var re = new RegExp('(?!;">)('+co+')(?!</s)', "gmi");
			res = res.replace(re, '<span style="background-color: #bf80ff;">'+co+'</span>');
		}
	});
	
	return res;
}