var cropSize = {x:0, y:0, w:0, h:0};
var origSize = {w:0, h:0};
var destSize = {w:128, h:128};
var $cropper = undefined;
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
        	var oldlink =  $('#avatar_preview').attr('src');
        	
            $('#avatar_preview_siz').attr('src', e.target.result);
            origSize.w = $('#avatar_preview_siz').width();
            origSize.h = $('#avatar_preview_siz').height();
            
            if(origSize.w<destSize.w || origSize.h<destSize.h)
        	{
            	alertDlg("Profile picture", "Image is too small.\n"+
            			"Required: "+destSize.w+" x "+destSize.h+
            			"\nYour image: "+origSize.w+" x "+origSize.h, "warn");
                   $('#avatar_preview').attr('src', oldlink);
            	return;
        	}
            
            if($cropper===undefined)
        	{
                $('#avatar_preview').attr('src', e.target.result);
	            $cropper = $('img.cropimg').cropper({
	            	  aspectRatio: 1 / 1,
	            	  crop: function(e) {
	            		cropSize = e;
	/*					console.log(e.x);
						console.log(e.y);
						console.log(e.width);
						console.log(e.height);
						console.log(e.rotate);
						console.log(e.scaleX);
						console.log(e.scaleY);*/
	            	    
		            	$('.btn-crop').show();
	            	  }
	            	});
        	}
            else
            	$cropper.cropper('replace', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$("#avatar_select").change(function(){
    readURL(this);
});

$('.btn-crop').on('click', function() {
	if(cropSize.x<0 || cropSize.y<0 || cropSize.width+cropSize.x> origSize.w || cropSize.height+cropSize.y> origSize.h) {
		alertDlg("Profile picture", "Image must fit into crop window.");
		return;
	}

	if(cropSize.width< destSize.w || cropSize.height< destSize.h) {
		alertDlg("Profile picture", "Resolution is too low. Please, zoom in.");
		return;
	}
	
	$( "#wait-upload" ).dialog('open');
    var file_data = $('#avatar_select').prop('files')[0];   
    var form_data = new FormData();                  
    form_data.append('file', file_data);
    form_data.append('w', Math.floor(cropSize.width));
    form_data.append('h', Math.floor(cropSize.height));
    form_data.append('x', Math.floor(cropSize.x));
    form_data.append('y', Math.floor(cropSize.y));
    
    $.ajax({
        url: 'Picture/',  
        cache: false,
        contentType: false,
        processData: false,
        data: form_data,                         
        type: 'POST',
        error: ajaxFailureMessage,
        success: function(response){
        	$( "#wait-upload" ).dialog('close');
        	console.log(response);
        	if(ajxErrorDlg(response))
        		return;
        	$cropper.cropper('destroy');  
        	$cropper = undefined;
        	$('.btn-crop').hide();
        	
            $('#avatar_preview').attr('src', "Picture/"+user.id+"/large");
        	
    		alertDlg("Profile picture", 'Congratulations, Your new avatar is set.');
        }
     });
});

$( "#wait-upload" ).dialog({
	autoOpen: false,
	dialogClass: "no-close",
	resizable: false,
    height: "auto",
    width: "auto",
    modal: true
});
