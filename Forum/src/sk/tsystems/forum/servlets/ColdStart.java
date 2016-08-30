package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ColdStart
 */
@WebServlet(description = "Initialize an empty database / erase database", urlPatterns = { "/ColdStart" })
public class ColdStart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().print("<html lang=\"en\"><head><meta charset=\"UTF-8\"><title>J3DT FORUM COLD START</title></head><body><h1>Cold Start script</h1><p style=\"text-weight: bold;\">"+
				"Please, use this script to create (rewrite) an empty database.</p><h3>Default acount</h3><b>User name: </b><i>admin</i><br/><b>Password: </b><i>123456.a</i><br/><br/>");
		
		if(request.getParameter("recreate")==null)
			response.getWriter().print("<form method=\"GET\"><input type=\"hidden\" name=\"recreate\" value=\"true\"/><input type=\"submit\" value=\"Create database\"/></form>");
		else {
			try {
				sk.tsystems.forum.coldstart.ColdStart cds = new sk.tsystems.forum.coldstart.ColdStart();
				OutputStream out = new OutputStream() {
					@Override
					public void write(int b) throws IOException {
						response.getWriter().write(b);
					}
				};
				cds.run(new PrintStream(new PrintStream(out)));
				
				response.getWriter().print("<div style=\"background-color: #99ebff;\"><h2>Database created succesfully</h2>");
			}
			catch(Exception e) {
				response.getWriter().print("<div style=\"background-color: #ffb3d1;\"><h2>An exception occured during cold start: "+e.getClass().getSimpleName()+
						"</h2>"+e.getMessage());
			}
		}
			
		response.getWriter().print("</div></body></html>");
	}
}
