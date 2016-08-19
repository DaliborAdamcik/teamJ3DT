package sk.tsystems.forum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.CommonEntity;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;
import sk.tsystems.forum.service.jpa.JpaConnector;

@Entity
@Table(name = "COMMENT_RATING")
public class CommentRating extends CommonEntity implements Comparable<CommentRating> {

	@Column(name = "RATING", nullable = false)
	private int rating;

	@OneToOne
	private User owner;

	@ManyToOne
	private Theme theme;

	@ManyToOne
	private Comment comment;

	/**
	 * Creates new rating for specified {@link Comment} by owner {@link User}
	 * @param comment {@link Comment}
	 * @param owner {@link User}
	 * @param rating {@link Integer}
	 * @throws FieldValueException
	 */
	public CommentRating(Comment comment, User owner, int rating) throws FieldValueException {
		this();
		testNotEmpty(owner, "owner", false);
		testNotEmpty(comment, "comment", false);
		
		if (rating != -1 && rating != 1)
			throw new FieldValueException("Can't set rating for comment. Rating can be -1, 1 only.");
		
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

	private void setRating(int rating) {
		this.rating = rating;
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.merge(this);
		}
	}
	
	public void upVote() {
		setRating(1);
	}

	public void downVote() {
		setRating(-1);
	}
	
	public void unVote() {
		setRating(0);
	}
	

	/**
	 * Overrides java.lang.Object.equals 
	 *
	 * @param object
	 * @return boolean
	 */	
	public boolean equals(Object object) {
		if (object instanceof CommentRating) {
			if (this.getId() == ((CommentRating) object).getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Implements Comparable<CommentRating>.compareTo
	 * 
	 * @param CommentRating
	 * @return integer
	 */
	@Override
	public int compareTo(CommentRating r) {
		return this.rating - r.getRating();
	}
}
