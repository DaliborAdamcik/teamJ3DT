package sk.tsystems.forum.helper;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.helper.exceptions.GetServiceException;
import sk.tsystems.forum.helper.exceptions.URLParserException;
import sk.tsystems.forum.helper.exceptions.UnknownActionException;
import sk.tsystems.forum.service.CommentService;
import sk.tsystems.forum.service.ThemeService;
import sk.tsystems.forum.service.TopicService;
import sk.tsystems.forum.service.UserService;

/**
 * 
 * @author Dalibor
 */
public class ServletHelper {
	/** Prefix string for service attribute */
	private static final String SERVICE_ATRIBUTE_PREFIX = "SERVICE_INSTANCE_";

	/** CommentService identifier (for session.getAttribute) */
	private static final String USER_SESSION_IDENT = "USER_ID";

	/** An reference to HttpServletRequest of caller script */
	private HttpServletRequest servletRequest;
	
	/** An cached user to reduce request to hibernate */
	private User currentUser = null; 

	/** Initiates new instance for servlet helper */
	public ServletHelper(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	/* An services part for Servlets */

	/** 
	 * Gets Service from servletRequest by required interface class
	 * 
	 * @param clazz
	 *            Interface class of requested service
	 * @return an instance of requested service, otherwise throws an exception
	 *         (runtime)
	 */
	@SuppressWarnings("unchecked")
	private final <T> T getService(Class<T> clazz) {
		Object savedService = servletRequest.getAttribute(SERVICE_ATRIBUTE_PREFIX+clazz.getSimpleName());
		if (savedService == null)
			throw new GetServiceException("Service instance of '" + clazz.getSimpleName() + "' is not set.");

		if(!clazz.isInstance(savedService)) 
			throw new GetServiceException("Bad service instance of '" + clazz.getSimpleName() + "'.");

		return (T) savedService;
	}

	/**
	 * Gets pre-initialized service object from request attributes.
	 * 
	 * @return An instance of User service, otherwise an runtime
	 *         exception is thrown
	 */
	public final UserService getUserService() {
		return  getService(UserService.class);
	}

	/**
	 * Gets pre-initialized service object from request attributes.
	 * 
	 * @return An instance of Topic service, otherwise an runtime
	 *         exception is thrown
	 */
	public final TopicService getTopicService() {
		return getService(TopicService.class);
	}

	/**
	 * Gets pre-initialized service object from request attributes.
	 * 
	 * @return An instance of Comment service, otherwise an runtime
	 *         exception is thrown
	 */
	public final ThemeService getThemeService() {
		return getService(ThemeService.class);
	}

	/**
	 * Gets pre-initialized service object from request attributes.
	 * 
	 * @return An instance of Theme service, otherwise an runtime
	 *         exception is thrown
	 */
	public final CommentService getCommentService() {
		return getService(CommentService.class);
	}

	/**
	 * Saves an object to request (setAtribute)
	 * 
	 * @param serviceInstance
	 *            An instance of service object to store
	 * @return true on sucess, otherwise RuntimeException is thrown
	 */
	public final boolean setService(Object serviceInstance) {
		Class<?>[] interfaces = serviceInstance.getClass().getInterfaces();
		if(interfaces.length==0)
			throw new GetServiceException("Service '"+serviceInstance.getClass().getSimpleName()
					+"' must implement service interface.");
		
		Class<?> useInterface = null;
		
		for (Class<?> intf : interfaces) {
			if(intf.getPackage().equals(UserService.class.getPackage()))
			{
				useInterface = intf;
				break;
			}
		}
		
		if(useInterface == null)
			throw new GetServiceException("Cant store not known service '"+
					serviceInstance.getClass().getSimpleName()+"' ");
		
		servletRequest.setAttribute(SERVICE_ATRIBUTE_PREFIX+useInterface.getSimpleName(), serviceInstance);
		return true;
	}

	/* User (session) part ********************************************** */

	/**
	 * Gets object stored in request (replaces call of request.getAtribute())
	 * 
	 * @param NAME
	 *            an object name in request
	 * @return Object from request if found, otherwise NULL will be returned
	 */
	public Object getSessionObject(String NAME) { // TODO maybe private?
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
	public boolean setSessionObject(String NAME, Object object) {// TODO maybe
															// private?,
															// do some checks?

		servletRequest.getSession().setAttribute(NAME, object);
		return true;
	}

	/**
	 * Use to get an fresh version of actually logged-in user.
	 * @param refresh {@link Boolean} true = get refreshed account from hibernate / false = get cached object
	 * @return An instance of User entity (logged in user) or null
	 */
	public final User getLoggedUser(boolean refresh) {
		Integer ident;
		if ((ident = (Integer) getSessionObject(USER_SESSION_IDENT)) == null)
			return null;
		
		if(!(currentUser instanceof User) || refresh || currentUser.getId()!=ident)
			currentUser = getUserService().getUser(ident);

		return currentUser;
	}

	/**
	 * Use to get an fresh version of actually logged-in user.
	 * @param refresh {@link Boolean} get refreshed account from hibernate
	 * @return An instance of User entity (logged in user) or null
	 */
	public final User getLoggedUser() {
		return getLoggedUser(false);
	}
	
	/**
	 * Saves User to session (this means an user is logged-in succesfully)
	 * 
	 * @param user
	 *            to save in session
	 * @return true on sucess
	 */
	public final boolean setLoggedUser(User user) {
		if (user != null) {
			setSessionObject(USER_SESSION_IDENT, user.getId());
			return true;
		} else {
			setSessionObject(USER_SESSION_IDENT, null);
			return false;
		}
	}

	/**
	 * Logouts current user
	 */
	public final void logoutUser() {
		setLoggedUser(null);
	}

	/**
	 * Retieves current session role. An non-logged-in user role is (by defualt)
	 * GUEST Logged-in user returns current user role. For an blocked users (by
	 * admin), this function automatically downgrades user role to GUEST
	 * 
	 * @return An role of User
	 */
	public final UserRole getSessionRole() {
		User user = getLoggedUser();
		if (user == null || user.isBlocked())
			return UserRole.GUEST;

		return user.getRole();
	}
	
	/**
	 * Parses JSON object from request
	 * @return JSON object from request or null if not present
	 * @author Dalibor
	 * @throws UnknownActionException if json is not available in request
	 */
	public final JSONObject getJSON() throws UnknownActionException
	{
    	StringBuilder sb = new StringBuilder();
        String s;
        try
        {
            while ((s = servletRequest.getReader().readLine()) != null) 
                sb.append(s);
            
        	return new JSONObject(sb.toString());
        }
        catch(IOException ex)
        {
			throw new UnknownActionException("Cand read JSON from request: IO error "+ex.getMessage(), ex);
        }
        catch(JSONException ex) // an error in json or not available
        {
			throw new UnknownActionException("We request an JSON object into an input.", ex);
        }
	}
	
	/**
	 * Parses request URL of actual request
	 * @param getParam true = include GET paraemeter in URL, false = pure URL 
	 * @return request URL
	 * @author Dalibor
	 */
	public final String requestURL(boolean getParam)
	{
		StringBuffer requestURL = servletRequest.getRequestURL();
		if (getParam && servletRequest.getQueryString() != null) {
		    requestURL.append("?").append(servletRequest.getQueryString());
		}
		return requestURL.toString();	
	}

	/**
	 * Parses request URL of actual request
	 * @return request URL
	 * @author Dalibor
	 */
	public final String requestURL()
	{
		return requestURL(false);
	}

	/**
	 * Gets URL parser object for current request URL
	 * @return {@link URLParser} instance for current request URL
	 * @throws URLParserException
	 */
	public final URLParser getURLParser() throws URLParserException
	{
		return new URLParser(requestURL());
	}

	/**
	 * Converts exception into JSON and writes into response. 
	 * @param exception An exception 
	 * @param response An servlet response to be written to
	 * @param append true - append into output, false - try to arase output ease output
	 * @throws IOException Thrown on unsucces write to reponse
	 * @author Dalibor Adamcik
	 */
	public static void ExceptionToResponseJson(Throwable exception, HttpServletResponse response, boolean append) throws IOException
	{
		HashMap<String, String> excinfo = new HashMap<>();
		excinfo.put("message", exception.getMessage());
		excinfo.put("type", exception.getClass().getSimpleName());
		JSONObject excson = new JSONObject();
		excson.put("error", excinfo);
		if (!append) {
			try{	
				response.resetBuffer();
			} catch(IllegalStateException e) {}
		}
		response.setContentType("application/json");
		response.getWriter().print(excson);
	}

	/**
	 * An JSON filter interface 
	 *  
	 * @author Dalibor Adamcik
	 */
	public interface JsonOutFilter {
		/**
		 * An target class to be filtered
		 * @return {@link Class} target class
		 */
		public Class<?> getTarget();
		/**
		 * An filter definition (MixIn)
		 * @return {@link Class} filter class
		 */
		public Class<?> getFilter();
	}

	/**
	 * Converts object to JSON format and writes to response
	 * @param response {@link HttpServletResponse} target response to write JSON object
	 * @param toJson {@link Object} an object to be written to JSON
	 * @param filters {@link JsonOutFilter} filters to be applied to output
	 * @param flush {@link Boolean} true = sets content type and closes response.
	 * false = appends to response
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public static void jsonResponse(HttpServletResponse response, Object toJson, JsonOutFilter[] filters, boolean flush) 
			throws JsonGenerationException, JsonMappingException, IOException {
		
		if(flush)
			response.setContentType("application/json");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		
		if(filters!=null) {
			for (JsonOutFilter filter : filters) 
				mapper.addMixIn(filter.getTarget(), filter.getFilter());
		}
		
		// TODO *** nesting for parrent child?
		
		if(!flush) {
			mapper.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
			mapper.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false);
		}
		mapper.writeValue(response.getWriter(), toJson);
	}

	/**
	 * Converts object to JSON format, sets content type write and close response.
	 * @param response {@link HttpServletResponse} target response to write JSON object
	 * @param toJson {@link Object} an object to be written to JSON
	 * @param filters {@link JsonOutFilter} filters to be applied to output
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public static void jsonResponse(HttpServletResponse response, Object toJson, JsonOutFilter[] filters) 
			throws JsonGenerationException, JsonMappingException, IOException {
		jsonResponse(response, toJson, filters, true);
	}
	
	/**
	 * Converts object to JSON format, sets content type write and close response.
	 * @param response {@link HttpServletResponse} target response to write JSON object
	 * @param toJson {@link Object} an object to be written to JSON
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public static void jsonResponse(HttpServletResponse response, Object toJson) 
			throws JsonGenerationException, JsonMappingException, IOException {
		jsonResponse(response, toJson, null, true);
	}
	
}
