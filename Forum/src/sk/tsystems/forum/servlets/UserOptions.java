package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.helper.UserHelper;
import sk.tsystems.forum.helper.exceptions.BadDateException;
import sk.tsystems.forum.helper.exceptions.PasswordCheckException;
import sk.tsystems.forum.servlets.master.MasterServlet;

/**
 * Servlet implementation class UserOptions
 */
@WebServlet("/Useroptions")
public class UserOptions extends MasterServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletHelper servletHelper = new ServletHelper(request);
		changePersonalInfo(request, servletHelper);
		changePassword(request,servletHelper);

		// formatting the date for output in jsp
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = servletHelper.getLoggedUser().getBirthDate();
		request.setAttribute("formatteddate", dt.format(date));

		// list of all topics

		List<Topic> listOfAllTopics = servletHelper.getTopicService().getTopics();

		List<Topic> listOfUserTopics = new ArrayList<Topic>();
		Iterator<Topic> topicIterator = servletHelper.getLoggedUser().getTopicsIterator();
		while (topicIterator.hasNext()) {
			listOfUserTopics.add(topicIterator.next());
		}

		// TODO toto je bieda.. dorobit equals a compareto do entit a potom
		// spravit
		for (Topic userTopic : listOfUserTopics) {
			for (Topic topic : listOfAllTopics) {
				if (topic.getId() == userTopic.getId()) {
					listOfAllTopics.remove(topic);
					break;
				}
			}

		}

		request.setAttribute("listofalltopics", listOfAllTopics);
		request.setAttribute("listofusertopics", listOfUserTopics);

		request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
		request.getRequestDispatcher("/WEB-INF/jsp/useroptions.jsp").include(request, response);
		request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);

	}

	private boolean changePersonalInfo(HttpServletRequest request, ServletHelper servletHelper) {
		String newRealName = request.getParameter("new_username");
		String newDateString = request.getParameter("new_date");
		if (newRealName != null && newDateString != null) {
			Date newDate = null;
			try {
				newDate = UserHelper.stringToDate(newDateString, "yyyy-mm-dd");
			} catch (BadDateException e) {
				e.printStackTrace();
			}
			User loggedUser = servletHelper.getLoggedUser();
			if (newDate != null) {
				loggedUser.setBirthDate(newDate);
			}
			if (newRealName != null) {
				loggedUser.setRealName(newRealName);
			}
			servletHelper.getUserService().updateUser(loggedUser);
			
		}
		return true;

	}

	private boolean changePassword(HttpServletRequest request,ServletHelper servletHelper){
		String newPassword= request.getParameter("new_password");
		String newConfirmPassword = request.getParameter("new_confirmpassword");
		if (newPassword!=null && newConfirmPassword!=null){
			if(newPassword.equals(newConfirmPassword)){
				User loggedUser =servletHelper.getLoggedUser();
					try {
						loggedUser.setPassword(newPassword);
						servletHelper.getUserService().updateUser(loggedUser);
						request.setAttribute("passwordmessage","password changed sucessfully");
					} catch (PasswordCheckException e) {
						request.setAttribute("passwordmessage",e.getMessage());
					}					  
			
			}else {
				request.setAttribute("passwordmessage","passwords do not match");
			}
		} else {
			request.setAttribute("passwordmessage","enter password");	
		}
	return true;

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
