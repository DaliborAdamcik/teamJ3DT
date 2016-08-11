package sk.tsystems.forum.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sk.tsystems.forum.entity.common.BlockableEntity;

@Entity
@Table(name = "TOPIC")
public class Topic extends BlockableEntity {

	/**
	 * name of the topic
	 */
	@Column(name = "NAME", nullable = false, unique = true)
	private String name;

	/**
	 * theme
	 */
	@ManyToOne
	@JoinColumn(name = "THEMEID")
	private Theme theme;

	/**
	 * description of topic
	 */
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	/**
	 * rating of topic (default = 0) modified by up / down votes
	 */
	@Column(name = "RATING", nullable = false)
	private int rating;

	/**
	 * author
	 */
	//@Column(name = "AUTHOR", nullable = false)
	@ManyToOne
	private User author;

	/**
	 * Public status for topic
	 */
	@Column(name = "ISPUBLIC", nullable = false)
	private boolean isPublic;
	// TODO implementovat list kommentov ak nam ho bude treba
	// @OneToMany
	// List<Comment> comments;

	public Topic(String name, Theme theme, String description, int rating, User author, boolean isPublic) {
		this();
		setName(name);
		setTheme(theme);
		setDescription(description);
		setRating(rating);
		setAuthor(author);
		setPublic(isPublic);
	}

	/**
	 * Constructor for JPA
	 */
	private Topic() {
		super();
	}

	/**
	 * Getter for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for theme
	 * 
	 * @return theme
	 */
	public Theme getTheme() {
		return theme;
	}

	/**
	 * Setter for theme
	 * 
	 * @param theme
	 */
	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	/**
	 * Getter for description
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter for description
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter for rating
	 * 
	 * @return rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Setter for rating
	 * 
	 * @param rating
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * Rating increase
	 */
	public void ratingIncrease() {
		rating++;
	}

	/**
	 * Rating decrease
	 */
	public void ratingDecrease() {
		rating--;
	}

	/**
	 * Getter for author
	 * 
	 * @return author
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * Setter for author
	 * 
	 * @param author
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	/**
	 * Getter for isPublic
	 * 
	 * @return isPublic
	 */
	public boolean isIsPublic() {
		return isPublic;
	}

	/**
	 * Setter for isPublic
	 * 
	 * @param isPublic
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * Getter for creationDate
	 * <p>
	 * This method returns date of topic create
	 * </p>
	 * <p>
	 * <b><i>DEPRECATED</i></b> Please use getCreated() instead.
	 * </p>
	 * 
	 * @return creationDate (return value is same as getCreated)
	 */
	@Deprecated
	public Date getCreationDate() {
		return getCreated();
	}
}
