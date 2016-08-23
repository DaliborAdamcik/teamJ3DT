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

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.CommentRating;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.entity.exceptions.EntityAutoPersist;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;
import sk.tsystems.forum.helper.ServletHelper;
import sk.tsystems.forum.helper.URLParser;
import sk.tsystems.forum.helper.exceptions.URLParserException;
import sk.tsystems.forum.helper.exceptions.UnknownActionException;
import sk.tsystems.forum.service.jpa.CommentJPA;
import sk.tsystems.forum.servlets.master.MasterServlet;

/**
 * Servlet implementation class Rating
 */
@WebServlet("/Rating/*")
public class Rating extends MasterServlet {
	private static final long serialVersionUID = 1L;
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletHelper servletHelper = new ServletHelper(request);
		URLParser pars;
		int finalRating = 0;
		try {
			pars = servletHelper.getURLParser();
			JSONObject obj = servletHelper.getJSON();
			if (obj == null) {
				throw new UnknownActionException("did not receive parameters");
			}
			String action = pars.getAction();
			if (action.equals("upvote")) {
				Comment comment = servletHelper.getCommentService().getComment(obj.getInt("id"));
				User owner = servletHelper.getLoggedUser();
				CommentRating cr = servletHelper.getCommentService().getCommentRating(owner, comment);
				if (cr == null) {
					cr = new CommentRating(comment, owner, 1);
					finalRating = 1;
				}

				if (cr != null) {
					if (cr.getRating() == -1) {
						cr.unVote();
						finalRating = 0;
					} else {
						cr.upVote();
						finalRating = 1;
					}
				}
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				Map<String, Object> resp = new HashMap<>();
				resp.put("id", obj.getInt("id"));
				resp.put("finalrating", finalRating);
				resp.put("rating",
						servletHelper.getCommentService().getComment(obj.getInt("id")).getRating().getRating());
				response.setContentType("application/json");
				mapper.writeValue(response.getWriter(), resp);
			}
			if (action.equals("downvote")) {
				Comment comment = servletHelper.getCommentService().getComment(obj.getInt("id"));
				User owner = servletHelper.getLoggedUser();

				CommentRating cr = servletHelper.getCommentService().getCommentRating(owner, comment);
				if (cr == null) {
					cr = new CommentRating(comment, owner, -1);
					finalRating = -1;
				}

				if (cr != null) {
					if (cr.getRating() == 1) {
						cr.unVote();
						finalRating = 0;
					} else {
						cr.downVote();
						finalRating = -1;
					}
				}
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				Map<String, Object> resp = new HashMap<>();
				resp.put("id", obj.getInt("id"));
				resp.put("finalrating", finalRating);
				resp.put("rating",
						servletHelper.getCommentService().getComment(obj.getInt("id")).getRating().getRating());
				response.setContentType("application/json");
				mapper.writeValue(response.getWriter(), resp);
			}
			if (action.equals("getinitialbuttons")) {
				User user = servletHelper.getLoggedUser();
				if(user == null || user.getRole()==UserRole.GUEST){
					return;
				}
				List<CommentRating> list = servletHelper.getCommentService().getAllCommentRatings(user);
				
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				Map<String, Object> resp = new HashMap<>();
				resp.put("ratings", list);
				response.setContentType("application/json");
				mapper.writeValue(response.getWriter(), resp);
			}

		} catch (URLParserException e) {
			e.printStackTrace();
		} catch (UnknownActionException e) {
			e.printStackTrace();
		} catch (FieldValueException e) {
			e.printStackTrace();
		} catch (EntityAutoPersist e) {
			e.printStackTrace();
		}

	}

}
