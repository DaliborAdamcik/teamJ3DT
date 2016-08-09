package sk.tsystems.forum.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.service.jpa.TopicJPA;
import sk.tsystems.forum.serviceinterface.TopicInterface;

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
				System.out.println("User was logged out...");
				request.setAttribute("loggeduser", null);
			}
		}
		
		//Atribut logged user, vyuizity pri jsp kde je menu
		request.setAttribute("loggeduser", helpser.getLoggedUser());
		request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
		
		
		//TODO presunute do JSP, uz to nebude treba
//		response.getWriter().printf("<a href=\"Welcome?parameter=logout\">Logout</a>");
		
	if (helpser.getLoggedUser() != null)
		
		//TODO netreba to uz pravdepodobne
//		response.getWriter().print("<h1>Logged: " + helpser.getLoggedUser().getUserName() + "</h1>");

		request.getRequestDispatcher("/WEB-INF/jsp/welcomepage.jsp").include(request, response);

		TopicInterface topicService = helpser.getTopicService();

		response.getWriter().printf("<br><b>TOPICS:</b><br><br>");

		for (Topic t : topicService.getTopics()) {
			response.getWriter().printf("\nTopic: <a href=\"Comment?topicid=%d\">%s<a><br>", t.getId(), t.getName());
		}
		//TODO toto sa uz asi nebude pouzivat, uz je to v inom jsp
//		request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);
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
