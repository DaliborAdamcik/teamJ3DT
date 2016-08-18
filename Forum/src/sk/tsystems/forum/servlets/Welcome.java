package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.entity.common.BlockableEntity;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.helper.TopicThemePrivileges;
import sk.tsystems.forum.helper.URLParser;
import sk.tsystems.forum.helper.exceptions.CommonEntityException;
import sk.tsystems.forum.helper.exceptions.URLParserException;
import sk.tsystems.forum.helper.exceptions.UnknownActionException;
import sk.tsystems.forum.helper.exceptions.WEBNoPermissionException;
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
				doGetListOfThemeTopic(helpser, response, true, "news".equals(url.getAction()));
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
	
	private void doGetListOfThemeTopic(ServletHelper helpser, HttpServletResponse response, boolean flushmode, boolean newsonly)
			throws ServletException, IOException {
		Map<String, Object> resp = new HashMap<>();
		
		Date filterDate;
		List<Theme> themes; 
		if(!newsonly || (filterDate = (Date) helpser.getSessionObject("theme_filter_date"))==null)
		{
			themes = helpser.getThemeService().getTheme();
		}
		else
			themes = helpser.getThemeService().getTheme(filterDate);

		if(!themes.isEmpty()) // save filter or last item
			helpser.setSessionObject("theme_filter_date", themes.get(themes.size()-1).getModified());
		
		List<Integer> erased = new ArrayList<>();  
		List<Topic> topics = new ArrayList<>();
		// create list of blocked, remove blocked (erased) 
		if(!helpser.getSessionRole().equals(UserRole.ADMIN))
		{
			UserRole currRole = helpser.getSessionRole();
			for (Iterator<Theme> iterator = themes.iterator(); iterator.hasNext();) {
				Theme theme = iterator.next();
				if(theme.isBlocked() ||
						theme.getTopic().isBlocked() || 
						!(theme.isIsPublic()&&theme.getTopic().isIsPublic()) && currRole.equals(UserRole.GUEST)) {
					erased.add(theme.getId());
					iterator.remove();
				} else {
					if(!topics.contains(theme.getTopic()))
						topics.add(theme.getTopic());
				}
			}
		} else {
			for (Theme theme: themes) {
				if(!topics.contains(theme.getTopic()))
					topics.add(theme.getTopic());
			}
		}
		
		resp.put("themes", themes);
		resp.put("topics", topics);
		resp.put("topicCount", topics.size());
		resp.put("themeCount", themes.size());
		if(newsonly)
			resp.put("erased", erased);
		
		if(flushmode)
			response.setContentType("application/json");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.addMixIn(Theme.class, MixIn.class);
		
		if(!flushmode) {
			mapper.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
			mapper.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false);
		}
		mapper.writeValue(response.getWriter(), resp);
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
		/*HashMap<Topic, List<Theme>> topicThemeList= new HashMap<>();
		List<Topic> topics = helpser.getTopicService().getTopics();
		Collections.sort(topics);
		
		for (Topic topic : topics) {
			List<Theme> themes = helpser.getThemeService().getTheme(topic);
			Collections.sort(themes);
			topicThemeList.put(topic, themes);
		}
		
		request.setAttribute("topthemlis", topicThemeList);
		*/// TODO potom to vyrucic po tadzzi
		
		// Atribut logged user, vyuizity pri jsp kde je menu
		request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
		request.getRequestDispatcher("/WEB-INF/jsp/welcomepage.jsp").include(request, response);
		request.getRequestDispatcher("/WEB-INF/jsp/commentnew.jsp").include(request, response);
		response.getWriter().print("<script id=\"themesFirst\" type=\"application/json\">");
		doGetListOfThemeTopic(helpser, response, false, false);
		response.getWriter().print("</script>");
		response.getWriter().print("<script>welcomeUIinit();</script>");
		request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);
		return;		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// edit theme
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletHelper svHelper = new ServletHelper(request);
		try {
			URLParser url = svHelper.getURLParser();
			if(url.getAction()==null)
				throw new URLParserException("Required parameter action is not supplied.");
			
			JSONObject json = svHelper.getJSON();
			if(json==null)
				throw new UnknownActionException("We request an JSON object into an input.");
			
			if("theme".equals(url.getAction()))
			{
				TopicThemePrivileges prioper = new TopicThemePrivileges(svHelper, url, response, Theme.class);
				Theme theme = prioper.getStoredObject(true);
				
				theme.setDescription(json.getString("description"));
				theme.setName(json.getString("name"));
				theme.setPublic(json.getBoolean("isPublic"));
				
				prioper.store(theme);
				return;
			}

			if("topic".equals(url.getAction()))
			{
				/*TopicThemePrivileges prioper = new TopicThemePrivileges(svHelper, url, response, Theme.class);
				Theme theme = prioper.getStoredObject(true);
				
				theme.setDescription(json.getString("description"));
				theme.setName(json.getString("name"));
				theme.setPublic(json.getBoolean("isPublic"));
				
				prioper.store(theme);
				return;*/
				throw new RuntimeException("Not yet implemented");
			}
			
			throw new UnknownActionException("Uknknown action taken");
			
		} catch (URLParserException | WEBNoPermissionException | UnknownActionException e) {
			ServletHelper.ExceptionToResponseJson(e, response, false);
		}
		
		
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
	
	// add theme
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper svHelper = new ServletHelper(request);
		try {
			URLParser url = svHelper.getURLParser();
			if(url.getAction()==null)
				throw new URLParserException("Required parameter action is not supplied.");
			
			JSONObject json = svHelper.getJSON();
			if(json==null)
				throw new UnknownActionException("We request an JSON object into an input.");
			
			if("theme".equals(url.getAction()))
			{
				TopicThemePrivileges prioper = new TopicThemePrivileges(svHelper, url, response, Topic.class);
				Topic topic = prioper.getStoredObject(false); // we add new theme to topic, no need to check ownership
				Theme newTheme = new Theme(json.getString("name"), topic, json.getString("description"), svHelper.getLoggedUser(), json.getBoolean("isPublic"));
				prioper.store(newTheme);
				return;
			}

			if("topic".equals(url.getAction()))
			{
				/*TopicThemePrivileges prioper = new TopicThemePrivileges(svHelper, url, response, Theme.class);
				Theme theme = prioper.getStoredObject(true);
				
				theme.setDescription(json.getString("description"));
				theme.setName(json.getString("name"));
				theme.setPublic(json.getBoolean("isPublic"));
				
				prioper.store(theme);
				return;*/
				throw new RuntimeException("Not yet implemented");
			}
		} catch (URLParserException | WEBNoPermissionException | UnknownActionException | JSONException | CommonEntityException e) {
			ServletHelper.ExceptionToResponseJson(e, response, false);
		}
	}
	
	abstract class MixIn {
		  @JsonIgnore abstract public Topic getTopic();  
	}
}
