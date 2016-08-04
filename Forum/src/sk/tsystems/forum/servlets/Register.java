package sk.tsystems.forum.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.service.jpa.UserJPA;
import sk.tsystems.forum.serviceinterface.UserInterface;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends MasterServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see MasterServlet#MasterServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
        ServletHelper svHelper = new ServletHelper(request);
        svHelper.setService(new UserJPA()); // TODO !!! this line must be removed from here for general purposes
        try
        {
        	UserInterface usrSvc = svHelper.getUserService();
        	
        	
        			
        	
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").include(request, response);
        	
        }
        finally
        {
            request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
