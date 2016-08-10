package sk.tsystems.forum.servlets;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.helper.BlockHelper;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.helper.TestHelper;
import sk.tsystems.forum.service.CommentService;
import sk.tsystems.forum.service.TopicService;
import sk.tsystems.forum.service.UserService;
import sk.tsystems.forum.service.jpa.BlockedJPA;
import sk.tsystems.forum.service.jpa.CommentJPA;
import sk.tsystems.forum.service.jpa.TopicJPA;
import sk.tsystems.forum.servlets.master.MasterServlet;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
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
		request.setAttribute("loggeduser", servletHelper.getLoggedUser());
		request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
		if (servletHelper.getLoggedUser() == null || servletHelper.getLoggedUser().getRole() != UserRole.ADMIN) {
			response.getWriter().print("<h1>Only admins can view this page</h1>");
			// TODO prehodit do JSP
			return;

		}
		// add all users to request, later to be printed in admin.jsp
		UserService userservice = servletHelper.getUserService();
		request.setAttribute("listofusers", userservice.getAllUsers());
		TopicService topicservice = servletHelper.getTopicService();
		request.setAttribute("listoftopics", topicservice.getTopics());
		// try to add topic TODO prerobit ked sa aktualizuje databaza
		try {
			String newTopicName = request.getParameter("new_topic");
			boolean addable = true;
			if (newTopicName != null && newTopicName != "") {
				for (Topic topic : topicservice.getTopics()) { // TODO total
																// shit, spravit
																// normalny
																// check a
																// smazat
					if (topic.getName().equals(newTopicName)) {
						System.out.println("topic exists");
						addable = false;
					}
				}
				if (addable) {
					Topic newTopic = new Topic(newTopicName, true);
					topicservice.addTopic(newTopic);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

//		// try pre block
//		try {
//			if (request.getParameter("block") != null && request.getParameter("block_reason") != null) {
//				int idOfUserToBeBlocked = Integer.parseInt(request.getParameter("block"));
//				String blockReason = request.getParameter("block_reason");
//				BlockHelper.block(idOfUserToBeBlocked, blockReason, servletHelper.getLoggedUser());
//			}
//			
//		} catch (Exception e) {
//
//		}
//		
//		//try pre unblock
//		try {
//			if (request.getParameter("unblock") != null) {
//				int idOfUserToBeUnbanned = Integer.parseInt(request.getParameter("unblock"));
//				BlockHelper.unblock(idOfUserToBeUnbanned);
//			}
//			
//		} catch (Exception e) {
//
//		}
		
		request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").include(request, response);
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
