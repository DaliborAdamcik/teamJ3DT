package sk.tsystems.forum.servlets;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.service.UserService;
import sk.tsystems.forum.servlets.master.MasterServlet;

/**
 * Servlet implementation class SignIn
 */
@WebServlet("/SignIn")
public class SignIn extends MasterServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").include(request, response);
		ServletHelper svHelper = new ServletHelper(request);
		UserService userService = svHelper.getUserService();

		String userName = request.getParameter("user_login"); 
		String password = request.getParameter("user_pass");
		if (userName == null || password == null) {
			return; 
		}

		User user = userService.getUser(userName);
		if (user == null || !user.authentificate(password)) {
			response.getWriter().println("<script>alertDlg('Sign In', 'Invalid username or password','warn');</script>");
			return;
		}

		if (user.isBlocked()) {
			response.getWriter().println("<script>alertDlg('Account locked', 'Sorry, your account is locked.<br>Reason: "+user.getBlocked().getReason()+"','warn');</script>");
			return;
		}

		svHelper.setLoggedUser(user);
			
		response.sendRedirect("Welcome");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
