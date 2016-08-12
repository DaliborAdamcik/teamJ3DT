package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.service.TopicService;
import sk.tsystems.forum.servlets.master.MasterServlet;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Comment/*")
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
			TopicService topicservice = svHelper.getTopicService();
			Theme theme = null;
			int topic_id = 0;
			try {
				topic_id = Integer.parseInt(request.getParameter("topicid"));
				request.setAttribute("topicid", topic_id);
				theme = svHelper.getThemeService().getTheme(topic_id);
			} catch (NullPointerException | NumberFormatException e) {
				e.printStackTrace();
			}

			String action = request.getParameter("action");

			if ("insert_comment".equals(action)) {
				// if (svHelper.getSessionRole().equals(UserRole.GUEST))
				if (svHelper.getLoggedUser() == null) {
					out.println("You are not sign in or maybe you are blocked");
				} else {
					svHelper.getCommentService().addComment(new Comment(request.getParameter("comment"), 
							theme, svHelper.getLoggedUser(), true));
				}
			}
			 
			List<Comment> comments = svHelper.getCommentService().getComments(theme);
			request.setAttribute("topicName", theme);
			request.setAttribute("comments", comments.iterator());

		} finally {
			request.getRequestDispatcher("/WEB-INF/jsp/comment.jsp").include(request, response);
			request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);
		}
	}

	
}
