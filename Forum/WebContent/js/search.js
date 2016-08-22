$('#tosearch').keyup(function(ev){
	// TODO test length
	
    $.ajax({
        type: "GET",
        url: "Search",
       //dataType: "application/json",
        data: {srch: $('#tosearch').val()},
        //contentType:"application/json",
        success: function (response) {
        	console.log(response);
        	if(ajxErrorDlg(response))
    		{
        		$('#srch_results').html("");
        		return;
    		}
    		$('#srch_results').html(response);
        },
        error: function (jxhr) {
            window.alert("Spracovanie neúspešné. Údaje neboli zapísané. Kód chyby:" + status + "\n" + jxhr.statusText + "\n" + jxhr.responseText);
        }
    });
    
});