package sk.tsystems.forum.service.jpa2;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.service.CommentService;
import sk.tsystems.forum.service.jpa.JpaConnector;

public class CommentJPA2 implements CommentService {

	private JpaConnector jpa;

	public CommentJPA2(JpaConnector jpa) {
		super();
		this.jpa = jpa;
	}

	@Override
	public boolean addComment(Comment comment) { // OK
		jpa.persist(comment);
		return true;
	}

	@Deprecated
	@Override
	public boolean removeComment(Comment comment) {
		jpa.remove(comment);
		return true;
	}

	@Override
	public boolean updateComment(Comment comment) { // OK
		jpa.merge(comment);
		return true;
	}

	@Override
	public Comment getComment(int ident) { // OK
		return jpa.getEntityManager().find(Comment.class, ident);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getComments(Theme theme) {
		return jpa.createQuery("SELECT c FROM Comment c WHERE c.theme=:theme").setParameter("theme", theme)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getComments(Theme theme, Date modifiedAfter) {
		return jpa.createQuery("SELECT c FROM Comment c WHERE c.theme=:theme AND c.modified>:modified")
				.setParameter("theme", theme).setParameter("modified", modifiedAfter, TemporalType.DATE)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getComments(User owner) {
		return jpa.createQuery("SELECT c FROM Comment c join c.owner o WHERE o=:owner").setParameter("owner", owner)
				.getResultList();
	}
}