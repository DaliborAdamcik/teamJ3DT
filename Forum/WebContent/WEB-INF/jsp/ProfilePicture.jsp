<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<input type="file" name="file" size="50" id="avatar_select" accept="Image/*"/>
<button class="btn-crop" style="display: none">Set your avatar</button>
<br/>
<img src="Picture/${CURRENT_USER.id}/large/" alt="" id="avatar_preview" class="cropimg">
<img src="Picture/${CURRENT_USER.id}/large/" alt="" id="avatar_preview_siz" style="display: none;">

<div id="wait-upload" title="Uploading... Please wait">
  <p>Please wait while upload of your avatar is finished.</p>
</div>

<style>
.no-close .ui-dialog-titlebar-close {
  display: none;
}
</style>
<script type="text/javascript" src="js/profilepicture.js"></script>
<script type="text/javascript" src="cropper/cropper.min.js"></script>
<link rel="stylesheet" type="text/css" href="cropper/cropper.min.css" />
