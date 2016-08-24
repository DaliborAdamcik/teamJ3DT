package sk.tsystems.forum.service;

import java.util.Date;
import java.util.List;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.CommentRating;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.User;

public interface CommentService {
	/**
	 * Adds comment to the database
	 * 
	 * @param comment {@link Comment}
	 * @return true if successful, false otherwise
	 */
	boolean addComment(Comment comment);
	/**
	 * Removes comment from the database
	 * 
	 * @param comment {@link Comment}
	 * @return true if successful, false otherwise
	 */
	boolean removeComment(Comment comment);
	/**
	 * Update comment in the database
	 * 
	 * @param comment {@link Comment}
	 * @return true if successful, false otherwise
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
	 * Returns all comments <b> that are listed with specific theme</b>
	 * 
	 * @param theme {@link Theme} theme that is parent for comments
	 * @return list of comments on specific theme
	 */
	List<Comment> getComments(Theme theme);
	
	/**
	 * Returns all comments <b> that are listed with specific theme</b> 
	 * and modified after specified date
	 * 
	 * @param theme {@link Theme}
	 * @param modifiedAfter {@link Date} of modification
	 * @return list of comments on specific theme
	 */
	List<Comment> getComments(Theme theme, Date modifiedAfter);
	
	/**
	 * Returns all comments that were created by certain user
	 * 
	 * @param owner ({@link User} who created comment)
	 * @return list of comments written by certain user
	 */
	List<Comment> getComments(User owner);
	
	
	/**
	 * Rating of certain comment from certain owner
	 * 
	 * @param owner ({@link User} who rated the comment)
	 * @param comment {@link Comment} which was rated
	 * @return rating  {@link CommentRating} of comment and owner from parameter
	 */
	CommentRating getCommentRating(User owner, Comment comment) ;
	
	/**
	 * Returns all comments from the database
	 * 
	 * @return list of comments from the database
	 * @see {@link List} 
	 */
	List<Comment> getAllComments();
	
	/**
	 * All ratings that were created by certain {@link User} 
	 * 
	 * @param owner {@link User}  who rated the comment
	 * @return {@link List}<{@link CommentRating}>  from specified user
	 * @see {@link List} 
	 * 
	 */
	List<CommentRating> getAllCommentRatings(User owner);
	
	
	

}
