package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.boot.model.source.internal.hbm.Helper;

import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.service.jpa.UserJPA;
import sk.tsystems.forum.serviceinterface.UserInterface;

/**
 * Servlet implementation class SignIn
 */
@WebServlet("/SignIn")
public class SignIn extends MasterServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see MasterServlet#MasterServlet()
	 */
	public SignIn() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	PrintWriter out = response.getWriter();
	
		request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
	
/*		out.println("<html>");
		out.println("<head>");
		out.println("<title>Login</title>");
		
		out.println("</head>");
		out.println("<body>");*/
		//out.println("<h>Login</h2>");
		
		try {
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").include(request, response);
			ServletHelper svHelper = new ServletHelper(request);
			UserInterface userService = svHelper.getUserService();

			
			try
			{
				User usr = new User("Joe", "123456", new Date(), "Jozef");                  /// TODO opravit userName a Name - nick presp. o co sa jedna
				svHelper.getUserService().addUser(usr);
			}
			catch(Exception e)
			{
				response.getWriter().print("Jozov account uz exituje");
			}
			
			
			String userName = request.getParameter("user_login"); 
			String password = request.getParameter("user_pass");
			if (userName == null || password == null) {
				return;
			}

			

			User user = userService.getUser(userName);
			if (user == null &&  user.getPassword().compareTo(password)!=0) {
				response.getWriter().println("<h1>Nespravne meno alebo heslo</h1>");
				return;
			}

			

			
				response.getWriter().println("I am " + user);
				svHelper.setLoggedUser(user);
			

		} finally {

		}
		
/*		out.println("<form metod='get'>");
		
		out.println("<input type ='hidden' name= '' value = ''/>");
		
		out.println("User Name:<input type='text' name=''><br>");
		out.println("<input type='submit'><br>");
		out.println("</form>");
		
		out.println("</body>");
		out.println("</html>");	*/	

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
