package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.helper.URLParser;
import sk.tsystems.forum.helper.exceptions.URLParserException;
import sk.tsystems.forum.helper.exceptions.UnknownActionException;
import sk.tsystems.forum.helper.exceptions.WEBNoPermissionException;
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
			 // get Theme for parrent - ... /Comment/10/ ...
			Theme theme = pars.getParentObject(Theme.class);
			
			// theme not exixsts or is blocked (can see only admin)
			if(theme == null || theme.isBlocked() && !svHelper.getSessionRole().equals(UserRole.ADMIN))  
				throw new WEBNoPermissionException("Theme not found or deleted.");
			
			// check privileges for theme
			if(!theme.isIsPublic() && svHelper.getSessionRole().equals(UserRole.GUEST))
				throw new WEBNoPermissionException("Theme is private.");
			
			if(pars.getAction()==null || "newonly".equals(pars.getAction())) // we need to return list of comments
			{
				Map<String, Object> resp = new HashMap<>();
				Date filterDate;
				List<Comment> comments;
				if(pars.getAction()==null || (filterDate = (Date) svHelper.getSessionObject("comment_filter_date"))==null)
				{
					comments = svHelper.getCommentService().getComments(theme);
				}
				else
					comments = svHelper.getCommentService().getComments(theme, filterDate);

				if(!comments.isEmpty()) // save filter or last item
					svHelper.setSessionObject("comment_filter_date", comments.get(comments.size()-1).getModified());

				// list of blocked (erased) items for GUEST, USER
				List<Integer> erased = new ArrayList<>();  
				
				// create list of blocked, remove blocked (erased) 
				if(!svHelper.getSessionRole().equals(UserRole.ADMIN))
				{
					for (Iterator<Comment> iterator = comments.iterator(); iterator.hasNext();) {
						Comment comment = iterator.next();
						if(comment.isBlocked()) {
							erased.add(comment.getId());
							iterator.remove();
						}
					}
				}
				
				resp.put("comments", comments);
				resp.put("theme", theme);
				if(pars.getAction()!=null && !erased.isEmpty()) // request updated items, send blocked list
					resp.put("deleted", erased);

				ServletHelper.jsonResponse(response, resp);
/*				
				response.setContentType("application/json");
				ObjectMapper mapper = new ObjectMapper();
				//mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
				mapper.setSerializationInclusion(Include.NON_NULL);
				mapper.writeValue(response.getWriter(), resp);*/
				return;
			}

			if(pars.getAction().compareTo("single")==0) // get single comment
			{
				throw new UnknownActionException("Sorry. At this time we cant get single comment. Not yet implemented.");
			}
			else 
				throw new UnknownActionException("Anknown action: "+pars.getAction());
			
		} catch (URLParserException | UnknownActionException | WEBNoPermissionException e) {
			ServletHelper.ExceptionToResponseJson(e, response, false);
		} 
	}
	
	// edit
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletHelper svHelper = new ServletHelper(request);
		URLParser pars;
		try {
			if(svHelper.getSessionRole().equals(UserRole.GUEST))
				throw new WEBNoPermissionException("Only regular user can edit comment");
			
			pars = svHelper.getURLParser();
			Comment comment = pars.getParentObject(Comment.class);
			
			if(comment == null || comment.getBlocked()!=null && !svHelper.getSessionRole().equals(UserRole.ADMIN))  
				throw new UnknownActionException("Comment not found.");
			
			if(!svHelper.getSessionRole().equals(UserRole.ADMIN) && svHelper.getLoggedUser().getId()!=comment.getOwner().getId())  
				throw new WEBNoPermissionException("No permission to edit comment.");

			JSONObject obj = svHelper.getJSON();
			comment.setComment(obj.getString("comment"));
			svHelper.getCommentService().updateComment(comment);
			
			
			Map<String, Object> resp = new HashMap<>();
			resp.put("comment", comment);
			ServletHelper.jsonResponse(response, resp);
		} catch (URLParserException | UnknownActionException | WEBNoPermissionException | FieldValueException e) {
			ServletHelper.ExceptionToResponseJson(e, response, false);
		}  catch (JSONException e) {
			ServletHelper.ExceptionToResponseJson(e, response, false);
			e.printStackTrace();
		} 
	}

	// add
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper svHelper = new ServletHelper(request);
		URLParser pars;
		try {
			// check privileges
			if(svHelper.getSessionRole().equals(UserRole.GUEST))
				throw new WEBNoPermissionException("You must be signed in / regular user to add comment.");

			pars = svHelper.getURLParser();
			if(pars.getParrentID()<0) 
				throw new UnknownActionException("Theme ID not specified.");

			 // get Theme for parrent - ... /Comment/10/ ...
			Theme theme = pars.getParentObject(Theme.class);
			
			// theme not exixsts or is blocked anyone cant add new comment
			if(theme == null || theme.getBlocked()!=null)  
				throw new WEBNoPermissionException("Theme not found.");
			
			JSONObject obj = svHelper.getJSON();
			
			Comment comment = new Comment(obj.getString("comment"), theme, svHelper.getLoggedUser());
			svHelper.getCommentService().addComment(comment);

			
			Map<String, Object> resp = new HashMap<>();
			resp.put("comment", comment);
			ServletHelper.jsonResponse(response, resp);
		} catch (URLParserException | UnknownActionException | WEBNoPermissionException | FieldValueException e) {
			ServletHelper.ExceptionToResponseJson(e, response, false);
		} 
	}
	
}
