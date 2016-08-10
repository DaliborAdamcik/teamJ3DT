package sk.tsystems.forum.service.jpa;

import java.util.List;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.service.CommentInterface;

public class CommentJPA implements CommentInterface {

	public CommentJPA() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addComment(Comment comment) { // OK
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.persist(comment);
			return true;
		}
	}
	@Deprecated
	@Override
	public boolean removeComment(Comment comment) {  
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.remove(comment); 
			return true;
		}
	}

	@Override
	public boolean updateComment(Comment comment) { // OK
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.merge(comment);
			return true;
		}
	}

	@Override
	public Comment getComment(int ident) { // OK
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.getEntityManager().find(Comment.class, ident);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getComments(Topic topic) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("SELECT c FROM Comment c WHERE c.topic=:topic").setParameter("topic", topic).getResultList();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getComments(User owner) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("SELECT c FROM Comment c join c.owner o WHERE o=:owner").setParameter("owner", owner).getResultList();
		}
		
	}

}
