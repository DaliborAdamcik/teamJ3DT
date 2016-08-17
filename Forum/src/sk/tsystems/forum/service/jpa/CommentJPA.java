package sk.tsystems.forum.service.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.service.CommentService;

public class CommentJPA implements CommentService {

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
	public List<Comment> getComments(Theme theme) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("SELECT c FROM Comment c WHERE c.theme=:theme").setParameter("theme", theme).getResultList();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getComments(Theme theme, Date modifiedAfter) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("SELECT c FROM Comment c WHERE c.theme=:theme AND c.modified>:modified").
					setParameter("theme", theme).setParameter("modified", modifiedAfter, TemporalType.DATE).getResultList();
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
