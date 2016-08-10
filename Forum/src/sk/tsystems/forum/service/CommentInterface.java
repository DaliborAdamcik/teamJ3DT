package sk.tsystems.forum.service;

import java.util.List;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;

public interface CommentInterface {
	/**
	 * Adds comment to the database
	 * 
	 * @param comment
	 * @return true if successful, false othervise
	 */
	boolean addComment(Comment comment);
	/**
	 * Removes comment from the database
	 * 
	 * @param comment
	 * @return true if successful, false othervise
	 */
	boolean removeComment(Comment comment);
	/**
	 * Update comment in the database
	 * 
	 * @param comment
	 * @return true if successful, false othervise
	 */
	boolean updateComment(Comment comment);

	/**
	 * Gets comment from the database
	 * 
	 * @param ID of comment
	 * @return comment with specific ID
	 */
	Comment getComment(int ident);
	
	/**
	 * Returns all comments <b> that are listed with specific topic</b>
	 * 
	 * @param topic
	 * @return list of comments on specific topic
	 */
	List<Comment> getComments(Topic topic);
	
	
	/**
	 * Returns all comments that were created by certain user
	 * 
	 * @param owner (user that created comment)
	 * @return list of comments written by certain user
	 */
	List<Comment> getComments(User owner);
	
	
	
	
	
	
	
	

}
