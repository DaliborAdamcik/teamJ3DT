package sk.tsystems.forum.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestChange
 */
@WebServlet("/TestChange")
public class TestChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int currentState = 0;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestChange() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Object obj = new Object();
		synchronized (obj) {
	        try {
	            Thread.sleep(5000);
	        } catch (Throwable e) {
	            e.printStackTrace();
	        }
	    }
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
