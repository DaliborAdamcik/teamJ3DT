package sk.tsystems.forum.service.jpa;

import java.util.List;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.serviceinterface.CommentInterface;

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

	@Override
	public boolean removeComment(Comment comment) {  
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.getEntityManager().remove(comment);// TODO begin end transaction ??
			return true;
		}
	}

	@Override
	public boolean updateComment(Comment comment) { // OK
		return addComment(comment);
	}

	@Override
	public Comment getComment(int ident) { // OK
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.getEntityManager().find(Comment.class, ident);
		}
	}

	@Override
	public List<Comment> getComments(Topic topic) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("SELECT c FROM Comment c WHERE c.topic=:topic").setParameter("topic", topic).getResultList();
		}
	}

}
