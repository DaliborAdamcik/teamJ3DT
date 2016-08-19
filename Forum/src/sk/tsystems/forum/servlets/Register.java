package sk.tsystems.forum.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.exceptions.field.FieldException;
import sk.tsystems.forum.entity.exceptions.field.user.UserEntityFieldException;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.helper.UserHelper;
import sk.tsystems.forum.helper.exceptions.NickNameException;
import sk.tsystems.forum.servlets.master.MasterServlet;

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
        	UserHelper.nickNameValidator(userName); // validate nickname
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
        resp.put("register", "doRegister");
		resp.put("registered", false);
		try { 
        	String userName = req.getString("nick"); // get nickname from user
        	resp.put("exists", helper.getUserService().getUser(userName)!=null); // check user exists
			
			User usr = new User(req.getString("nick"), req.getString("pass"), req.getString("birth"), req.getString("realname")); // TODO !! len tak nabuchane to je
			if(helper.getUserService().addUser(usr))
			{
				helper.setLoggedUser(usr);
				helper.setSessionObject("loggeduser", usr);
				resp.put("registered", true);
			}
		}
        catch(JSONException | NullPointerException ex)
        {
            resp.put("error", "requested parameters not found"); // an error occured getting username
        }		
		catch (UserEntityFieldException | FieldException  e) {
			resp.put("error", e.getMessage()); 
		} 
	}
}
