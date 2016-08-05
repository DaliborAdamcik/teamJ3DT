package sk.tsystems.forum.servlets;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		

		String userName = request.getParameter("user_login"); //TODO overenia parametre na null... ak null ideme prec zo skriptu
		String password = request.getParameter("user_pass");	
		if(userName == null || password == null){	
			return;
		}	

        ServletHelper svHelper = new ServletHelper(request);
        svHelper.setService(new UserJPA()); // TODO !!! this line must be removed from here for general purposes
		UserInterface userService = svHelper.getUserService(); //TODO najdenie pozuivatela podla mena
		userService.getUser(userName);
		
		User user = userService.getUser(userName);
		if(user == null){
			return;
		} 
		
		if(password != user.getPassword()){ //TODO overenie hesla
			return;
		}
		
		/// TODO vypisat som "peter" alebo zle heslo
				
		
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
