package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Patch;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;

import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.service.jpa.UserJPA;
import sk.tsystems.forum.serviceinterface.UserInterface;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends MasterServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see MasterServlet#MasterServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
        request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").include(request, response);
        request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("register", "no parameters expected");
        ServletHelper helper = new ServletHelper(request);
        
        try
        {
        	JSONObject jsonClient;
        	if((jsonClient = helper.getJSON())==null) // no json data received
        		return;
        	
        	if(jsonClient.has("checknick"))
        		checkUserExists(jsonClient.getJSONObject("checknick"), jsonResponse, helper);
        	else
        	if(jsonClient.has("register"))
        		doRegister(jsonClient.getJSONObject("register"), jsonResponse, helper);
        	else
                jsonResponse.put("register", "unrecognized action");
        }
        finally
        {
			response.setContentType("application/json");
			response.getWriter().print(jsonResponse);
        }
	}
	
	private void checkUserExists(JSONObject req, JSONObject resp, ServletHelper helper)
	{
        resp.put("register", "checkUserExists");
        try
        {
        	String userName = req.getString("nick"); // get nickname from user
        	nickNameValidator(userName); // validate nickname
			resp.put("exists", helper.getUserService().getUser(userName)!=null); // check user exists
        }
        catch(JSONException ex)
        {
            resp.put("error", "requested parameters not found"); // an error occured getting username
        } catch (NickNameException e) { 
			resp.put("error", e.getMessage()); // nick name validation exception
		}
	}
	
	private void doRegister(JSONObject req, JSONObject resp, ServletHelper helper)
	{
        resp.put("register", "checkUserExists");
		resp.put("registered", false);
		// TODO do some check on name, eg length and so on... // nick, bith, pass
		try { // TODO erase this
			//resp.put("exists", helper.getUserService().getUser(req.getString("nick"))!=null);
			
			User usr = new User(req.getString("nick"), req.getString("pass"), new Date(), req.getString("nick")+"real"); // TODO !! len tak nabuchane to je
			if(helper.getUserService().addUser(usr))
			{
				resp.put("registered", true);
				helper.setLoggedUser(usr);
			}
		}
		catch(javax.persistence.NoResultException e)
		{
			resp.put("exists", false);
			System.err.println("****************************** Temporary catch on register.java.");
		}
		catch(javax.persistence.NonUniqueResultException e)
		{
			resp.put("exists", true);
			System.err.println("****************************** Temporary catch on register.java.");
		}
	}
	
	private boolean nickNameValidator(String userName) throws NickNameException
	{
    	if(userName.length()<4)
    		throw new NickNameException("Nickname must be 4 characters long");
    	
    	Pattern testValidNickName = Pattern.compile("^([a-z][a-z0-9]{3,})$");
    	if(!testValidNickName.matcher(userName).matches())
    		throw new NickNameException("Nickname can contain a-z and 0-9 characters. First character muste be a-z.");
    	
    	return true;
	}
	
	private class NickNameException extends Exception {
		private static final long serialVersionUID = 1L;
		NickNameException(String message) {
			super(message);
		}
	}

}
