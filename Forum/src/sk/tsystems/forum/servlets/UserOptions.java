package sk.tsystems.forum.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.servlets.master.MasterServlet;

/**
 * Servlet implementation class UserOptions
 */
@WebServlet("/Useroptions")
public class UserOptions extends MasterServlet {
	private static final long serialVersionUID = 1L;
       
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		ServletHelper servletHelper = new ServletHelper(request);
//		
//		request.setAttribute("loggeduser", servletHelper.getLoggedUser());
		request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
		request.getRequestDispatcher("/WEB-INF/jsp/welcomepage.jsp").include(request, response);
		request.getRequestDispatcher("/WEB-INF/jsp/useroptions.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
