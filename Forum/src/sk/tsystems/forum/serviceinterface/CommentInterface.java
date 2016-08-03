package sk.tsystems.forum.serviceinterface;

import java.util.List;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;

public interface CommentInterface {
	
boolean addComment(Comment comment);

boolean removeComment(Comment comment);

boolean updateComment(Comment comment);

Comment getComment(int ident);

List<Comment> getComments(Topic topic);

}
