package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.entity.common.BlockableEntity;
import sk.tsystems.forum.entity.common.CommonEntity;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.helper.URLParser;
import sk.tsystems.forum.helper.exceptions.URLParserException;
import sk.tsystems.forum.service.jpa.TopicJPA;
import sk.tsystems.forum.servlets.master.MasterServlet;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/Welcome/*")
public class Welcome extends MasterServlet {
	private static final long serialVersionUID = 1L;
	TopicJPA topicJPA = new TopicJPA();

	/**
	 * @see MasterServlet#MasterServlet()
	 */
	public Welcome() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletHelper helpser = new ServletHelper(request);
		try {
			URLParser url = helpser.getURLParser();
			if(url.getParrentID()<0) { // return web page
				responsePageHTML(request, response, helpser);
				return;
			}
			
			if(url.getParrentID()==0) // list of themes with topic
			{
				List<Theme> themes = helpser.getThemeService().getTheme();
				
				Map<String, Object> resp = new HashMap<>();
				if(!helpser.getSessionRole().equals(UserRole.ADMIN))
					resp.put("filterbyblock", themes.removeIf(t -> t.isBlocked() || t.getTopic().isBlocked() ));
				
				if(helpser.getSessionRole().equals(UserRole.GUEST))
					resp.put("filterpublic", themes.removeIf(t -> !t.isIsPublic() || !t.getTopic().isIsPublic()));

				resp.put("themes", themes);
				resp.put("resultcount", themes.size());
				
				response.setContentType("application/json");
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				mapper.writeValue(response.getWriter(), resp);
				return;
			}
			
			// single object by child id
			if(url.getAction()==null)
				throw new URLParserException("Action must be specified");
			
			Class<?> clz = null;
			switch(url.getAction())
			{
				case "theme": clz = Theme.class; break;
				case "topic": clz = Topic.class; break;
				default: throw new URLParserException("Unknown action: "+url.getAction()); 
			}

			BlockableEntity com = (BlockableEntity) url.getParentObject(clz);
			if(com==null || 
					com.isBlocked() && !helpser.getSessionRole().equals(UserRole.ADMIN) ||
					
					(com instanceof Theme && (!((Theme)com).isIsPublic())
							||
					com instanceof Topic && (!((Topic)com).isIsPublic())		
							)&& helpser.getSessionRole().equals(UserRole.GUEST)	
				)
				throw new URLParserException("An entity "+clz.getSimpleName()+" with id "+url.getParrentID()+" cant be found.");
			
			Map<String, Object> resp = new HashMap<>();
			resp.put("theme", com);
			
			response.setContentType("application/json");
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.writeValue(response.getWriter(), resp);
		} catch (URLParserException e) {
			if(helpser.requestURL().indexOf("Welcome")<0) 
				responsePageHTML(request, response, helpser);
			else {

				response.getWriter().print(e);
				e.printStackTrace();
			}
		}
	}
	
	private void responsePageHTML(HttpServletRequest request, HttpServletResponse response, ServletHelper helpser)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		if (request.getParameter("parameter") != null) {
			if (request.getParameter("parameter").equals("logout")) {
				helpser.logoutUser();
				request.setAttribute(CURRENT_USER_ATTRIB, null);
				System.out.println("User was logged out...");				
			}
		}
		
		// TODO potom to vyrucic
		HashMap<Topic, List<Theme>> topicThemeList= new HashMap<>();
		for (Topic topic : helpser.getTopicService().getTopics()) {
			topicThemeList.put(topic, helpser.getThemeService().getTheme(topic));
		}
		
		request.setAttribute("topthemlis", topicThemeList);
		// TODO potom to vyrucic po tadzzi
		
		// Atribut logged user, vyuizity pri jsp kde je menu
		request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
		response.getWriter().print("<div id=\"welcome_pg\">");
		request.getRequestDispatcher("/WEB-INF/jsp/welcomepage.jsp").include(request, response);
		response.getWriter().print("</div><div id=\"comments_pg\" style=\"display: none;\">");
		request.getRequestDispatcher("/WEB-INF/jsp/commentnew.jsp").include(request, response);
		response.getWriter().print("</div>");
		
		request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);
		return;		
	}

	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

/*
		if (request.getParameter("new_topic_name") != null) {
			if (!request.getParameter("new_topic_name").equals("")) {
				System.out.println("Request> create new topic: " + request.getParameter("new_topic_name"));

				if (helpser.getSessionRole() == UserRole.ADMIN) {
					topicJPA.addTopic(new Topic(request.getParameter("new_topic_name"), true));
					System.out.println("New topic " + request.getParameter("new_topic_name") + "was created.");
				}
			}
		}*/

		// TODO Auto-generated method stub
		
	}

}
