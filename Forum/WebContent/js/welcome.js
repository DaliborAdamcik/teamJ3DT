/**
 * An timer to start receive of fresh data
 */
var templateTheme;
var templateTopic;
var welcomeDrawObjects;
var listenEvents = false;

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
    
    if(window.location.href.indexOf("#asuser")>0)
    	user.role="REGULARUSER";
    
    var themeIdToShow = hasTagParam('red2com');
    if(!isNaN(themeIdToShow))  // redirect to comments
    	loadComments(themeIdToShow);

    if(window.location.href.indexOf("#asadmin")>0)
    	user.role="ADMIN";
    
    themes2page();
    ajaxEvents(); 
    
    var openTopicFromSrch = hasTagParam('optop');
    if(!isNaN(openTopicFromSrch))
	{
    	var indexFS = $('#topicList').find('#ent_'+openTopicFromSrch+'_tit').index()-1;
		$('#topicList').accordion( "option", "active", indexFS );
	}
    
}

/**
 * Calls servlet and receive comments
 * After first call (receive all) receives only changes  
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
 * Calls Events servlet for changes 
 * @returns void
 * @author Dalibor Adamcik
 */
function ajaxEvents() {
	$.ajax({
        type: "GET",
        url: "Events",
        contentType:"text/plain",
        success: function (resp) {
        	console.log("Events: ",resp);
        	if(resp=="changed") {
        		ajaxWelcome();
        		if(themeIdentForComments!=undefined)
        			ajaxComments();
        	}
        	else
        	ajaxEvents();
        },
        error: function(resp) {ajaxFailureMessage(resp);}
    });
}


/**
 * Stops automatic synchronization of wecome page
 * @returns void
 * @author Dalibor Adamcik
 */
function stopWelcomeSynchonize() {
}

function indexInArr(array, ident) {
	return array.map(function(x) {return x.id; }).indexOf(ident);
}

function renewStoredObject(resp) {
	ajaxEvents();
	console.log(resp);
	if(ajxErrorDlg(resp))
			return;
	
	if(resp.topics)
		resp.topics.forEach(renewStoredTopic);
	
	if(resp.themes)
		resp.themes.forEach(renewStoredTheme);    
	
	if(resp.erased)
	resp.erased.forEach(function(ident){
		var $erased = $('#topicList').find('#ent_'+ident);
		if($erased.length==0)
		{
			$erased = $('#topicList').find('#ent_'+ident+'_tit');
			var $sub = $('#topicList').find('#ent_'+ident+'_cont');
			if($erased.length>0)
				$erased.remove();

			if($sub.length>0)
				$sub.remove();
		}
		else
		$erased.remove();
		
		console.log($erased);
		
		
	});    
	
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
		entityMenuButtonShow();
	}
}

function paintTheme(theme){
	try {
		if(theme.painted)
			return;
		theme.painted = true;
		
		theme.created = timeStmp2strDate(theme.created);
		if(theme.blocked)
			theme.blocked.created = timeStmp2strDate(theme.blocked.created);

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

		// TODO TU JE RYZZZAAAAA
		console.log("bejka", topic);
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
        	if(ajxErrorDlg(response))
        		return;
        	
        	$('#editThemeDlg').data("theme", response.theme);
        	editThemeDlg_data2field();
        	$('#editThemeDlg').dialog('open');
        },
        error: ajaxFailureMessage
    });
	else
		alertDlg('Cant edit theme', 'Theme must be specified. Backward compatibility error.', 'error');
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
	theme.created = timeStmp2strDate(theme.created);
	$('#editThemeDlg_ownerInfo').html(Mustache.to_html($('#editThemeDlg_ownerInfo_tmpl').html(), theme));
	$('#editThemeDlg_ownerInfo').show();
}

function editThemeDlg_save(){
	var $edthdlg = $("#editThemeDlg");
	
	var topicId =$edthdlg.data('topicid');
	var themeId = $edthdlg.data('themeid');

	if(topicId==0 && themeId==0 || topicId>0 && themeId>0)
	{
		alertDlg("Edit / create theme",'Nothing to edit.', 'error');
		return
	}
	
	var jsobj = {
		name: $('#editThemeDlg_name').val().trim(),
		description: $('#editThemeDlg_desc').val().trim(),
		isPublic: $('#editThemeDlg_pub').prop('checked')
	};
	
	console.log("to edit", jsobj);
	try {
		if(isEmptyString(jsobj.name) || isEmptyString(jsobj.description))
    	{
	    	alertDlg((themeId>0?"Edit":"Create")+" theme", "Name and description is required fields.", "warn");
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
	        		response.theme.modified = response.theme.modified+1; 
	        		renewStoredTopic(response.theme.topic);
	        		renewStoredTheme(response.theme);
	        		themes2page();
	        		console.log("safasdfgsdhd");
	        	}
	        	ajxErrorDlg(response);
	        },
	        error: ajaxFailureMessage
	    });

	}
	catch(err) {
	    console.log(err);
	}
}