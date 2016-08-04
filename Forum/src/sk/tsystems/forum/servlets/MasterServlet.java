package sk.tsystems.forum.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.serviceinterface.CommentInterface;
import sk.tsystems.forum.serviceinterface.TopicInterface;
import sk.tsystems.forum.serviceinterface.UserInterface;
import sk.tsystems.forum.servlets.master.GetServiceException;

public abstract class MasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private static final String USER_SERVICE_IDENT = "USER_SERVICE_INSTANCE"; 
	private static final String TOPIC_SERVICE_IDENT = "TOPIC_SERVICE_INSTANCE";
	private static final String COMMENT_SERVICE_IDENT = "COMMENT_SERVICE_INSTANCE";

	private static final String USER_SESSION_IDENT = "USER_ID";
	
	/* An services part for Servlets */

	private final Object getService(HttpServletRequest request, String SERVICE, Class<?> clazz)
	{
		Object savedService = request.getAttribute(SERVICE);
		if(savedService == null)
			throw new GetServiceException("Service instance of "+clazz.getSimpleName()+" is null.");
		
		return savedService;
	}
	
	final UserInterface getUserService(HttpServletRequest request)
	{
		return (UserInterface) getService(request, USER_SERVICE_IDENT, UserInterface.class);
	}
	
	final TopicInterface getTopicService(HttpServletRequest request)
	{
		return (TopicInterface) getService(request, TOPIC_SERVICE_IDENT, TopicInterface.class);
	}
	
	final CommentInterface getCommentService(HttpServletRequest request)
	{
		return (CommentInterface) getService(request, COMMENT_SERVICE_IDENT, CommentInterface.class);
	}
       
	private final boolean setService(HttpServletRequest request, Object serviceInstance, String SERVICE)
	{
		request.setAttribute(SERVICE, serviceInstance);
		return true;
	}
	
	final boolean setService(HttpServletRequest request, Object serviceInstance)
	{
		if(serviceInstance instanceof UserInterface)
			return setService(request, serviceInstance, USER_SERVICE_IDENT);
		else
		if(serviceInstance instanceof TopicInterface)
			return setService(request, serviceInstance, TOPIC_SERVICE_IDENT);
		else
		if(serviceInstance instanceof CommentInterface)
			return setService(request, serviceInstance, COMMENT_SERVICE_IDENT);
		else
			throw new GetServiceException("Unknown service "+serviceInstance.getClass().getSimpleName()+" to save.");	
	}
	

	
	
    final User getSessionUser(HttpServletRequest request)
    {
/*    	User storedInSession = (User) request.getSession().getAttribute(USER_SESSION_ID)
    	if(request.)*/
    	return null;
    }
    
    final boolean setSessionUser(HttpServletRequest request, User user)
    {
    	return false;
    }

    final void logoutUser(HttpServletRequest request)
    {
    	
    }
    
    final UserRole getSessionRole(HttpServletRequest request)
    {
    	return UserRole.GUEST;
    }

/*	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
*/
}
