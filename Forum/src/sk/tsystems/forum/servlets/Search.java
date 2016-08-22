package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.service.jpa.JpaConnector;
import sk.tsystems.forum.servlets.master.MasterServlet;

@WebServlet("/Search")
public class Search extends MasterServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletHelper helpser = new ServletHelper(request);
		try {
			String tosearch = request.getParameter("srch");
			
			
			if(tosearch==null) { // return web page
				response.setContentType("text/html");
				request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
				request.getRequestDispatcher("/WEB-INF/jsp/search.jsp").include(request, response);
				request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp").include(request, response);
				return;
			}

			// TODO **** length check 
			String isearch = "%"+tosearch.replace(' ', '%').toLowerCase()+"%";
			
			List <Comment> comments;
			List <Theme> themes;
			List <Topic> topics;
			UserRole role = helpser.getSessionRole();
			try(JpaConnector jpa = new JpaConnector()) {
				comments = jpa.getEntityManager().createQuery("SELECT c FROM Comment c WHERE lower(c.comment) like :toSearch "+
						(role.equals(UserRole.ADMIN)?"":
							(role.equals(UserRole.REGULARUSER)?"":" AND c.theme.isPublic = false AND c.theme.topic.isPublic = true")+
								" AND c.blocked = null AND c.theme.blocked=null AND c.theme.topic.blocked=null"), 
						Comment.class).setParameter("toSearch", isearch).getResultList();
				
				themes = jpa.getEntityManager().createQuery("SELECT c FROM Theme c WHERE (lower(c.description) like :toSearch "
						+ " OR lower(c.name) like :toSearch)  "+
						(role.equals(UserRole.ADMIN)?"":
							(role.equals(UserRole.REGULARUSER)?"":" AND c.isPublic = false AND c.topic.isPublic = true")+
								" AND c.blocked = null AND c.topic.blocked=null"), 
						Theme.class).setParameter("toSearch", isearch).getResultList();

				topics = jpa.getEntityManager().createQuery("SELECT c FROM Topic c WHERE lower(c.name) like :toSearch)  "+
						(role.equals(UserRole.ADMIN)?"":
							(role.equals(UserRole.REGULARUSER)?"":" AND c.isPublic = false ")+
								" AND c.blocked = null "), 
						Topic.class).setParameter("toSearch", isearch).getResultList();
			}
			
			List<SearchResult> results = new ArrayList<>();
			for (Comment comment: comments) {
				results.add(new SearchResult(comment, tosearch));
			}
			
			for (Theme theme : themes) {
				results.add(new SearchResult(theme));
			}
			
			for (Topic topic : topics) {
				results.add(new SearchResult(topic));
			}
			
			HashMap<String, Object> resp = new HashMap<>();
			resp.put("what", tosearch);
			resp.put("count", results.size());
			resp.put("result", results);
			
			ServletHelper.jsonResponse(response, resp);
			
			
			//	doGetListOfThemeTopic(helpser, response, true, true);
		} catch (Exception e) {
			e.printStackTrace();
			ServletHelper.ExceptionToResponseJson(e, response, false);
		}
	}
	
	public class SearchResult {
		String comment;
		String topic;
		String theme;
		int idComment;
		int idTopic;
		int idTheme;
		boolean blocked;
		Date lastModified;
		Date created;
		
		public SearchResult(Comment comment, String srchWord) {
			this(comment.getTheme());
			this.comment = comment.getComment();
			this.idComment = comment.getId();
			this.lastModified = comment.getModified();
			this.created = comment.getCreated();
			this.blocked = comment.isBlocked();
		}

		public SearchResult(Theme theme) {
			this(theme.getTopic());
			this.theme = theme.getName();
			this.idTheme = theme.getId();
			this.lastModified = theme.getModified();
			this.created = theme.getCreated();
			this.blocked = theme.isBlocked();
		}

		public SearchResult(Topic topic) {
			this.idTopic = topic.getId();
			this.topic = topic.getName();
			this.lastModified = topic.getModified();
			this.created = topic.getCreated();
			this.blocked = topic.isBlocked();
		}

		public String getComent() {
			return comment;
		}
		public String getTopic() {
			return topic;
		}
		public String getTheme() {
			return theme;
		}
		public int getIdComment() {
			return idComment;
		}
		public int getIdTopic() {
			return idTopic;
		}
		public int getIdTheme() {
			return idTheme;
		}
		public boolean isBlocked() {
			return blocked;
		}
		public Date getLastModified() {
			return lastModified;
		}
		public Date getCreated() {
			return created;
		}
	}
}
