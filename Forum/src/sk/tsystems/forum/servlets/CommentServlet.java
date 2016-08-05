package sk.tsystems.forum.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.service.jpa.UserJPA;
import sk.tsystems.forum.serviceinterface.CommentInterface;
import sk.tsystems.forum.serviceinterface.TopicInterface;
import sk.tsystems.forum.serviceinterface.UserInterface;


/**
 * Servlet implementation class Register
 */
@WebServlet("/Comment")
public class CommentServlet extends MasterServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see MasterServlet#MasterServlet()
     */
    public CommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/header.jsp").include(request, response);
        ServletHelper svHelper = new ServletHelper(request);
        try
        {
        	UserInterface usrSvc = svHelper.getUserService();
        	CommentInterface commentservice = svHelper.getCommentService();
        	TopicInterface topicservice = svHelper.getTopicService();
//        	commentservice.addComment(new Comment("sehr schon", topicservice.getTopic(10), usrSvc.getUser(32), true));
//        	commentservice.addComment(new Comment("alles gutes", topicservice.getTopic(10), usrSvc.getUser(45), true));
//        	commentservice.addComment(new Comment("igen", topicservice.getTopic(10), usrSvc.getUser(32), true));
//        	commentservice.addComment(new Comment("szep", topicservice.getTopic(10), usrSvc.getUser(48), true));
//        	commentservice.addComment(new Comment("szia mafia", topicservice.getTopic(10), usrSvc.getUser(53), true));
        	
        	commentservice.getComments(topicservice.getTopic(10));
        	List<Comment> comments = new ArrayList<>();
			comments = commentservice.getComments(topicservice.getTopic(10));
			request.setAttribute("comments", comments.iterator());
        	       			
			request.getRequestDispatcher("/WEB-INF/jsp/comment.jsp").include(request, response);
        	
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
