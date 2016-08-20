package sk.tsystems.forum.servlets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
// Import required java libraries
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
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
	
   private boolean isMultipart;
   private String filePath;
   private int maxFileSize = 200 * 1024;
   private int maxMemSize = 4 * 1024;

   public void init( ){
      // Get the file location where it would be stored.
      filePath = "C:/temp/";	
             //getServletContext().getInitParameter("file-upload"); 
   }
   public void doPost(HttpServletRequest request, 
               HttpServletResponse response)
              throws ServletException, java.io.IOException {
	   
	   File file;
	   
      // Check that we have a file upload request
      isMultipart = ServletFileUpload.isMultipartContent(request);
      response.setContentType("text/html");
      java.io.PrintWriter out = response.getWriter( );
      if( !isMultipart ){
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Servlet upload</title>");  
         out.println("</head>");
         out.println("<body>");
         out.println("<p>No file uploaded</p>"); 
         out.println("</body>");
         out.println("</html>");
         return;
      }
      DiskFileItemFactory factory = new DiskFileItemFactory();
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File("c:\\temp"));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );

      try{ 
      // Parse the request to get file items.
      List fileItems = upload.parseRequest(new ServletRequestContext(request));
	
      // Process the uploaded file items
      Iterator i = fileItems.iterator();

      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet upload</title>");  
      out.println("</head>");
      out.println("<body>");
      while ( i.hasNext () ) 
      {
         FileItem fi = (FileItem)i.next();
         if ( !fi.isFormField () )	
         {
            // Get the uploaded file parameters
            String fieldName = fi.getFieldName();
            String fileName = fi.getName();
            String contentType = fi.getContentType();
            boolean isInMemory = fi.isInMemory();
            long sizeInBytes = fi.getSize();
            // Write the file
            if( fileName.lastIndexOf("\\") >= 0 ){
               file = new File( filePath + 
               fileName.substring( fileName.lastIndexOf("\\"))) ;
            }else{
               file = new File( filePath + 
               fileName.substring(fileName.lastIndexOf("\\")+1)) ;
            }
            //fi.write( file ) ;
            
            try {
	            BufferedImage bi = ImageIO.read(fi.getInputStream());
	            ServletHelper svh = new ServletHelper(request);
	            User own = svh.getLoggedUser(); 
	            
	            if(own==null)
	            	throw new RuntimeException("you must be logged in");
	            	
	            ProfilePicture pp = ProfilePicture.profilePicture(own);
	            if(pp!=null)
	            {
	            	pp.setPicture(bi);
	            }
	            else
            		new ProfilePicture(own, bi);
	            
	            out.println("<img src=\"Picture/"+own.getId()+"/\">");
	            
	            //File outputfile = new File(file+".png");
	            //BufferedImage bisc = resize(bi, 50, 50);
	            //mageIO.write(bisc, "png", outputfile);
            } catch(java.lang.IllegalArgumentException exc) {
            	out.print(exc.getMessage());
            }
            catch(FieldValueException | EntityAutoPersist e)
            {
            	response.getWriter().println(e.getMessage());
            }
            
            
            
            
            out.println("Uploaded Filename: " + fileName + "<br>");
         }
      }
      out.println("</body>");
      out.println("</html>");
   }catch(Exception ex) {
       ex.printStackTrace();
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
			
			System.out.println(svh.getLoggedUser());
			System.out.println(pp);
			if("large".equals(url))
				pp.getPicture(response);
			else
				pp.getThubnail(response);
				
		} catch (URLParserException e) {
			System.out.println(e.getMessage());
			String path = request.getServletContext().getRealPath("/images/userPicture.png");
			
			File fi = new File(path);
			System.out.println("General picture exists: "+fi.exists()); 
			
            BufferedImage bi = ImageIO.read(fi.toURI().toURL());

    		response.setContentType("image/png");
			ImageIO.write(bi, "PNG", response.getOutputStream());
		}
	   
   } 
   
}

