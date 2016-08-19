package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;
import sk.tsystems.forum.entity.exceptions.field.user.BadDateException;
import sk.tsystems.forum.entity.exceptions.field.user.PasswordCheckException;
import sk.tsystems.forum.entity.exceptions.field.user.UserEntityFieldException;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.helper.URLParser;
import sk.tsystems.forum.helper.UserHelper;
import sk.tsystems.forum.helper.exceptions.URLParserException;
import sk.tsystems.forum.helper.exceptions.UnknownActionException;
import sk.tsystems.forum.servlets.master.MasterServlet;

/**
 * Servlet implementation class UserOptionsNew
 */
@WebServlet("/Useroptions/*")
public class UserOptions extends MasterServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserOptions() {
		super();
		// TODO Auto-generated constructor stub
	}

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
				request.getRequestDispatcher("/WEB-INF/jsp/useroptions.jsp").include(request, response);
				request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);
				return;
			}

			if (servletHelper.getLoggedUser() == null) {
				throw new UnknownActionException("You need to log in first");

			}
			String action = pars.getAction();
			if (action == null) {
				List<Topic> allTopics = servletHelper.getTopicService().getTopics();
				List<Topic> userTopics = new ArrayList<Topic>();
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				Map<String, Object> resp = new HashMap<>();

				SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy");
				Date date = servletHelper.getLoggedUser().getBirthDate();
				resp.put("datestring", dt.format(date));

				// TODO toto je dost shit.. spravit nejaky kulturny filter ked
				// sa zvysi cas
				Iterator<Topic> topicIterator = servletHelper.getLoggedUser().getTopicsIterator();
				while (topicIterator.hasNext()) {
					userTopics.add(topicIterator.next());
				}
				for (Topic userTopic : userTopics) {
					for (Topic topic : allTopics) {
						if (topic.getId() == userTopic.getId()) {
							allTopics.remove(topic);
							break;
						}
					}

				}
				Collections.sort(allTopics);
				Collections.sort(userTopics);
				resp.put("alltopics", allTopics);
				resp.put("usertopics", userTopics);
				resp.put("user", servletHelper.getLoggedUser());
				response.setContentType("application/json");
				mapper.writeValue(response.getWriter(), resp);
				return;
			}
		} catch (URLParserException e) {
			response.getWriter().println(e.getMessage());

		} catch (UnknownActionException e) {
			response.getWriter().println(e.getMessage());
		}
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletHelper servletHelper = new ServletHelper(request);
		URLParser pars;
		try {
			if (servletHelper.getLoggedUser() == null) {
				throw new UnknownActionException("you need to log in first");
			}

			pars = servletHelper.getURLParser();
			String action = pars.getAction();

			JSONObject obj = servletHelper.getJSON();

			if (obj == null) {
				throw new UnknownActionException("did not receive parameters");
			}
			if (action == null) {
				return;
			}

			switch (action) {
			case "changeinfo":
				changeInfo(response, servletHelper, obj);
				break;
			case "changepassword":
				changePassword(response, servletHelper, obj);
				break;
			case "addtopic":
				addTopic(response, servletHelper, obj);
				break;
			case "removetopic":
				removeTopic(response, servletHelper, obj);
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

	private void removeTopic(HttpServletResponse response, ServletHelper servletHelper, JSONObject obj)
			throws IOException, JsonGenerationException, JsonMappingException {
		int rtopicid = obj.getInt("id");
		User ruser = servletHelper.getLoggedUser();
		ruser.removeTopic(servletHelper.getTopicService().getTopic(rtopicid));
		servletHelper.getUserService().updateUser(ruser);
		ObjectMapper rmapper = new ObjectMapper();
		rmapper.setSerializationInclusion(Include.NON_NULL);
		Map<String, Object> rresp = new HashMap<>();
		rresp.put("suc", "suc");
		response.setContentType("application/json");
		rmapper.writeValue(response.getWriter(), rresp);
	}

	private void addTopic(HttpServletResponse response, ServletHelper servletHelper, JSONObject obj)
			throws IOException, JsonGenerationException, JsonMappingException {
		int topicid = obj.getInt("id");
		User user = servletHelper.getLoggedUser();
		user.addTopic(servletHelper.getTopicService().getTopic(topicid));
		servletHelper.getUserService().updateUser(user);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		Map<String, Object> resp = new HashMap<>();
		resp.put("suc", "suc");
		response.setContentType("application/json");
		mapper.writeValue(response.getWriter(), resp);
	}

	private void changePassword(HttpServletResponse response, ServletHelper servletHelper, JSONObject obj)
			throws IOException, JsonGenerationException, JsonMappingException {
		String errMessage = null;
		System.out.println(obj);
		String oldPassword = obj.getString("oldpassword");
		String newPassword = obj.getString("newpassword");
		String confirmPassword = obj.getString("confirmpassword");
		if (servletHelper.getLoggedUser().authentificate(oldPassword)) {

			if (confirmPassword.equals(newPassword)) {
				try {
					UserHelper.passwordOverallControll(newPassword);
					User user = servletHelper.getLoggedUser();
					user.setPassword(newPassword);
					servletHelper.getUserService().updateUser(user);
					errMessage = "success";
				} catch (PasswordCheckException e) {

					errMessage = e.getMessage();
				} catch (FieldValueException e) {
					errMessage = "invalid access";
				}
			} else {
				errMessage = "passwords do not match";
			}
		} else {
			errMessage = "incorrect password";
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		Map<String, Object> resp = new HashMap<>();
		resp.put("errMessage", errMessage);
		response.setContentType("application/json");
		mapper.writeValue(response.getWriter(), resp);
	}

	private void changeInfo(HttpServletResponse response, ServletHelper servletHelper, JSONObject obj)
			throws IOException, JsonGenerationException, JsonMappingException {
		String newRealName = obj.getString("newrealname");
		String newDateString = obj.getString("newdate");
		if (newRealName != null && newDateString != null) {
			Date newDate = null;
			try {
				newDate = UserHelper.stringToDate(newDateString, "dd.MM.yyyy");
			} catch (BadDateException e) {
				e.printStackTrace();
			}
			User loggedUser = servletHelper.getLoggedUser();
			if (newDate != null) {
				try {
					loggedUser.setBirthDate(newDate);
				} catch (FieldValueException | BadDateException e) {
					// TODO DOPLNIT OSETRENIE
				}
			}
			if (newRealName != null) {

				try {
					loggedUser.setRealName(newRealName);
				} catch (FieldValueException e) {
					System.out.println("Invalid access");
				}

			}
			servletHelper.getUserService().updateUser(loggedUser);

		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		Map<String, Object> resp = new HashMap<>();
		resp.put("date", newDateString);
		resp.put("realname", newRealName);

		response.setContentType("application/json");
		mapper.writeValue(response.getWriter(), resp);
	}

}
