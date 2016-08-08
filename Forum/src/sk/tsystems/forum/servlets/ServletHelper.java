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
	 * Gets object from servletRequest by name This method checks getted object
	 * is instance of requested class
	 * 
	 * @param SERVICE
	 *            requested service name from attribute
	 * @param clazz
	 *            Used to check instence is class of
	 * @return an instance of requested service, otherwise throws an exception
	 *         (runtime)
	 */
	private final Object getService(String SERVICE, Class<?> clazz) {
		Object savedService = servletRequest.getAttribute(SERVICE);
		if (savedService == null)
			throw new GetServiceException("Service instance of " + clazz.getSimpleName() + " is null.");

		// TODO check class for claZZ

		return savedService;
	}

	/**
	 * Gets pre-initialized service object from request attributes.
	 * 
	 * @return An instance of UserInterface service, otherwise an runtime
	 *         exception is thrown
	 */
	final UserInterface getUserService() {
		return (UserInterface) getService(USER_SERVICE_IDENT, UserInterface.class);
	}

	/**
	 * Gets pre-initialized service object from request attributes.
	 * 
	 * @return An instance of TopicInterface service, otherwise an runtime
	 *         exception is thrown
	 */
	final TopicInterface getTopicService() {
		return (TopicInterface) getService(TOPIC_SERVICE_IDENT, TopicInterface.class);
	}

	/**
	 * Gets pre-initialized service object from request attributes.
	 * 
	 * @return An instance of CommentInterface service, otherwise an runtime
	 *         exception is thrown
	 */
	final CommentInterface getCommentService() {
		return (CommentInterface) getService(COMMENT_SERVICE_IDENT, CommentInterface.class);
	}

	/**
	 * Saves an object to request (setAtribute)
	 * 
	 * @param serviceInstance
	 *            An instance of service object to store
	 * @param SERVICE
	 *            An name of service object
	 * @return true on sucess (always true)
	 */
	private final boolean setService(Object serviceInstance, String SERVICE) {
		servletRequest.setAttribute(SERVICE, serviceInstance);
		return true;
	}

	/**
	 * Saves service to request.setAttribute(). This method checks service type
	 * (interface) is known. Check prevents set an unidentified objects.
	 * 
	 * @param serviceInstance
	 *            an instance of service to be saved (an shared over servlets)
	 * @return true on success, false on error occured, throws exceptions on
	 *         foreign service is detected
	 */
	final boolean setService(Object serviceInstance) {
		if (serviceInstance instanceof UserInterface)
			return setService(serviceInstance, USER_SERVICE_IDENT);
		else if (serviceInstance instanceof TopicInterface)
			return setService(serviceInstance, TOPIC_SERVICE_IDENT);
		else if (serviceInstance instanceof CommentInterface)
			return setService(serviceInstance, COMMENT_SERVICE_IDENT);
		else
			throw new GetServiceException(
					"Unknown service " + serviceInstance.getClass().getSimpleName() + " to save.");
	}

	/* User (session) part ********************************************** */

	/**
	 * Gets object stored in request (replaces call of request.getAtribute())
	 * 
	 * @param NAME
	 *            an object name in request
	 * @return Object from request if found, otherwise NULL will be returned
	 */
	Object getSessionObject(String NAME){ // TODO maybe private?
	
		return servletRequest.getSession().getAttribute(NAME);
	}

	/**
	 * Stores object in request. (replaces call of request.setAtribute(name,
	 * object))
	 * 
	 * @param NAME
	 *            an object name in request
	 * @param object
	 *            an object to be stored
	 * @return always true
	 */
	boolean setSessionObject(String NAME, Object object) {// TODO maybe private?,
															// do some checks?
	
		servletRequest.getSession().setAttribute(NAME, object);
		return true;
	}

	/**
	 * Use to get an fresh version of actually logged-in user.
	 * 
	 * @return An instance of User entity (logged in user) or null
	 */
	final User getLoggedUser() {
		Integer ident;
		if ((ident = (Integer) getSessionObject(USER_SESSION_IDENT)) == null)
			return null;

		return getUserService().getUser(ident);
	}

	/**
	 * Saves User to session (this means an user is logged-in succesfully)
	 * 
	 * @param user
	 *            to save in session
	 * @return true on sucess
	 */
	final boolean setLoggedUser(User user) {
		setSessionObject(USER_SESSION_IDENT, user.getId());
		return true;
	}

	/**
	 * Logouts current user
	 */
	final void logoutUser() {
		setLoggedUser(null);
	}

	/**
	 * Retieves current session role. An non-logged-in user role is (by defualt)
	 * GUEST Logged-in user returns current user role. For an blocked users (by
	 * admin), this function automatically downgrades user role to GUEST
	 * 
	 * @return An role of User
	 */
	final UserRole getSessionRole() {
		User user = getLoggedUser();
		if (user == null || user.getBlocked() != null)
			return UserRole.GUEST;

		return user.getRole();
	}

}