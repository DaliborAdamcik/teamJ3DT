/**
 * An timer to start receive of fresh data
 */
var welcomeRefreshTimeout= undefined;
var templateTheme;
var templateTopic;
var welcomeDrawObjects;

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
    
    templateTheme = $('#themeTemplate').html();
    templateTopic = $('#topicTemplate').html();
    welcomeDrawObjects = JSON.parse($('#themesFirst').html());
    console.log(welcomeDrawObjects);
    
    themes2page();
    welcomeRefreshTimeout = setInterval(ajaxWelcome, 30000);
}

/**
 * Calls servlet and receive comments
 * After first call (receive all) receives only changes  
 * @param news undefined = only news
 * @returns void
 * @author Dalibor Adamcik
 */
function ajaxWelcome() {
	$.ajax({
        type: "GET",
        url: "Welcome/0/news",
        contentType:"application/json;charset=UTF-8",
        success: renewStoredObject,
        error: function(resp) {ajaxFailureMessage(resp);stopWelcomeSynchonize();}
    });
}

/**
 * Stops automatic synchronization of comments
 * @returns void
 * @author Dalibor Adamcik
 */
function stopWelcomeSynchonize() {
	if(welcomeRefreshTimeout)
	{
		clearInterval(welcomeRefreshTimeout);
		welcomeRefreshTimeout = undefined;
	}
}

function indexInArr(array, ident) {
/*	var elementPos = array.map(function(x) {return x.id; }).indexOf(ident);
	var objectFound = array[elementPos];*/	
	return array.map(function(x) {return x.id; }).indexOf(ident);
}

function renewStoredObject(resp) {
	console.log(resp);
	resp.topics.forEach(renewStoredTopic);    
	resp.themes.forEach(renewStoredTheme);    

	// TODO process erased
	// TODO impelement remove
	
	themes2page();
}


function renewStoredTheme (theme) {
	var index = indexInArr(welcomeDrawObjects.themes, theme.id);
	if(index<0)			
		welcomeDrawObjects.themes.push(theme);
	else
	{
		if(welcomeDrawObjects.themes[index].modified<theme.modified)
			welcomeDrawObjects.themes[index] = theme;
		console.log("repkalop", index, welcomeDrawObjects.themes[index]);
	}
}

function renewStoredTopic(topic) {
	var index = indexInArr(welcomeDrawObjects.topics, topic.id);
	if(index<0)			
		welcomeDrawObjects.topics.push(topic);
	else
	{
		if(welcomeDrawObjects.topics[index].modified<topic.modified)
			welcomeDrawObjects.topics[index] = topic;
		console.log("repkalop", index, welcomeDrawObjects.topics[index]);
	}
}

/**
 * Draws themes on webpage 
 * @param theme An theme JSON to add to page in HTML format
 * @returns void
 */
function themes2page(){
	try {
		welcomeDrawObjects.topics.forEach(paintTopic);    
		welcomeDrawObjects.themes.forEach(paintTheme);    
	}
	catch(err) {
		console.error("themes2page: ", err);
	}
	finally {
		$('#topicList').accordion('refresh');
	}
}

function paintTheme(theme){
	try {
		if(theme.painted)
			return;

		theme.painted = true;
		var html = Mustache.to_html(templateTheme, theme);
	    
		var $destobj = $('#topicList').find('#ent_'+theme.topicId+'_cont');
		if($destobj.length===0)
		{
			console.error("Topic not found id:", theme.topicId);
			return;
		}
	    
		var $old = $destobj.find('#ent_'+theme.id); 
		if($old.length!==0)
			$old.replaceWith(html);
		else
			$destobj.append($(html));
	}
	catch(err) {
		console.error("paintTheme: ", err);
	}
}

function paintTopic(topic)
{
	try {
		if(topic.painted)
			return;
		topic.painted = true;
		
	    var html = Mustache.to_html(templateTopic, topic);
/*		var $old = $('#topicList').find('#ent_'+comment.id);
		if($old.length!==0)
		$old.replaceWith(html);
		else*/
	    $('#topicList').append($(html));
	}
	catch(err) {
		console.error("paintTheme: ", err);
	}
}


/**
 * Edit theme dialog
 * @param themeid Unique ID of theme to edit
 * @author Dalibor Adamcik
 */
function editThemeDlgPopup(themeid){
	var $edthdlg = $( "#editThemeDlg" );
	$edthdlg.dialog('option', 'title', 'Edit theme');
	$edthdlg.data('topicid', 0);
	$edthdlg.data('themeid', themeid);
	
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

/**
 * New theme dialog
 * @param topicId ID of topic to be parrent of Theme
 * @author Dalibor Adamcik
 */
function newThemeDlgPopup(topicId){
	var $edthdlg = $( "#editThemeDlg" );
	$edthdlg.dialog('option', 'title', 'New theme');
	$edthdlg.data('topicid', topicId);
	$edthdlg.data('themeid', 0);
	editThemeDlg_erase();
	$edthdlg.dialog('open');
}


/**
 * Clears forms in edit theme dialog
 */
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
	
	var topicId =$edthdlg.data('topicid');
	var themeId = $edthdlg.data('themeid');

	if(topicId==0 && themeId==0 || topicId>0 && themeId>0)
	{
		alert('Error: Nothing to edit');
		return
	}
	
	var jsobj = {
		name: $('#editThemeDlg_name').val().trim(),
		description: $('#editThemeDlg_desc').val().trim(),
		isPublic: $('#editThemeDlg_pub').prop('checked')
	};
	
	try {
		if(jsobj.name.legth==0 || jsobj.description.legth==0)
    	{
	    	alert("Name and description is required fields.");
	    	return;
    	}
		
	    $edthdlg.dialog('close');    
	    console.log(jsobj);
	    $.ajax({
	        type: (themeId>0?'POST':'PUT'),
	        url: 'Welcome/'+ (themeId>0?themeId:topicId)+'/theme/',
	        contentType:"application/json;charset=UTF-8",
	        dataType: "json",
	        data: JSON.stringify(jsobj),
	        success: function (response) {
	        	console.log("edit resp: ", response);
	        	if(response.theme) {
	        		renewStoredTopic(response.theme.topic);
	        		renewStoredTheme(response.theme);
	        		themes2page();
	        	}
        		if(response.error){
        			console.error(response.error);
    	        	alert('Error: '+error.type + " "+error.message);
        		}
	        		
	        	
	        },
	        error: ajaxFailureMessage
	    });

	}
	catch(err) {
	    console.log(err);
	}
}

function addNewTopic() {
	var jsobj = {
		name: $("#new_topic_txt").val().trim()
	//	isPublic: $('#new_topic_che').prop('checked')
	};
	
	try {
		if(jsobj.name.legth==0)
    	{
	    	alert("Name is required field.");
	    	return;
    	}
		
	    console.log(jsobj);
	    $.ajax({
	        type: 'PUT',
	        url: 'Welcome/0/topic/',
	        contentType:"application/json;charset=UTF-8",
	        dataType: "json",
	        data: JSON.stringify(jsobj),
	        success: function (response) {
	        	console.log("new topic resp: ", response);
	        	if(response.topic) {
	        		renewStoredTopic(response.topic);
	        		themes2page();
	        	}
        		if(response.error){
        			console.error(response.error);
    	        	alert('Error: '+error.type + " "+error.message);
        		}
	        },
	        error: ajaxFailureMessage
	    });

	}
	catch(err) {
	    console.log(err);
	}
}
