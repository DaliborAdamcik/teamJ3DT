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

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
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
@WebServlet("/Admin/*")
public class Admin extends MasterServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Admin() {
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
			// if (servletHelper.getLoggedUser() == null ||
			// servletHelper.getLoggedUser().getRole() != UserRole.ADMIN) {
			if (pars.getParrentID() < 0) {
				request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
				response.getWriter().print("<h1>Only admins can view this page</h1>");
				request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").include(request, response);
				request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);

				return;
			}
			String action = pars.getAction();
			// Integer objId = pars.getParrentID();
			// //ak je zadane nespravne ID
			// if(objId == 0 ) {
			// throw new UnknownActionException("Invalid id selected.");
			// }
			// // check privileges
			// if(!BlockHelper.isInDatabase(objId)){
			// throw new UnknownActionException("Object with selected id is not
			// in database.");
			// }

			if (action == null) {
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
			// if(action.equals("block")){
			// BlockHelper.block(objId, blockReason,
			// servletHelper.getLoggedUser());
			// ObjectMapper mapper = new ObjectMapper();
			// mapper.setSerializationInclusion(Include.NON_NULL);
			//
			// }
		} catch (URLParserException e) {
			response.getWriter().println(e.getMessage());
		}
		// catch (UnknownActionException e) {
		// response.getWriter().println(e.getMessage());
		// }

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletHelper svHelper = new ServletHelper(request);
		URLParser pars;
		try {
			// check privileges
			// if(svHelper.getSessionRole().equals(UserRole.GUEST))
			// throw new UnknownActionException("You must be signed in /
			// confirmed user to add comment.");

			pars = svHelper.getURLParser();
			if (pars.getParrentID() < 0)
				throw new UnknownActionException("ID not specified.");
			String action = pars.getAction();

			JSONObject obj = svHelper.getJSON();
			if (obj == null) {
				throw new UnknownActionException("did not receive parameters");
			}
			if (action == null) {
				return;
			}

			switch (action) {
			case "promote":
				promoteUser(response, svHelper, obj);
				break;
			case "mark":
				mark(response, obj);
				break;
			case "unblock":
				unblock(response, obj);
				break;
			default:
				return;

			}

		} catch (URLParserException e) {
			response.getWriter().println(e.getMessage());
		} catch (UnknownActionException e) {
			response.getWriter().println(e.getMessage());
		}
	}

	private void unblock(HttpServletResponse response, JSONObject obj)
			throws IOException, JsonGenerationException, JsonMappingException {
		BlockHelper.unblock(obj.getInt("id"));
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		Map<String, Object> resp = new HashMap<>();
		resp.put("suc", "suc");

		response.setContentType("application/json");
		mapper.writeValue(response.getWriter(), resp);
	}

	private void mark(HttpServletResponse response, JSONObject obj)
			throws IOException, JsonGenerationException, JsonMappingException {
		BlockHelper.mark(obj.getInt("id"));
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		Map<String, Object> resp = new HashMap<>();
		resp.put("suc", "suc");

		response.setContentType("application/json");
		mapper.writeValue(response.getWriter(), resp);
	}

	private void promoteUser(HttpServletResponse response, ServletHelper svHelper, JSONObject obj)
			throws UnknownActionException, IOException, JsonGenerationException, JsonMappingException {
		if (svHelper.getUserService().getUser(obj.getInt("id")) == null) {
			throw new UnknownActionException("user with id " + obj.getInt("id") + " does not exist");
		}

		if (svHelper.getUserService().getUser(obj.getInt("id")).getRole() != UserRole.GUEST) {
			throw new UnknownActionException("user has already been promoted");
		}
		User user = svHelper.getUserService().getUser(obj.getInt("id"));
		user.setRole(UserRole.REGULARUSER);
		svHelper.getUserService().updateUser(user);

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		Map<String, Object> resp = new HashMap<>();
		resp.put("user", user);

		response.setContentType("application/json");
		mapper.writeValue(response.getWriter(), resp);
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
