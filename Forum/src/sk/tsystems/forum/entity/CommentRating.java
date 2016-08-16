package sk.tsystems.forum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.CommonEntity;
import sk.tsystems.forum.helper.exceptions.UserEntityException;
import sk.tsystems.forum.service.jpa.JpaConnector;

@Entity
@Table(name = "COMMENT_RATING")
public class CommentRating extends CommonEntity {

	@Column(name = "RATING", nullable = false)
	private int rating;

	@OneToOne
	private User owner;

	@ManyToOne
	private Theme theme;

	@ManyToOne
	private Comment comment;
	
	public CommentRating(Comment comment, User owner, int rating) throws UserEntityException {
		this();
		if (comment == null || owner == null || (rating != -1 && rating != 1))
			throw new UserEntityException("Required fields are not set properly");

		this.rating = rating;
		this.owner = owner;
		this.theme = comment.getTheme();
		this.comment = comment;

		try (JpaConnector jpa = new JpaConnector()) {
			jpa.persist(this);
		}
	}

	private CommentRating() {
		super();
	}

	public int getRating() {
		return rating;
	}

	public User getOwner() {
		return owner;
	}

	public Theme getTheme() {
		return theme;
	}

	public Comment getComment() {
		return comment;
	}

	public static CommentRating getRating(Comment comment, User owner) {
		try (JpaConnector jpa = new JpaConnector()) {
			return (CommentRating) jpa
					.createQuery("SELECT r FROM CommentRating r WHERE r.owner=:owner AND r.comment=:comment")
					.setParameter("owner", owner).setParameter("comment", comment).getSingleResult();
		}
	}

	private void updateRating(int rating) {
		this.rating = rating;
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.merge(this);
		}
	}
	
	public void upVote() {
		updateRating(1);
	}

	public void downVote() {
		updateRating(-1);
	}
	
	public void unVote() {
		updateRating(0);
	}
}
