package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.helper.BlockHelper;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.helper.URLParser;
import sk.tsystems.forum.helper.exceptions.URLParserException;
import sk.tsystems.forum.helper.exceptions.UnknownActionException;
import sk.tsystems.forum.service.TopicService;
import sk.tsystems.forum.servlets.master.MasterServlet;

/**
 * Servlet implementation class AdminNew
 */
@WebServlet("/AdminNew/*")
public class AdminNew extends MasterServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminNew() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletHelper servletHelper = new ServletHelper(request);
		URLParser pars;

		try {
			pars = servletHelper.getURLParser();
			//if (servletHelper.getLoggedUser() == null || servletHelper.getLoggedUser().getRole() != UserRole.ADMIN) {
			if(pars.getParrentID()<0){
				request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
				response.getWriter().print("<h1>Only admins can view this page</h1>");
				request.getRequestDispatcher("/WEB-INF/jsp/NewAdmin.jsp").include(request, response);
				request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);
				
				return;
			}
			String action = pars.getAction();
//			Integer objId = pars.getParrentID();
//			//ak je zadane nespravne ID
//			if(objId == 0 )  {
//				throw new UnknownActionException("Invalid id selected.");
//			}
//			// check privileges
//			if(!BlockHelper.isInDatabase(objId)){
//				throw new UnknownActionException("Object with selected id is not in database.");
//			}
			
			if(action==null){
				List<User> users = servletHelper.getUserService().getAllUsers();
				List<Topic> topics = servletHelper.getTopicService().getTopics();
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				Map<String, Object> resp = new HashMap<>();
				resp.put("users", users);
				resp.put("topics", topics);
				resp.put("role", servletHelper.getSessionRole());
				
				
				response.setContentType("application/json");
				mapper.writeValue(response.getWriter(), resp);
				return;
			}
//			if(action.equals("block")){
//				BlockHelper.block(objId, blockReason, servletHelper.getLoggedUser());
//				ObjectMapper mapper = new ObjectMapper();
//				mapper.setSerializationInclusion(Include.NON_NULL);
//				
//			}
		} catch (URLParserException e) {
			response.getWriter().println(e.getMessage());
		}
//		catch (UnknownActionException e) {
//		 response.getWriter().println(e.getMessage());
//		 }
		request.getRequestDispatcher("/WEB-INF/jsp/NewAdmin.jsp").include(request, response);
		request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);
	}
	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
