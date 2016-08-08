package sk.tsystems.forum.servlets;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.service.jpa.CommentJPA;
import sk.tsystems.forum.service.jpa.TopicJPA;
import sk.tsystems.forum.serviceinterface.CommentInterface;
import sk.tsystems.forum.serviceinterface.TopicInterface;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper servletHelper = new ServletHelper(request);
		List<Comment> listOfComments = new ArrayList<Comment>();
		request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
		if(servletHelper.getLoggedUser().getRole()!=null &&servletHelper.getLoggedUser().getRole() !=UserRole.ADMIN){
			response.getWriter().print("<h1>Only admins can view this page</h1>");
		}
		
		else{
			CommentInterface commentservice = servletHelper.getCommentService();
			TopicInterface topicservice =servletHelper.getTopicService();
			for(Topic topic : topicservice.getTopics()){
				
				listOfComments.addAll(commentservice.getComments(topic));
			}
				request.setAttribute("listofcomments", listOfComments);
				//TODO dorobit zobrazenie v jsp
		}
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
