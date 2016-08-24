package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;
import sk.tsystems.forum.helper.BlockHelper;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.helper.URLParser;
import sk.tsystems.forum.helper.exceptions.URLParserException;
import sk.tsystems.forum.helper.exceptions.UnknownActionException;
import sk.tsystems.forum.service.BlockedService;
import sk.tsystems.forum.service.TopicService;
import sk.tsystems.forum.service.jpa.BlockedJPA;
import sk.tsystems.forum.service.jpa2.BlockedJPA2;
import sk.tsystems.forum.servlets.master.MasterServlet;

/**
 * Servlet implementation class AdminNew
 */
@WebServlet("/Admin/*")
public class Admin extends MasterServlet {
	private static final long serialVersionUID = 1L;

	

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
				request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").include(request, response);
				request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);

				return;
			}
			
			if(servletHelper.getSessionRole()!=UserRole.ADMIN){
				response.getWriter().print("<h1>Only admins can view this page</h1>");
				return;
			}
			String action = pars.getAction();
						if (action == null) {
				List<User> users = servletHelper.getUserService().getAllUsers();
				List<Topic> topics = servletHelper.getTopicService().getTopics();
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				Map<String, Object> resp = new HashMap<>();
				Collections.sort(users);
				Collections.sort(topics);
				resp.put("users", users);
				resp.put("topics", topics);
				resp.put("role", servletHelper.getSessionRole());

				response.setContentType("application/json");
				mapper.writeValue(response.getWriter(), resp);
				return;
			}
		} catch (URLParserException e) {
			response.getWriter().println(e.getMessage());
		}
		

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletHelper servletHelper = new ServletHelper(request);
		URLParser pars;
		try {
			if(servletHelper.getSessionRole()!=UserRole.ADMIN){
				return;
			}

			pars = servletHelper.getURLParser();
			if (pars.getParrentID() < 0)
				throw new UnknownActionException("ID not specified.");
			String action = pars.getAction();

			JSONObject obj = servletHelper.getJSON();
			if (obj == null) {
				throw new UnknownActionException("did not receive parameters");
			}
			if (action == null) {
				return;
			}

			switch (action) {
			case "promote":
				promoteUser(response, servletHelper, obj);
				break;
			case "mark":
				mark(response, obj);
				break;
			case "unblock":
				unblock(response, servletHelper,obj);
				break;
			case "addtopic":
				addTopic(response, servletHelper, obj);
				break;
			case "changetopic":
				changeTopic(response, servletHelper, obj);
				break;
			case "getblocked":
				String reason = obj.getString("reason");
				BlockedService bs= new BlockedJPA();
				Blocked blocked =bs.getBlocked(reason);
				
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				Map<String, Object> resp = new HashMap<>();
				resp.put("blocked", blocked);
				response.setContentType("application/json");
				mapper.writeValue(response.getWriter(), resp);
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

	private void changeTopic(HttpServletResponse response, ServletHelper servletHelper, JSONObject obj)
			throws IOException, JsonGenerationException, JsonMappingException {
		Topic topic;
		String error=null;
		TopicService topicService = servletHelper.getTopicService();
		try {
			topic = topicService.getTopic(obj.getInt("id"));
			topic.setName(obj.getString("newname"));
			topicService.updateTopic(topic);
			
		} catch (JSONException e) {
			error = "JSON exception";
		} catch (FieldValueException e) {
			error = e.getMessage();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		Map<String, Object> resp = new HashMap<>();
		
		if(error!=null){
			resp.put("error", error);
		}else {
			resp.put("suc", "suc");
		}

		response.setContentType("application/json");
		mapper.writeValue(response.getWriter(), resp);
	}

	private void addTopic(HttpServletResponse response, ServletHelper servletHelper, JSONObject obj)
			throws IOException, JsonGenerationException, JsonMappingException {
		Topic topic;
		String error = null;
		try {
			String newTopicName = obj.getString("topicname");
			List<Topic> listOfTopic = servletHelper.getTopicService().getTopics();
			for(Topic actualTopic:listOfTopic){
				if(actualTopic.getName().equals(newTopicName)){
					error = "Topic with this name already exists";
					
				}
			}
			if(error==null){
				topic = new Topic(newTopicName,obj.getBoolean("ispublic"));
				servletHelper.getTopicService().addTopic(topic);
			}
			
			
		} catch (FieldValueException e) {
			error = "invalid access";
			
		} catch (JSONException e) {
			error = "Json error";
		}
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		Map<String, Object> resp = new HashMap<>();
		resp.put("topic", "topic");
		if(error!=null){
			resp.put("error", error);
		}

		response.setContentType("application/json");
		mapper.writeValue(response.getWriter(), resp);
	}

	private void unblock(HttpServletResponse response,ServletHelper svHelper, JSONObject obj)
			throws IOException, JsonGenerationException, JsonMappingException {
		BlockHelper.unblock(obj.getInt("id"));
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		Map<String, Object> resp = new HashMap<>();
		resp.put("suc", "suc");
		resp.put("user", svHelper.getLoggedUser());

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

	

}
