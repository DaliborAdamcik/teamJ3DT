package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helpser = new ServletHelper(request);
        request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);

				
		UserInterface userService = helpser.getUserService();
		
		Date date = new Date();
		
		User newAdmin = new User("Pan Admin", "zloziteheslo", date, "admicius");
		newAdmin.setRole(UserRole.ADMIN);
		
		User newUser = new User("Aladar Fajka", "hesielko", date, "Aladaaaar");
		newUser.setBlocked(new Blocked(newAdmin, "lebo"));
		newUser.setRole(UserRole.GUEST);
		
		List<User> userList = new ArrayList<User>();
		userList.add(newAdmin);
		userList.add(newUser);
		response.getWriter().println("<h1>Uncompleted welcome page</h1>");
		
		request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
