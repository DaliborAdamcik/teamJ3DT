package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Out;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.serviceinterface.TopicInterface;
import sk.tsystems.forum.serviceinterface.UserInterface;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/Welcome")
public class Welcome extends MasterServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see MasterServlet#MasterServlet()
	 */
	public Welcome() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		

		// CONTENT TYPE and HTTP SESSION
		response.setContentType("text/html");
/*
		// GET ATTRIBUTE
		if (!request.getAttribute("new_topic_name").equals("")) {
			System.out.println(request.getAttribute("new_topic_name"));
			request.setAttribute("new_topic_name", "");
		}*/
		
		System.out.println("v session pod new_topic_name sa prave nachadza: " + request.getParameter("new_topic_name"));

		ServletHelper helpser = new ServletHelper(request);
		request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);

		if(helpser.getLoggedUser()!=null)
			response.getWriter().print("<h1>Logged: "+helpser.getLoggedUser()+"</h1>");

		request.getRequestDispatcher("/WEB-INF/jsp/welcomepage.jsp").include(request, response);

		TopicInterface topicService = helpser.getTopicService();

		response.getWriter().printf("<br><b>TOPICS:</b><br><br>");

		for (Topic t : topicService.getTopics()) {
			response.getWriter().printf("\nTopic: <a href=\"Comment?topicid=%d\">%s<a><br>", t.getId(), t.getName());
		}

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
