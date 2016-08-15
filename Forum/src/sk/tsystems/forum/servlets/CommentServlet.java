package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.helper.URLParser;
import sk.tsystems.forum.helper.exceptions.URLParserException;
import sk.tsystems.forum.helper.exceptions.UnknownActionException;
import sk.tsystems.forum.servlets.master.MasterServlet;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Comment/*")
public class CommentServlet extends MasterServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see MasterServlet#MasterServlet()
	 */
	public CommentServlet() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ServletHelper svHelper = new ServletHelper(request);
		URLParser pars;
		try {
			pars = svHelper.getURLParser();
			if(pars.getParrentID()<0) // we not specified parent, we need to return page template
			{
				request.setAttribute("themeid", request.getParameter("topicid"));
				request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
				request.getRequestDispatcher("/WEB-INF/jsp/commentnew.jsp").include(request, response);
				request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);
				return;
			}

			 // get Theme for parrent - ... /Comment/10/ ...
			Theme theme = pars.getParentObject(Theme.class);
			
			// theme not exixsts or is blocked (can see only admin)
			if(theme == null || theme.getBlocked()!=null && !svHelper.getSessionRole().equals(UserRole.ADMIN))  
				throw new UnknownActionException("Theme not found.");
			
			// check privileges
			if(!theme.isIsPublic() && svHelper.getSessionRole().equals(UserRole.GUEST))
				throw new UnknownActionException("Theme is private.");
			
			if(pars.getAction()==null) // we need to return list of coments
			{
				List<Comment> comments = svHelper.getCommentService().getComments(theme);
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				//mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
				// TODO filter comments by blocked
				Map<String, Object> resp = new HashMap<>();
				resp.put("comments", comments);
				resp.put("theme", theme);
				resp.put("role", svHelper.getSessionRole());
				
				
				response.setContentType("application/json");
				mapper.writeValue(response.getWriter(), resp);
				return;
			}

			if(pars.getAction().compareTo("single")==0) // get single comment
			{
				throw new UnknownActionException("not yet implemented");
				
			}
			else 
				throw new UnknownActionException("Anknown action: "+pars.getAction());
			
		} catch (URLParserException e) {
			response.getWriter().println(e.getMessage());
		} catch (UnknownActionException e) {
			response.getWriter().println(e.getMessage());
		}
	}
	
	// edit
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletHelper svHelper = new ServletHelper(request);
		URLParser pars;
		try {
			if(svHelper.getSessionRole().equals(UserRole.GUEST))
				throw new UnknownActionException("Guest cant edit comment");
			
			pars = svHelper.getURLParser();
			Comment comment = pars.getParentObject(Comment.class);
			
			if(comment == null || comment.getBlocked()!=null && !svHelper.getSessionRole().equals(UserRole.ADMIN))  
				throw new UnknownActionException("Comment not found.");
			
			if(!svHelper.getSessionRole().equals(UserRole.ADMIN) && svHelper.getLoggedUser().getId()!=comment.getOwner().getId())  
				throw new UnknownActionException("Comment not found.");

			JSONObject obj = svHelper.getJSON();
			comment.setComment(obj.getString("comment"));
			svHelper.getCommentService().updateComment(comment);
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			Map<String, Object> resp = new HashMap<>();
			resp.put("comment", comment);
			
			response.setContentType("application/json");
			mapper.writeValue(response.getWriter(), resp);
		} catch (URLParserException e) {
			response.getWriter().println(e.getMessage());
		} catch (UnknownActionException e) {
			response.getWriter().println(e.getMessage());
		}
	}

	// remove
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	// add
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper svHelper = new ServletHelper(request);
		URLParser pars;
		try {
			// check privileges
			if(svHelper.getSessionRole().equals(UserRole.GUEST))
				throw new UnknownActionException("You must be signed in / confirmed user to add comment.");

			pars = svHelper.getURLParser();
			if(pars.getParrentID()<0) 
				throw new UnknownActionException("Theme ID not specified.");

			 // get Theme for parrent - ... /Comment/10/ ...
			Theme theme = pars.getParentObject(Theme.class);
			
			// theme not exixsts or is blocked (can see only admin)
			if(theme == null || theme.getBlocked()!=null)  
				throw new UnknownActionException("Theme not found.");
			
			
			JSONObject obj = svHelper.getJSON();
			
			Comment comment = new Comment(obj.getString("comment"), theme, svHelper.getLoggedUser(), true);
			svHelper.getCommentService().addComment(comment);
			
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			Map<String, Object> resp = new HashMap<>();
			resp.put("comment", comment);
			
			response.setContentType("application/json");
			mapper.writeValue(response.getWriter(), resp);
		} catch (URLParserException e) {
			response.getWriter().println(e.getMessage());
		} catch (UnknownActionException e) {
			response.getWriter().println(e.getMessage());
		}
	}
	
}
