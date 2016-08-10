package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.service.CommentService;
import sk.tsystems.forum.service.TopicService;
import sk.tsystems.forum.servlets.master.MasterServlet;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Comment")
public class CommentServlet extends MasterServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see MasterServlet#MasterServlet()
	 */
	public CommentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
		ServletHelper svHelper = new ServletHelper(request);
		PrintWriter out = response.getWriter();

		try {
			// UserInterface usrSvc = svHelper.getUserService();
			CommentService commentservice = svHelper.getCommentService();
			TopicService topicservice = svHelper.getTopicService();
			int topic_id = 0;
			try {
				topic_id = Integer.parseInt(request.getParameter("topicid"));
				request.setAttribute("topicid", topic_id);
			} catch (NullPointerException | NumberFormatException e) {
				e.printStackTrace();
			}

			// try {
			// usr = new User("Janka", "janka", new Date(), "Jana");
			// svHelper.getUserService().addUser(usr);
			// } catch (Exception e) {
			// response.getWriter().print("Tento account uz existuje");
			// }
			//
			// svHelper.setLoggedUser(usr);

			// commentservice.addComment(new Comment("sehr schon",
			// topicservice.getTopic(topic_id), usrSvc.getUser(10), true));
			// commentservice.addComment(new Comment("alles gutes",
			// topicservice.getTopic(topic_id), usrSvc.getUser(12), true));
			// commentservice.addComment(new Comment("igen",
			// topicservice.getTopic(topic_id), usrSvc.getUser(10), true));
			// commentservice.addComment(new Comment("szep",
			// topicservice.getTopic(topic_id), usrSvc.getUser(3), true));
			// commentservice.addComment(new Comment("szia mafia",
			// topicservice.getTopic(topic_id), usrSvc.getUser(6), true));

			String action = request.getParameter("action");

			if ("insert_comment".equals(action)) {
				// if (svHelper.getSessionRole().equals(UserRole.GUEST))
				if (svHelper.getLoggedUser() == null) {
					out.println("You are not sign in or maybe you are blocked");
				} else {
					commentservice.addComment(new Comment(request.getParameter("comment"),
							topicservice.getTopic(topic_id), svHelper.getLoggedUser(), true));
				}
			}

			commentservice.getComments(topicservice.getTopic(topic_id));
			List<Comment> comments = new ArrayList<>();
			comments = commentservice.getComments(topicservice.getTopic(topic_id));
			request.setAttribute("comments", comments.iterator());

		} finally {
			request.getRequestDispatcher("/WEB-INF/jsp/comment.jsp").include(request, response);
			request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);
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

}
