package sk.tsystems.forum.servlets.master;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.service.jpa.CommentJPA;
import sk.tsystems.forum.service.jpa.TopicJPA;
import sk.tsystems.forum.service.jpa.UserJPA;

public abstract class MasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CURRENT_USER_ATTRIB = "CURRENT_USER";
	/**
	 * This method initializes every Servlet thats implement this abstract class
	 * We use ServletHelper (here) to:
	 *  * Initialize and save services instances
	 *    
	 * WARNING: Please, don't place here any code that can be directly send to client 
	 * 	e.g including another servlet / jsp
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("**** Master SERVLET initialization *****"); // TODO only for debug purposes, please remove in production mode
		ServletHelper servletHelper = new ServletHelper(request);
		servletHelper.setService(new UserJPA()); // Put all services we need to run
		servletHelper.setService(new TopicJPA());
		servletHelper.setService(new CommentJPA());
		
		// we can do some global checks here
		
		// globally set current user
		request.setAttribute(CURRENT_USER_ATTRIB, servletHelper.getLoggedUser());
		
		super.service(request, response); // this line cant be comment out in case of any situation 
	}
}
