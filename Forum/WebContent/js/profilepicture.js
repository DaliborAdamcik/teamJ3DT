var cropSize = {x:0, y:0, w:0, h:0};
var origSize = {w:0, h:0};
var destSize = {w:300, h:300};

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
        	var oldlink =  $('#avatar_preview').attr('src');
        	
            $('#avatar_preview').attr('src', e.target.result);
            origSize.w = $('#avatar_preview').width();
            origSize.h = $('#avatar_preview').height();
            console.log(origSize);
            
            if(origSize.w<destSize.w || origSize.h<destSize.h)
        	{
            	alert("Image is too small.\n"+
            			"Required: "+destSize.w+" x "+destSize.h+
            			"\nYour image: "+origSize.w+" x "+origSize.h);
                   $('#avatar_preview').attr('src', oldlink);
            	return;
        	}
            	
            if($('img.cropimg').data("cred"))
            	return;
                
            $('img.cropimg').data("cred", true);
            $('img.cropimg').cropimg({
              resultWidth: destSize.w,
              resultHeight: destSize.h,
              onChange: function(x, y, w, h, image) {
            	cropSize.x = x;
            	cropSize.y = y;
            	cropSize.w = w;
            	cropSize.h = h;
            	$('.btn-crop').show();
              }
            });
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$("#avatar_select").change(function(){
    readURL(this);
});

$('.btn-crop').on('click', function() {
	console.log(cropSize, cropSize.x>0, cropSize.y>0, cropSize.w> origSize.w, cropSize.h> origSize.h);
	console.log(cropSize.h + cropSize.y < destSize.h , cropSize.w + cropSize.x < destSize.w);
	console.log(cropSize.h + cropSize.y , destSize.h , cropSize.w + cropSize.x , destSize.w);
	if(cropSize.x>0 || cropSize.y>0 || cropSize.w> origSize.w || cropSize.h> origSize.h) {
		alert("Image must fit into crop window.");
		return;
	}

	if( cropSize.h + cropSize.y < 128 || cropSize.w + cropSize.x < 128	) {
		alert("Final resolution is too low. Please zoom out.");
		return;
	}
	
	$( "#wait-upload" ).dialog('open');
    var file_data = $('#avatar_select').prop('files')[0];   
    var form_data = new FormData();                  
    form_data.append('file', file_data);
    form_data.append('w', cropSize.w);
    form_data.append('h', cropSize.h);
    form_data.append('x', cropSize.x);
    form_data.append('y', cropSize.y);
    
    $.ajax({
        url: 'Picture/',  
        dataType: 'text', 
        cache: false,
        contentType: false,
        processData: false,
        data: form_data,                         
        type: 'POST',
        success: function(response){
            $('#avatar_preview').attr('src', "Picture/"+user.id+"/large");
        	$('.btn-crop').hide();
        	$('img.cropimg').data('cropimg').reset();
        	$( "#wait-upload" ).dialog('close');
        	console.log("pic change reponse", response); // TODO responses
        }
     });
});

$( "#wait-upload" ).dialog({
	autoOpen: false,
	dialogClass: "no-close",
	resizable: false,
    height: "auto",
    width: "auto",
    modal: true/*,
    buttons: {
        "Wait": function() {},
    }  */

  });
