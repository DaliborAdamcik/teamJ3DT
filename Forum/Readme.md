1. About .gitignore file .classpath
	File .classpath can contain specific settings for each developer (environment / Eclipse IDE).
	At first time You clone project from GitHub You can use published version of .classpath file.
	After clone, You need (probably) modify libraries in Your IDE (IDE showing error in project - Java build path problem):
	a) Right click on project (menu is shown), then click "Properties".
	b) In left tree view select "Java build path"
	c) Select tab "Libraries"
	d) Select "JRE System library" and click on button "Remove"
	e) Click button "Add library" and select (in new opened window) "JRE System library",  than click Next
	f) Select radio "Workspace default", click "Finish" (dialog closed) and OK
	g) Check Your project for error "Java build path problem". At a success accomplish of this task, 
	   this error must disappear.  
		
 
	
	  