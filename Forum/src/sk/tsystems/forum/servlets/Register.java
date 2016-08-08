package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.sun.org.apache.xerces.internal.util.DOMEntityResolverWrapper;

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
        jsonResponse.put("register", "something is bad");
        ServletHelper helper = new ServletHelper(request);
        
        try
        {
            // TODO tot dat do helper class
        	StringBuilder sb = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) 
                sb.append(s);

            System.out.println(sb);// TODO temp code, remove

        	JSONObject jsonClient = new JSONObject(sb.toString());
        	// * po tade
        	
        	if(jsonClient.has("checknick"))
        	{
        		checkUserExists(jsonClient.getJSONObject("checknick"), jsonResponse, helper);
        	}
        	if(jsonClient.has("register"))
        	{
        		doRegister(jsonClient.getJSONObject("register"), jsonResponse, helper);
        	}
            
            
        	
        	
        }
        finally
        {
			response.setContentType("application/json");
			response.getWriter().print(jsonResponse);
        }
	}
	
	private void checkUserExists(JSONObject req, JSONObject resp, ServletHelper helper)
	{
		// TODO do some check on name, eg length and so on...
		try { // TODO erase this
			resp.put("exists", helper.getUserService().getUser(req.getString("nick"))!=null);
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
	
	private void doRegister(JSONObject req, JSONObject resp, ServletHelper helper)
	{
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

}
