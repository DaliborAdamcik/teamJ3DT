<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<input type="file" name="file" size="50" id="avatar_select" accept="Image/*"/>
<button class="btn-crop" style="display: none">Set your avatar</button>
<br/>
<img src="Picture/${CURRENT_USER.id}/large/" alt="" id="avatar_preview" class="cropimg">

<div id="wait-upload" title="Uploading... Please wait">
  <p>Please wait while upload of your avatar is finished.</p>
</div>

<style>
.no-close .ui-dialog-titlebar-close {
  display: none;
}
</style>


<script type="text/javascript" src="js/profilepicture.js"></script>
<script type="text/javascript" src="imgcrop/jquery.mousewheel.js"></script>
<script type="text/javascript" src="imgcrop/cropimg.jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="imgcrop/resource/cropimg.css" />
<link rel="stylesheet" type="text/css" href="imgcrop/resource/font-awesome.min.css" />