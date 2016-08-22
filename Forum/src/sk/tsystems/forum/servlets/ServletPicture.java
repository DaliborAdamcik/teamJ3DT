package sk.tsystems.forum.servlets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import sk.tsystems.forum.entity.ProfilePicture;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.exceptions.EntityAutoPersist;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.helper.URLParser;
import sk.tsystems.forum.helper.exceptions.URLParserException;
import sk.tsystems.forum.servlets.master.MasterServlet;

@WebServlet("/Picture/*")
public class ServletPicture extends MasterServlet {
	private static final long serialVersionUID = 1L;
	
   private String filePath; // TODO *** LOAD THIS FROM CONFIG FILE
   private int maxFileSize = 750 * 1024;
   private int maxMemSize = 4 * 1024;

   public void init(){
      filePath = "C:/temp/"; // TODO **** create settings file to init this 
     //getServletContext().getInitParameter("file-upload"); 
   }

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		
		try {
			if(!ServletFileUpload.isMultipartContent(request))
				throw new FieldValueException("Accept only uploads.");
			
			ServletHelper svh = new ServletHelper(request);
			User owner = svh.getLoggedUser();

			if (owner == null)
				throw new FieldValueException("Only authentificated users can perform this action");
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(maxMemSize);
			factory.setRepository(new File(filePath));

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(maxFileSize);
			
			FileItem upFile = null;
			HashMap<String, String> mapFields = new HashMap<>(); 
			
			try {
				Iterator<FileItem> filetIt = upload.parseRequest(new ServletRequestContext(request)).iterator();
				
				while(filetIt.hasNext())
				{
					FileItem field = filetIt.next();
					if(field.isFormField())
						mapFields.put(field.getFieldName(), field.getString());
					else
						upFile = field;
				}
			} catch(org.apache.tomcat.util.http.fileupload.FileUploadBase.SizeLimitExceededException e) {
				throw new FieldValueException("File is too big", e);
			} catch(FileUploadException e) {
				throw new FieldValueException("Error durig upload: "+e.getMessage(), e);
			}

			if(upFile == null)
				throw new FieldValueException("No staged files");
			
			int xpos = intVal(mapFields.get("x"))*-1;
			int ypos = intVal(mapFields.get("y"))*-1;
			int width = intVal(mapFields.get("w"));
			int height = intVal(mapFields.get("h"));
			
			if(xpos<0 || ypos<0)
				throw new FieldValueException("Bad crop start position.");
			
		
			try {
				BufferedImage bi = ImageIO.read(upFile.getInputStream());
				
				if(width>bi.getWidth() || height>bi.getHeight())
					throw new FieldValueException("Bad crop size.");

				BufferedImage crop = bi.getSubimage(xpos, ypos, width, height);
				BufferedImage saveImg = new BufferedImage(crop.getWidth(), crop.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics g = saveImg.createGraphics();
				g.drawImage(crop, 0, 0, null);

				ProfilePicture pp = ProfilePicture.profilePicture(owner);
				if (pp != null) {
					pp.setPicture(saveImg);
				} else
					new ProfilePicture(owner, saveImg);
				
				HashMap<String, String> resp = new HashMap<>();
				resp.put("result", "ok");
				resp.put("message", "profile picture sucessfully changed");
				ServletHelper.jsonResponse(response, resp);
			} catch (java.lang.IllegalArgumentException exc) {
				ServletHelper.ExceptionToResponseJson(exc, response, false);
			} 
		} catch (FieldValueException | EntityAutoPersist e) {
			ServletHelper.ExceptionToResponseJson(e, response, false);
		} 
	}
	
	private int intVal(String str) throws FieldValueException {
		try {
			return Integer.parseInt(str);
		} catch(NullPointerException | NumberFormatException e) {
			throw new FieldValueException("Required parameters not set: "+str);
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		ServletHelper svh = new ServletHelper(request);
		try {
			URLParser url = svh.getURLParser();
			User user = url.getParentObject(User.class);
			if(user==null)
				throw new URLParserException("no user found");
			
			ProfilePicture pp = ProfilePicture.profilePicture(user);
			if(pp==null)
				throw new URLParserException("no profile picture assigned");
			
			if("large".equals(url.getAction()))
				pp.getPicture(response);
			else
				pp.getThubnail(response);
				
		} catch (URLParserException e) {
			String path = request.getServletContext().getRealPath("/images/userPicture.png");
			File fi = new File(path);
            BufferedImage bi = ImageIO.read(fi.toURI().toURL());
    		response.setContentType("image/png");
			ImageIO.write(bi, "PNG", response.getOutputStream());  
		}
	   
   } 
   
}

