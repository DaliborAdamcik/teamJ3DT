package sk.tsystems.forum.servlets;

import javax.servlet.http.HttpServletRequest;

import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.serviceinterface.CommentInterface;
import sk.tsystems.forum.serviceinterface.TopicInterface;
import sk.tsystems.forum.serviceinterface.UserInterface;
import sk.tsystems.forum.servlets.master.GetServiceException;


/**
 *  
 * @author Dalibor
 */
public class ServletHelper {
	/**
	 * UserService identifier (for getAttribute)
	 */
	private static final String USER_SERVICE_IDENT = "USER_SERVICE_INSTANCE"; 

	/**
	 * TopicService identifier (for getAttribute)
	 */
	private static final String TOPIC_SERVICE_IDENT = "TOPIC_SERVICE_INSTANCE";

	/**
	 * CommentService identifier (for getAttribute)
	 */
	private static final String COMMENT_SERVICE_IDENT = "COMMENT_SERVICE_INSTANCE";

	/**
	 * CommentService identifier (for session.getAttribute)
	 */
	private static final String USER_SESSION_IDENT = "USER_ID";

	/**
	 * An reference to HttpServletRequest of caller script
	 */
	private HttpServletRequest servletRequest;

	/**
	 * Initiates new instance for servlet helper 
	 */
	public ServletHelper(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
	
	/* An services part for Servlets */

	/**
	 * Gets object from servletRequest by name
	 * This method checks getted object is instance of requested class 
	 * @param SERVICE requested service name from attribute
	 * @param clazz Used to check instence is class of
	 * @return
	 */
	private final Object getService(String SERVICE, Class<?> clazz)
	{
		Object savedService = servletRequest.getAttribute(SERVICE);
		if(savedService == null)
			throw new GetServiceException("Service instance of "+clazz.getSimpleName()+" is null.");
		
		//TODO check class for claZZ
		
		return savedService;
	}
	
	final UserInterface getUserService()
	{
		return (UserInterface) getService(USER_SERVICE_IDENT, UserInterface.class);
	}
	
	final TopicInterface getTopicService()
	{
		return (TopicInterface) getService(TOPIC_SERVICE_IDENT, TopicInterface.class);
	}
	
	final CommentInterface getCommentService()
	{
		return (CommentInterface) getService(COMMENT_SERVICE_IDENT, CommentInterface.class);
	}
       
	private final boolean setService(Object serviceInstance, String SERVICE)
	{
		servletRequest.setAttribute(SERVICE, serviceInstance);
		return true;
	}
	
	final boolean setService(Object serviceInstance)
	{
		if(serviceInstance instanceof UserInterface)
			return setService(serviceInstance, USER_SERVICE_IDENT);
		else
		if(serviceInstance instanceof TopicInterface)
			return setService(serviceInstance, TOPIC_SERVICE_IDENT);
		else
		if(serviceInstance instanceof CommentInterface)
			return setService(serviceInstance, COMMENT_SERVICE_IDENT);
		else
			throw new GetServiceException("Unknown service "+serviceInstance.getClass().getSimpleName()+" to save.");	
	}
	

	/* User (session) part  ********************************************** */
	Object getSessionObject(String NAME)
	{
		return servletRequest.getSession().getAttribute(NAME);
	}

	boolean setSessionObject(String NAME, Object object)
	{
		servletRequest.getSession().setAttribute(NAME, object);
		return true;
	}
	
    final User getLoggedUser()
    {
    	Integer ident;
    	if((ident = (Integer) getSessionObject(USER_SESSION_IDENT))==null)
    		return null;
    	
    	return getUserService().getUser(ident);
    }
    
    /**
     * Saves User to session
     * @param user to save in session
     * @return true on sucess
     */
    final boolean setLoggedUser(User user)
    {
    	setSessionObject(USER_SESSION_IDENT, user.getId());
    	return true;
    }

    final void logoutUser() // TODO unimplemented method
    {
    	setLoggedUser(null);
    }
    
    final UserRole getSessionRole() // TODO unimplemented method
    {
    	User user = getLoggedUser();
    	if(user==null)
    		return UserRole.GUEST;
    	else
    		return user.getRole();
    }

/*	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
*/
	

}
