package sk.tsystems.forum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import sk.tsystems.forum.entity.common.BlockableEntity;
import sk.tsystems.forum.entity.dto.ThemeObjectDTO;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;

@Entity
@Table(name = "THEME")
public class Theme extends BlockableEntity implements Comparable<Theme> {

	/** {@link String} field <b>name of theme</b> */
	@Column(name = "NAME", nullable = false, unique = true)
	private String name;

	/** {@link Topic} field for {@link Theme} to be included in */
	@ManyToOne
	@JoinColumn(name = "TOPICID")
	private Topic topic;

	/** {@link String} field <b>description of theme</b> */
	@Column(name = "DESCRIPTION", nullable = false, columnDefinition = "CLOB")
	private String description;

	/** {@link User} field <b>author of theme</b> */
	// @Column(name = "AUTHOR", nullable = false)
	@ManyToOne
	private User author;

	/** {@link Boolean} field <b>public status of theme</b> */
	@Column(name = "ISPUBLIC", nullable = false)
	private boolean isPublic;
	
	/** {@link ThemeObjectDTO} field <b>an DTO statistic for theme</b> not loaded by hibernate*/
	@Transient
	private ThemeObjectDTO dtoTheme; 

	/**
	 * Create new theme
	 * 
	 * @ @param
	 *       name {@link String} name of theme
	 * @param topic
	 *            {@link Topic} to be included in
	 * @param description
	 *            {@link String} description of theme
	 * @param author
	 *            {@link User} author of this theme
	 * @throws {@link
	 *             FieldValueException}
	 */
	public Theme(String name, Topic topic, String description, User author, boolean isPublic)
			throws FieldValueException {
		this();
		testNotEmpty(author, "author", false);
		testNotEmpty(topic, "topic", false);
		this.author = author;
		this.topic = topic;
		setName(name);
		setDescription(description);
		setPublic(isPublic);
	}

	/**
	 * Constructor for JPA, must be private
	 */
	private Theme() {
		super();
		dtoTheme = null;
	}

	/**
	 * Getter for name
	 * 
	 * @return {@link String} name of theme
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 *
	 * @param name
	 *            {@link String} name of theme
	 * @throws {@link
	 *             FieldValueException}
	 */
	public void setName(String name) throws FieldValueException {
		testNotEmpty(name, "name", true);
		this.name = name;
	}

	/**
	 * Getter for topic
	 * 
	 * @return {@link Topic} to be included in
	 */
	public Topic getTopic() {
		return topic;
	}

	/**
	 * Getter for description
	 * 
	 * @return {@link String} description of theme
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter for description
	 * 
	 * @param description
	 *            {@link String} description of theme
	 * @throws {@link
	 *             FieldValueException}
	 */
	public void setDescription(String description) throws FieldValueException {
		testNotEmpty(description, "description", false);
		this.description = description;
	}

	/**
	 * Getter for author
	 * 
	 * @return {@link User} author of theme
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * Getter for isPublic
	 * 
	 * @return boolean public status of theme
	 */
	public boolean isIsPublic() {
		return isPublic;
	}

	/**
	 * Setter for isPublic
	 * 
	 * @param isPublic
	 *            boolean public status of theme
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * Getter for rating
	 * Is not automatically created by hibernate
	 * @return rating in DTO object
	 */
	public ThemeObjectDTO getRating() {
		return dtoTheme;
	}

	/**
	 * Setter for rating
	 * 
	 * @param rating {@link ThemeObjectDTO} DTO object for rating
	 */
	public void setRating(ThemeObjectDTO rating) {
		this.dtoTheme = rating;
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
		if (object instanceof Theme) {
			if (this.getId() == ((Theme) object).getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Implements Comparable\<Theme\>.compareTo
	 * 
	 * @param {@link
	 * 			Theme}
	 * @return returned value(integer) is > 0 when the name of theme is higher
	 *         in alphabetical order than the name of the theme in parameter, =
	 *         0 if equal, < 0 if lower
	 */
	@Override
	public int compareTo(Theme o) {
		return this.name.compareTo(o.getName());
	}

	/**
	 * Returns topic unique ID
	 * 
	 * @return an ID assigned to topic {@link Topic} to be included in
	 */
	public int getTopicId() {
		if (getTopic() == null)
			return 0;
		return getTopic().getId();
	}
}
