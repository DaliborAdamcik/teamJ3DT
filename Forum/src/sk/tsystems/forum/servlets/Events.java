package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Events")
public class Events extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Date gate;
	private static String LAST_EVENT_STAMP = "LAST_EVENT_STAMP";
	private static int waitSeconds = 60;
	
	public Events() {
		super();
		gate = new Date();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Date old;
		
		resp.setContentType("text/plain");

		if((old = (Date) req.getSession().getAttribute(LAST_EVENT_STAMP))==null)
			old = new Date(0);
		
		synchronized (gate) {
			int tc = 0;
			
			do {
				// test something changed
		
				if(gate.compareTo(old)>0) {
					resp.getWriter().print("changed");
					req.getSession().setAttribute(LAST_EVENT_STAMP, gate.clone());
					return;
				}
				
				// nothing changed, response client 
				if(tc==1) {
					resp.getWriter().print("none");
					return;
				}
					
				// wait change for waitSeconds 
				try {
					gate.wait(waitSeconds*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while(tc++<2);
		}
	}
	
	public static void updateGate() {
		try {			
			synchronized (gate) {
					gate.setTime(System.currentTimeMillis());
					gate.notify();
				
				
			}
		} catch(NullPointerException e) {
			gate = new Date(0);
		}
	}
}
