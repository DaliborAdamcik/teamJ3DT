package sk.tsystems.forum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.CommonEntity;
import sk.tsystems.forum.entity.exceptions.EntityAutoPersist;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;
import sk.tsystems.forum.service.jpa.JpaConnector;

@Entity
@Table(name = "COMMENT_RATING")
public class CommentRating extends CommonEntity implements Comparable<CommentRating> {

	/** integer field <b>rating of the comment {@link Comment} </b> */
	@Column(name = "RATING", nullable = false)
	private int rating;

	/** {@link User} field of <b>owner</b> */
	@OneToOne
	private User owner;

	/** {@link Theme} field for {@link Comment} to be included in */
	@ManyToOne
	private Theme theme;

	/** {@link Comment} field for <b>comment which is rated</b> */
	@ManyToOne
	private Comment comment;

	/**
	 * Creates new rating for specified {@link Comment} by owner {@link User}
	 * 
	 * @param comment
	 *            {@link Comment}
	 * @param owner
	 *            {@link User}
	 * @param rating
	 *            {@link Integer}
	 * @throws {@link
	 *             FieldValueException}
	 * @throws {@link
	 *             EntityAutoPersist}
	 */
	public CommentRating(Comment comment, User owner, int rating) throws FieldValueException, EntityAutoPersist {
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
		} catch (IllegalArgumentException | PersistenceException e) {
			throw new EntityAutoPersist("Cant persist '" + getClass().getSimpleName() + "' ", e);
		}
	}

	/** Constructor only for hibernate. Must be private. */
	private CommentRating() {
		super();
	}

	/**
	 * Getter for rating
	 * 
	 * @return integer value of rating for comment {@link Comment}
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Getter for owner
	 * 
	 * @return owner {@link User} of rating
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * Getter for theme
	 * 
	 * @return theme {@link Theme}
	 */
	public Theme getTheme() {
		return theme;
	}

	/**
	 * Getter for comment
	 * 
	 * @return comment {@link Comment}
	 */
	public Comment getComment() {
		return comment;
	}

	/**
	 * Gets rating from the database
	 * 
	 * @param comment
	 *            {@link Comment} which is rated
	 * @param owner
	 *            {@link User} who is rating
	 * @return rating for specified comment from specified owner
	 */
	public static CommentRating getRating(Comment comment, User owner) {
		try (JpaConnector jpa = new JpaConnector()) {
			return (CommentRating) jpa
					.createQuery("SELECT r FROM CommentRating r WHERE r.owner=:owner AND r.comment=:comment")
					.setParameter("owner", owner).setParameter("comment", comment).getSingleResult();
		}
	}

	/**
	 * Set rating in the database
	 * 
	 * @param integer
	 *            rating
	 * @throws {@link
	 *             EntityAutoPersist}
	 */
	private void setRating(int rating) throws EntityAutoPersist {
		this.rating = rating;
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.merge(this);
		} catch (IllegalArgumentException | PersistenceException e) {
			throw new EntityAutoPersist(this, e);
		}
	}

	/**
	 * Set rating to 1
	 * 
	 * @throws {@link
	 *             EntityAutoPersist}
	 */
	public void upVote() throws EntityAutoPersist {
		setRating(1);
	}

	/**
	 * Set rating to -1
	 * 
	 * @throws {@link
	 *             EntityAutoPersist}
	 */
	public void downVote() throws EntityAutoPersist {
		setRating(-1);
	}

	/**
	 * Set rating to 0
	 * 
	 * @throws {@link
	 *             EntityAutoPersist}
	 */
	public void unVote() throws EntityAutoPersist {
		setRating(0);
	}

	/**
	 * Overrides java.lang.Object.equals
	 *
	 * @param object
	 *            {@link Object}
	 * @return true if <b>this</b> has the same id as {@link Object} in the
	 *         parameter
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
	 * Implements Comparable\<CommentRating\>.compareTo
	 * 
	 * @param {@link
	 * 			CommentRating}
	 * @return returned value(integer) is > 0 when this rating is higher than
	 *         the rating in parameter, = 0 if equal, < 0 if lower
	 */
	@Override
	public int compareTo(CommentRating r) {
		return this.rating - r.getRating();
	}
}
