package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.service.jpa.TopicJPA;
import sk.tsystems.forum.servlets.master.MasterServlet;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/Welcome")
public class Welcome extends MasterServlet {
	private static final long serialVersionUID = 1L;
	TopicJPA topicJPA = new TopicJPA();

	/**
	 * @see MasterServlet#MasterServlet()
	 */
	public Welcome() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// CONTENT TYPE and OBJECTS
		response.setContentType("text/html");
		ServletHelper helpser = new ServletHelper(request);

		// GET ATTRIBUTE
		if (request.getParameter("new_topic_name") != null) {
			if (!request.getParameter("new_topic_name").equals("")) {
				System.out.println("Request> create new topic: " + request.getParameter("new_topic_name"));

				if (helpser.getSessionRole() == UserRole.ADMIN) {
					topicJPA.addTopic(new Topic(request.getParameter("new_topic_name"), true));
					System.out.println("New topic " + request.getParameter("new_topic_name") + "was created.");
				}
			}
		}

		if (request.getParameter("parameter") != null) {
			if (request.getParameter("parameter").equals("logout")) {
				helpser.logoutUser();
				request.setAttribute(CURRENT_USER_ATTRIB, null);
				System.out.println("User was logged out...");				
			}
		}
		
		HashMap<Topic, List<Theme>> topicThemeList= new HashMap<>();
		
		for (Topic topic : helpser.getTopicService().getTopics()) {
			topicThemeList.put(topic, helpser.getThemeService().getTheme(topic));
		}
		
		System.out.println(topicThemeList);
		
		
		request.setAttribute("topthemlis", topicThemeList);

		// Atribut logged user, vyuizity pri jsp kde je menu
		request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
		response.getWriter().print("<div id=\"welcome_pg\">");
		request.getRequestDispatcher("/WEB-INF/jsp/welcomepage.jsp").include(request, response);
		response.getWriter().print("</div><div id=\"comments_pg\" style=\"display: none;\">");
		request.getRequestDispatcher("/WEB-INF/jsp/commentnew.jsp").include(request, response);
		response.getWriter().print("</div>");
		
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
